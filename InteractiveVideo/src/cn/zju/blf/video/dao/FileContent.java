package cn.zju.blf.video.dao;

public class FileContent {
	private String timestamp;
	private String fileName;
	private String content;
	
	public FileContent(String timestamp, String fileName, String content)
	{
		this.timestamp = timestamp;
		this.fileName = fileName;
		this.content = content;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean equals(Object o)
	{
		if(o != null && o instanceof FileContent)
		{
			FileContent f = (FileContent)o;
			
			return fileName.equals(f.getFileName()) && content.equals(f.getContent());
		}
		return false;
	}
	
	public int hashCode()
	{
		return fileName.hashCode() + content.hashCode();
	}
}
