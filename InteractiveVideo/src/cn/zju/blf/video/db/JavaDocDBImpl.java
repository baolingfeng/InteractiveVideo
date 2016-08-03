package cn.zju.blf.video.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.zju.blf.video.code.MyJavaDoc;

public class JavaDocDBImpl {
	Logger logger = Logger.getLogger(JavaDocDBImpl.class);
	
	MySQLDBManager db;
	
	private static String JAVADOC_DB_URL = "jdbc:mysql://155.69.147.247:3306/hci";
	private static String JAVADOC_DB_USER = "blf";
	private static String JAVADOC_DB_PASSWORD = "123456";
	
	private void readDBConfig()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(JavaDocDBImpl.class.getResourceAsStream("/config/db.txt"))); 
			String line = br.readLine();

	        while (line != null) {
	            String[] params = line.split("=");
	        	if("JAVADOC_DB_URL".equals(params[0]))
	        	{
	        		JAVADOC_DB_URL = params[1];
	        	}
	        	else if("JAVADOC_DB_USER".equals(params[0]))
	        	{
	        		JAVADOC_DB_USER = params[1];
	        	}
	        	else if("JAVADOC_DB_PASSWORD".equals(params[0]))
	        	{
	        		JAVADOC_DB_PASSWORD = params[1];
	        	}
	            
	            line = br.readLine();
	        }
		}catch(Exception e)
		{
			logger.info("read db config error: " + e.getMessage());
		}
	}
	
	public JavaDocDBImpl()
	{
		readDBConfig();
		
		db = new MySQLDBManager(JAVADOC_DB_URL, JAVADOC_DB_USER, JAVADOC_DB_PASSWORD);
	}
	
	public List<MyJavaDoc> getJavadoc(String className)
	{
		List<MyJavaDoc> docs = new ArrayList<MyJavaDoc>();
		
		String sql = "select distinct b.name, a.* from docs a, libraries b "
				+ "where a.lib_id = b.id and a.class like ? and a.method is null";
		
		List<Map<String, Object>> rs = db.executeQuery(sql, '%'+className+'%');
		for(Map<String, Object> m : rs)
		{
			MyJavaDoc doc = new MyJavaDoc();
			
			doc.setLibrary((String)m.get("name"));
			doc.setClassName((String)m.get("class"));
			doc.setJavadoc(tranlateJavadoc((String)m.get("javadoc")));
			doc.setMethod(false);
			
			System.out.println(doc);
			
			if(docs.indexOf(doc) < 0)
			{
				if(doc.getClassName().endsWith(className))
				{
					docs.add(0, doc);
				}
				else{
					docs.add(doc);
				}
			}
		}
		
		return docs;
	}
	
	public List<MyJavaDoc> getJavadoc(String className, String method)
	{
		List<MyJavaDoc> docs = new ArrayList<MyJavaDoc>();
		
		String sql = "select distinct b.name, a.* from docs a, libraries b "
				+ "where a.lib_id = b.id and a.class like ? and a.method like ?";
		
		List<Map<String, Object>> rs = db.executeQuery(sql, '%'+className+'%', '%' + method + '%');
		for(Map<String, Object> m : rs)
		{
			MyJavaDoc doc = new MyJavaDoc();
			
			doc.setLibrary((String)m.get("name"));
			doc.setClassName((String)m.get("class"));
			doc.setJavadoc(tranlateJavadoc((String)m.get("javadoc")));
			doc.setMethodName((String)m.get("method"));
			doc.setMethodParams((String)m.get("param"));
			doc.setMethod(true);
			
			System.out.println(doc);
			if(docs.indexOf(doc) < 0)
			{
				if(doc.getClassName().endsWith(className))
				{
					docs.add(0, doc);
				}
				else{
					docs.add(doc);
				}
			}
		}
		
		return docs;
	}
	
	public List<MyJavaDoc> getJavadocByMethod(String method)
	{
		List<MyJavaDoc> docs = new ArrayList<MyJavaDoc>();
		
		String sql = "select b.name, a.* from docs a, libraries b "
				+ "where a.lib_id = b.id and a.method like ?";
		
		List<Map<String, Object>> rs = db.executeQuery(sql, '%' + method + '%');
		for(Map<String, Object> m : rs)
		{
			MyJavaDoc doc = new MyJavaDoc();
			
			doc.setLibrary((String)m.get("name"));
			doc.setClassName((String)m.get("class"));
			doc.setJavadoc(tranlateJavadoc((String)m.get("javadoc")));
			doc.setMethodName((String)m.get("method"));
			doc.setMethodParams((String)m.get("param"));
			doc.setMethod(true);
			
			System.out.println(doc);
			
			if(docs.indexOf(doc) < 0)
			{
				if(doc.getMethodName().endsWith(method))
				{
					docs.add(0, doc);
				}
				else{
					docs.add(doc);
				}
			}
		}
		
		return docs;
	}
	
	public List<MyJavaDoc> getJavaDocWithQuery(String query)
	{
		List<MyJavaDoc> l1 = getJavadoc(query);
		List<MyJavaDoc> l2 = getJavadocByMethod(query);
		
		l1.addAll(l2);
		
		return l1;
	}
	
	private String tranlateJavadoc(String doc)
	{
		String arr[] = doc.split("\\n");
		
		String res = "";
		for(int i=0; i<arr.length; i++)
		{
			if(arr[i].trim().endsWith("*/"))
			{
				res += arr[i].trim().substring(0, arr[i].trim().length()-2);
			}
			else if(arr[i].trim().startsWith("*"))
			{
				res += arr[i].trim().substring(1);
			}
			else if(arr[i].trim().startsWith("/**"))
			{
				res += arr[i].trim().substring(3);
			}
			else
			{
				res += arr[i].trim();
			}
			
			res += "<br/>";
		}
		
		return res;
	}
	
	public void close()
	{
		db.close();
	}
}
