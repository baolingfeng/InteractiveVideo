package cn.zju.blf.video.dao;

import java.util.Comparator;

public class LowLevelEvent{
	private String timestamp;
	private String eventName;
	private int px;
	private int py;
	private String windowName;
	private int winRectLeft;
	private int winRectTop;
	private int winRectRight;
	private int winRectBottom;
	private String processName;
	private String parentWindow;
	
	private String type;
	
	private FocusUIAction action = null;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public int getPy() {
		return py;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public int getWinRectLeft() {
		return winRectLeft;
	}

	public void setWinRectLeft(int winRectLeft) {
		this.winRectLeft = winRectLeft;
	}

	public int getWinRectTop() {
		return winRectTop;
	}

	public void setWinRectTop(int winRectTop) {
		this.winRectTop = winRectTop;
	}

	public int getWinRectRight() {
		return winRectRight;
	}

	public void setWinRectRight(int winRectRight) {
		this.winRectRight = winRectRight;
	}

	public int getWinRectBottom() {
		return winRectBottom;
	}

	public void setWinRectBottom(int winRectBottom) {
		this.winRectBottom = winRectBottom;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(String parentWindow) {
		this.parentWindow = parentWindow;
	}

	public FocusUIAction getAction() {
		return action;
	}

	public void setAction(FocusUIAction action) {
		this.action = action;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString()
	{
		return timestamp + "/" + eventName + "/" + px + "," + py + "/" + windowName + "/" + parentWindow + "/" + processName;
	}
	
	
}
