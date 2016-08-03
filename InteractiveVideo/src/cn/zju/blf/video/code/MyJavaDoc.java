package cn.zju.blf.video.code;

import java.util.List;

public class MyJavaDoc {
	private String library;
	private String className;
	private String methodName = "";
	private String methodParams = "";
	private String javadoc = "";
	private boolean isMethod = false;
	
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = (library != null ? library : "");
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = (className != null ? className : "");
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = (methodName != null ? methodName : "");
	}
	public String getMethodParams() {
		return methodParams;
	}
	public void setMethodParams(String methodParams) {
		this.methodParams = (methodParams != null ? methodParams : "");
	}
	public String getJavadoc() {
		return javadoc;
	}
	public void setJavadoc(String javadoc) {
		this.javadoc = javadoc;
	}
	public boolean isMethod() {
		return isMethod;
	}
	public void setMethod(boolean isMethod) {
		this.isMethod = isMethod;
	}
	
	public String toString()
	{
		/*
		if(this.isMethod)
		{
			return className + "." + methodName + "/" + methodParams;
		}
		else
		{
			return className + "." + javadoc.length();
		}
		*/
		return library + "/" + className + "/" + isMethod +"." + methodName + "." + methodParams + "/" + javadoc.length();
	}
	
	public boolean equals(Object o)
	{
		if(o == null) return false;
		
		if(this == o) return true;
		
		if(o instanceof MyJavaDoc)
		{
			MyJavaDoc jd = (MyJavaDoc)o;
			
			//System.out.println(this + "\n" + o);
			
			if(this.isMethod == jd.isMethod) 
			{
				if(this.isMethod == false)
				{
					return library.equals(jd.library) && className.equals(jd.className);
				}
				else
				{
					return library.equals(jd.library) && className.equals(jd.className) && 
							methodName.equals(jd.methodName) && methodParams.equals(jd.methodParams);
				}
			}
		}
		
		return false;
	}
	
	public int hashCode()
	{
		if(this.isMethod)
		{
			return (library + className + methodName + methodParams).hashCode();
		}
		else{
			return (library + className).hashCode();
		}
		
	}
}
