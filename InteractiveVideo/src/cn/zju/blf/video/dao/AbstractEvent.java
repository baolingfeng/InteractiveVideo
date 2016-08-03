package cn.zju.blf.video.dao;

import java.util.HashMap;
import java.util.Map;

public class AbstractEvent {
	private String timestamp;
	private double interval;
	private Map<String, Object> summary = new HashMap<String, Object>();
	
	
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public double getInterval() {
		return interval;
	}
	public void setInterval(double interval) {
		this.interval = interval;
	}
	public Map<String, Object> getSummary() {
		return summary;
	}
	public void setSummary(Map<String, Object> summary) {
		this.summary = summary;
	}
	
	public void addExpression(String key, Object o)
	{
		summary.put(key, o);
	}
}
