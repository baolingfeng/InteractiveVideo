package cn.zju.blf.video.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CodePatch {
	private String fileName;
	Map<String, String> variables = new HashMap<String, String>();
	Map<String, String> declaration = new HashMap<String, String>();
	Map<String, Set<String>> methodCalls = new HashMap<String, Set<String>>();
	Set<String> imports = new HashSet<String>();
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Map<String, String> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	public Map<String, String> getDeclaration() {
		return declaration;
	}
	public void setDeclaration(Map<String, String> declaration) {
		this.declaration = declaration;
	}
	public Map<String, Set<String>> getMethodCalls() {
		return methodCalls;
	}
	public void setMethodCalls(Map<String, Set<String>> methodCalls) {
		this.methodCalls = methodCalls;
	}
	public Set<String> getImports() {
		return imports;
	}
	public void setImports(Set<String> imports) {
		this.imports = imports;
	}
	
	public void addImports(String s)
	{
		this.imports.add(s);
	}
	
	public void addVariable(String key, String value)
	{
		this.variables.put(key, value);
	}
	
	public void addMethodCall(String key, String value)
	{
		if(this.methodCalls.containsKey(key))
		{
			this.methodCalls.get(key).add(value);
		}
		else
		{
			Set<String> l = new HashSet<String>();
			l.add(value);
			this.methodCalls.put(key, l);
		}
		
	}
	
	public void addDeclaration(String key, String value)
	{
		this.declaration.put(key, value);
	}
	
	public List<String> getAPICalls()
	{	
		List<String> apiCalls = new ArrayList<String>();
		for(Entry<String, Set<String>> entry : methodCalls.entrySet())
		{
			String var = entry.getKey();
			Set<String> calls = entry.getValue();
			String pre = var;
			
			if(variables.containsKey(var))
			{
				String type = variables.get(var);
				pre += "(";
				pre += "".equals(getImport(type)) ? type : getImport(type);
				pre += ")";
			}
			else if(declaration.containsKey(var))
			{
				String type = declaration.get(var);
				pre += "(";
				pre += "".equals(getImport(type)) ? type : getImport(type);
				pre += ")";
			}
			
			for(String c : calls)
			{
				apiCalls.add(pre + "." + c);
			}
		}
		//System.out.println(apiCalls);
		return apiCalls;
	}
	
	private String getImport(String type)
	{
		for(String s : this.imports)
		{
			
			String[] arr = s.split("\\.");
			int len = arr.length;
			if(type.equals(arr[len-1]))
			{
				return s;
			}
		}
		return "";
	}
}
