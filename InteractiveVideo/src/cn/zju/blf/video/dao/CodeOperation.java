package cn.zju.blf.video.dao;

import java.util.List;

public class CodeOperation {
	private String operation;
	private String type; //variable, class member, api call
	private String expression;
	
	public CodeOperation()
	{
	}
	
	public CodeOperation(String operation, String type, String expression)
	{
		this.operation = operation;
		this.type = type;
		this.expression = expression;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String toString()
	{
		return operation + " " + type + " " + expression;
	}
}
