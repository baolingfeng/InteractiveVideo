package cn.zju.blf.video.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class QuestionnaireDBImpl {
	Logger logger = Logger.getLogger(JavaDocDBImpl.class);
	
	MySQLDBManager db;
	
	private static String QUESTION_DB_URL = "jdbc:mysql://155.69.147.247:3306/hci";
	private static String QUESTION_DB_USER = "blf";
	private static String QUESTION_DB_PASSWORD = "123456";
	
	private void readDBConfig()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(JavaDocDBImpl.class.getResourceAsStream("/config/db.txt"))); 
			String line = br.readLine();

	        while (line != null) {
	            String[] params = line.split("=");
	        	if("QUESTION_DB_URL".equals(params[0]))
	        	{
	        		QUESTION_DB_URL = params[1];
	        	}
	        	else if("QUESTION_DB_USER".equals(params[0]))
	        	{
	        		QUESTION_DB_USER = params[1];
	        	}
	        	else if("QUESTION_DB_PASSWORD".equals(params[0]))
	        	{
	        		QUESTION_DB_PASSWORD = params[1];
	        	}
	            
	            line = br.readLine();
	        }
		}catch(Exception e)
		{
			logger.info("read db config error: " + e.getMessage());
		}
	}
	
	public QuestionnaireDBImpl()
	{
		readDBConfig();
		
		db = new MySQLDBManager(QUESTION_DB_URL, QUESTION_DB_USER, QUESTION_DB_PASSWORD);
	}
	
	public int insertRecord(JSONObject jsonObj)
	{
		String host = jsonObj.getString("host");
		//String email = jsonObj.getString("email");
		String starttime = jsonObj.getString("starttime");
		String endtime = jsonObj.getString("endtime");
		String group = jsonObj.getString("group");
		String video = jsonObj.getString("video");
		
		String sql = "insert into records(host, starttime, endtime, egroup, video) values(?, ?, ?, ?, ?)";
		
		int recordId = db.executeInsertWithAI(sql, host, starttime, endtime, group, video);
		
		String sql2 = "insert into answers(record_id, question, answer, times) values(?, ? ,? ,?)";
		int qnumber = (Integer)jsonObj.get("qnumber");
		for(int i=1; i<=qnumber; i++)
		{
			String question = "q" + i;
			String answer = jsonObj.getString(question);
			String times = jsonObj.getString("q"+i+"_time");
			db.executeInsert(sql2, recordId, question, answer, times);
		}
		
		return recordId;
		//db.commit();
	}
	
	public void updateEmail(String recordId, String email)
	{
		String sql = "update records set email=? where id = ?";
		
		db.executeUpdate(sql, email, recordId);
	}
	
	public void insertRate(String recordId, String rate, String comment)
	{
		String sql2 = "insert into answers(record_id, question, answer, times) values(?, ? ,? ,?)";
		
		db.executeInsert(sql2, recordId, "rate", rate,  "");
		db.executeInsert(sql2, recordId, "comment", comment,  "");
	}
	
	public void close()
	{
		db.close();
	}
	
	public MySQLDBManager getDB()
	{
		return db;
	}
	
	public static void main(String[] args)
	{
		QuestionnaireDBImpl db = new QuestionnaireDBImpl();
		
		String sql = "insert into records(email, starttime, endtime, group) values(?, ?, ?, ?)";
		
		int recordId = db.getDB().executeInsertWithAI(sql, "test", "1", "2", "3");
	}
}
