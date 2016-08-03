package cn.zju.blf.video.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;

import cn.zju.blf.video.code.MyJavaDoc;
import cn.zju.blf.video.db.MySQLDBManager;


public class Test {
	public static void main(String args[]) throws Exception
	{
		String path = "C:/baolingfeng/research/InteractiveVideo/java/com/sun/corba/se/impl/naming/cosnaming/NamingContextDataStore.java";
		
		int libId = 3;
		
		//extractJavaDoc(path);
		List<String> root= new ArrayList<String>();
		//processFolder("C:/baolingfeng/research/InteractiveVideo/java", root);
		
		processFolder(libId, "C:/baolingfeng/research/InteractiveVideo/eclipse.platform.swt/bundles/org.eclipse.swt/Eclipse SWT/win32", root);
	}
	
	public static void processFolder(int libId, String srcFolder, List<String> root)
	{
		File folder = new File(srcFolder);
		
		File[] listOfFiles = folder.listFiles();
		
		MySQLDBManager db = new MySQLDBManager();
		String insertSql = "insert into docs(lib_id, class, method, param, javadoc) value(?, ?, ?, ?, ?)";
		String selectSql = "select count(1) from docs where lib_id = ? and class = ?";
	    for (int i = 0; i < listOfFiles.length; i++) 
	    {
	    	if (listOfFiles[i].isFile()) {
	    		int idx = listOfFiles[i].getName().lastIndexOf(".");
	    		
	    		if(!"java".equals(listOfFiles[i].getName().substring(idx+1)))
	    		{
	    			System.out.println(listOfFiles[i].getName() + " is not java source file"); 
	    			continue;
	    		}
	    		
	    		String className = String.join(".", root) + "." + listOfFiles[i].getName().substring(0, idx);
	    		
	    		int count = db.queryCount(selectSql, libId, className);
	    		if(count > 0) 
	    		{
	    			//System.out.println(className + " has been processed"); 
	    			continue;
	    		}
	    		
	    		System.out.println(className + " is being processed");
	    		try
	    		{
	    			List<MyJavaDoc> docs = extractJavaDoc(srcFolder + "/" + listOfFiles[i].getName());
	    			if(docs.size() > 0)
	    			{
	    				
	    				for(MyJavaDoc d : docs)
	    				{
	    					if(d.isMethod())
	    					{
	    						db.executeInsert(insertSql, libId, className, d.getMethodName(), String.join(",", d.getMethodParams()), d.getJavadoc());
	    					}
	    					else
	    					{
	    						db.executeInsert(insertSql, libId, className, null, null, d.getJavadoc());
	    					}
	    				}
	    			}
	    		}catch(Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    	} else if (listOfFiles[i].isDirectory()) {
	    		System.out.println("Directory " + String.join(".", root) + "." + listOfFiles[i].getName());
	    		root.add(listOfFiles[i].getName());
	    		processFolder(libId, srcFolder + "/" + listOfFiles[i].getName(), root);
	    		root.remove(root.size()-1);
	    	}
	    }
	}
	
	public static List<MyJavaDoc> extractJavaDoc(String filePath) throws Exception
	{
		String content = readFileToString(filePath);
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		final List<MyJavaDoc> docs = new ArrayList<MyJavaDoc>();
		
		cu.accept(new ASTVisitor() {
			String className;
			
			public boolean visit(TypeDeclaration n)
			{
				//System.out.println(n.getName());
				//System.out.println(n.getJavadoc());
				className = n.getName().toString();
				if(n.getJavadoc() != null)
				{
					MyJavaDoc mjd = new MyJavaDoc();
					mjd.setClassName(n.getName().toString());
					mjd.setMethod(false);
					mjd.setJavadoc(n.getJavadoc().toString());
					docs.add(mjd);
				}
				return true;
			}
			
			public boolean visit(MethodDeclaration n) {
				//System.out.println(n.getName());
				
				List<String> parameters = new ArrayList<String>();
                for (Object parameter : n.parameters()) {
                    VariableDeclaration variableDeclaration = (VariableDeclaration) parameter;
                    
                    String type = variableDeclaration.getStructuralProperty(SingleVariableDeclaration.TYPE_PROPERTY).toString();
                    for (int i = 0; i < variableDeclaration.getExtraDimensions(); i++) {
                        type += "[]";
                    }
                    parameters.add(type);
                }
                
                if(n.getJavadoc() != null)
                {
                	MyJavaDoc mjd = new MyJavaDoc();
    				mjd.setClassName(className);
    				mjd.setMethod(true);
    				mjd.setMethodName(n.getName().toString());
    				mjd.setJavadoc(n.getJavadoc().toString());
    				mjd.setMethodParams(String.join(",", parameters));
    				docs.add(mjd);
                }
				
				return false; // do not continue 
			}
			
		});
		
		return docs;
	}
	
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}
	 
		
}
