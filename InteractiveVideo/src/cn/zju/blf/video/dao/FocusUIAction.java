package cn.zju.blf.video.dao;

public class FocusUIAction {
	private String actionName;
	private String actionType;
	private String actionValue;
	private int boundLeft;
	private int boundTop;
	private int boundRight;
	private int boundBottom;
	private String parentActionName;
	private String parentActionType;
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionValue() {
		return actionValue;
	}
	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}
	public int getBoundLeft() {
		return boundLeft;
	}
	public void setBoundLeft(int boundLeft) {
		this.boundLeft = boundLeft;
	}
	public int getBoundTop() {
		return boundTop;
	}
	public void setBoundTop(int boundTop) {
		this.boundTop = boundTop;
	}
	public int getBoundRight() {
		return boundRight;
	}
	public void setBoundRight(int boundRight) {
		this.boundRight = boundRight;
	}
	public int getBoundBottom() {
		return boundBottom;
	}
	public void setBoundBottom(int boundBottom) {
		this.boundBottom = boundBottom;
	}
	public String getParentActionName() {
		return parentActionName;
	}
	public void setParentActionName(String parentActionName) {
		this.parentActionName = parentActionName;
	}
	public String getParentActionType() {
		return parentActionType;
	}
	public void setParentActionType(String parentActionType) {
		this.parentActionType = parentActionType;
	}
	
	public String toString()
	{
		return actionName + "/" + actionType + "/" + parentActionName + "/" + parentActionType;
	}
}
