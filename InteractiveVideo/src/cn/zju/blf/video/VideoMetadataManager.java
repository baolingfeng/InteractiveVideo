package cn.zju.blf.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VideoMetadataManager {
	
	public static Map<String, VideoMetadata> meta = new HashMap<String, VideoMetadata>();
	
	public static List<String> videoList = new ArrayList<String>();
	
	private static VideoMetadataManager singleton = new VideoMetadataManager();
	
	private VideoMetadataManager(){
		initVideoList();
		
		VideoMetadata md1 = new VideoMetadata();
		md1.setLogFile("/data/email/log/log.db3");
		md1.setVideo("data/email/email.mp4");
		md1.setStartTime("2016-07-13 16:08:34.000");
		//md1.setTitle("���ʹ��Java����email");
		//md1.setIntro("�����Ƶ�ǽ������ʹ��Java����д����email�ĳ���");
		md1.setTitle("a Java program to send email:");
		md1.setIntro("a simple program to send email: <br/>" +
					"1. load email configuration from a properties file <br/>" +
					"2. send a test email using Java mail library");
		meta.put("email", md1);
		
		VideoMetadata md2 = new VideoMetadata();
		md2.setLogFile("/data/plugin/log/log.db3");
		md2.setVideo("data/plugin/plugin.mp4");
		md2.setStartTime("2016-07-30 14:54:23.000");
		//md2.setTitle("���ʵ��һ���򵥵�Eclipse����༭��");
		//md2.setIntro("�ó�����Ҫ����һ�¹���<br/>1. �����ı༭����������Ϊ���ܣ�<br/>2. �ı�����ͳ�ƹ��ܣ���ʾ��Eclipse�е�һ��view��");
		md2.setTitle("an Eclipse plugin to implement a simple text editor");
		md2.setIntro("<br/>1. a basic text editor with file edit and save function;"
				+ "<br/>2. a view to show the length of content of text editor");
		meta.put("plugin", md2);
		
		VideoMetadata md3 = new VideoMetadata();
		md3.setLogFile("/data/mysql/log/log.db3");
		md3.setVideo("data/mysql/mysql.mp4");
		md3.setStartTime("2016-08-04 13:44:57.000");
//		md3.setTitle("Java����MySql���ݿ�");
//		md3.setIntro("ʵ�������Java����MySql���ݿ⣬������<br/>1. ִ�м򵥵�sql��ѯ��䣻<br/>2. ��preparedstatment��ѯ���ݿ⣻"
//				+ "<br/>3. ���ݿ���벢��������ֶε�ֵ; <br/>4.��ε��ô洢���̡�");
		md3.setTitle("a program to illustrate four MySql Database operations");
		md3.setIntro("ʵ�������Java����MySql���ݿ⣬������<br/>1. query a table using Statemen; <br/>2. query a table using PreparedStatement;"
				+ "<br/>3. insert a table then return the auto increment id;<br/>4. call a stored procedure.");
		meta.put("mysql", md3);
	}
	
	public void initVideoList()
	{
		videoList.add("email");
		videoList.add("plugin");
	}
	
	public static VideoMetadataManager getInstance(){
		return singleton;
	}
	
	public Set<String> getVideoList()
	{
		return meta.keySet();
	}
	
	public String getTitle(String videoName){
		return meta.get(videoName).getTitle();
	}
	
	public String getIntro(String videoName){
		return meta.get(videoName).getIntro();
	}
	
	public String getLogFile(String videoName){
		return meta.get(videoName).getLogFile();
	}
	
	public String getVideo(String videoName){
		return meta.get(videoName).getVideo();
	}
	
	public String getStartTime(String videoName){
		return meta.get(videoName).getStartTime();
	}
}

class VideoMetadata{
	String logFile;
	String video;
	String startTime;
	String title;
	String intro;
	
	public VideoMetadata(){
		
	}
	
	public VideoMetadata(String logFile, String video, String startTime){
		this.logFile = logFile;
		this.video = video;
		this.startTime = startTime;
	}
	
	public String getLogFile() {
		return logFile;
	}
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}
