package cn.zju.blf.video.code;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import cn.zju.blf.video.dao.CodeOperation;

public class CodeAnalyzer {
	
	public static CodePatch parseJavaSource(String str)
	{
		str = str.replace("\\n", "\n");
		str = str.replace("\\t", "\t");
		
		try
		{
			CompilationUnit cu = JavaParser.parse(new ByteArrayInputStream(str.getBytes("UTF-8")), "UTF-8");
			
			CodePatch cp = new CodePatch();
			
			cu.accept(new VoidVisitorAdapter<Void>(){
				
				@Override
		        public void visit(final MethodCallExpr n, final Void arg){
		            //System.out.println(n.getScope() + "/" + n.getName());
					if(n.getScope() != null)
					{
						cp.addMethodCall(n.getScope().toString(), n.getName());
					}
					else
					{
						cp.addMethodCall("this", n.getName());
					}
					
					
		            super.visit(n, arg);
		        }
				
				@Override
				public void visit(final VariableDeclarationExpr n, final Void arg)
				{
					for(VariableDeclarator v: n.getVars())
					{
						cp.addVariable(v.getId().getName(), n.getType().toString());
					}
					//System.out.println(n.getType() + "/" + n.getVars().get(0).getId().getName());
					super.visit(n, arg);
				}
				
				@Override
				public void visit(final ImportDeclaration n, final Void arg)
				{
					//System.out.println(n.getName());
					cp.addImports(n.getName().toString());
					
					super.visit(n, arg);
				}
				
				@Override
				public void visit(final FieldDeclaration n, final Void arg)
				{
					for(VariableDeclarator v: n.getVariables())
					{
						cp.addDeclaration(v.getId().getName(), n.getType().toString());
					}
					super.visit(n, arg);
				}
				
			}, null);
			
			return cp;
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception
	{
		File f = new File("c:/baolingfeng/java.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String str = "";
		String temp = null;
		while((temp = br.readLine()) != null) 
		{
			str += temp;
		}
		
		br.close();
		
		parseJavaSource(str);
	}
	
}
