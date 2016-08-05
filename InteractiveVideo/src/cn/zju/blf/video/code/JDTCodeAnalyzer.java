package cn.zju.blf.video.code;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class JDTCodeAnalyzer {
	public static CodePatch parseJavaSource(String str)
	{
		str = str.replace("\\n", "\n");
		str = str.replace("\\t", "\t");
		
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		if(cu == null) return null;
		
		CodePatch cp = new CodePatch();
		
		cu.accept(new ASTVisitor() {
			public boolean visit(FieldDeclaration n) {
				String type = n.getType().toString();
				for(Object o: n.fragments())
                {
                	VariableDeclarationFragment fd = (VariableDeclarationFragment)o;
                	
                	cp.addDeclaration(fd.getName().toString(), type);
                }
				
				return false; // do not continue to avoid usage info
			}
			
			public boolean visit(ImportDeclaration n) {
				cp.addImports(n.getName().toString());
				
				return false; // do not continue 
			}
			
			public boolean visit(MethodDeclaration n) {
				//System.out.println(n.getName());
				
				List<String> parameters = new ArrayList<String>();
                for (Object parameter : n.parameters()) {
                    VariableDeclaration variableDeclaration = (VariableDeclaration) parameter;
                    
                    String type = variableDeclaration.getStructuralProperty(SingleVariableDeclaration.TYPE_PROPERTY)
                            .toString();
                    for (int i = 0; i < variableDeclaration.getExtraDimensions(); i++) {
                        type += "[]";
                    }
                    parameters.add(type);
                }
                
                n.accept(new ASTVisitor() {
                	public boolean visit(VariableDeclarationStatement fs) {
                        //System.out.println("in method: " + fs.getType());
                		String type = fs.getType().toString();
                        for(Object o: fs.fragments())
                        {
                        	VariableDeclarationFragment fd = (VariableDeclarationFragment)o;
                        	cp.addVariable(fd.getName().toString(), type);
                        }
                        return false;
                    }
                	
                	public boolean visit(MethodInvocation n) {
                		//System.out.println(n);
                		try
                		{
                			cp.addMethodCall(n.getExpression().toString(), n.getName().toString());
                		}catch(Exception e)
                		{
                			System.out.println(e.getMessage() + ": " + n.getExpression()+ "/" + n.getName());
                		}
                		
                		
                		return false;
                	}
                });
                
				return false; // do not continue 
			}
		});
		
		return cp;
	}
}
