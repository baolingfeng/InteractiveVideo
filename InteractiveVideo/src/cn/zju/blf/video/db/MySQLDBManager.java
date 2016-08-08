package cn.zju.blf.video.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MySQLDBManager {
	Logger logger = Logger.getLogger(MySQLDBManager.class);
	
	private Connection conn;
	
	public static String LOCAL_URL = "jdbc:mysql://127.0.0.1:3306/javadoc";
	public static String LOCAL_USER = "blf";
	public static String LOCAL_PASSWORD = "123456";
	
	public MySQLDBManager()
	{
		conn = getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASSWORD);
	}
	
	public MySQLDBManager(String url, String user, String password)
	{
		conn = getConnection(url, user, password);
	}
	
	public Connection getConnection(String url, String user, String password)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection(url, user, password);
		}catch(Exception e)
		{
			logger.info(e);
		}
		return null;
	}
	
	public void close()
	{
		try
		{
			conn.close();
		}catch(Exception e)
		{
			logger.info(e);
		}
	}
	
	public void commit()
	{
		try
		{
			conn.commit();
		}catch(Exception e)
		{
			logger.info(e);
		}
	}
	
	private void setPreparedStatement(PreparedStatement pstmt, Object... params) throws Exception
	{
		for(int i=0; i<params.length; i++)
		{
			Object o = params[i];
			if(o instanceof Integer)
			{
				pstmt.setInt(i+1, (Integer)o);
			}
			else if(o instanceof Double)
			{
				pstmt.setDouble(i+1, (Double)o);
			}
			else if(o instanceof Float)
			{
				pstmt.setFloat(i+1, (Float)o);
			}
			else if(o instanceof String)
			{
				pstmt.setString(i+1, (String)o);
			}
			else if(o instanceof Boolean)
			{
				pstmt.setBoolean(i+1, (Boolean)o);
			}
			else if(o instanceof Date)
			{
				pstmt.setDate(i+1, (Date)o);
			}
			else if(o == null)
			{
				pstmt.setNull(i+1, Types.INTEGER);
			}
		}
	}
	
	public void executeInsert(String sql, Object... params)
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			setPreparedStatement(pstmt, params);
			
			pstmt.execute();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void executeUpdate(String sql, Object... params)
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			setPreparedStatement(pstmt, params);
			
			pstmt.executeUpdate();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public int executeInsertWithAI(String sql, Object... params)
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			setPreparedStatement(pstmt, params);
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next())
			{
				return rs.getInt(1);
			}
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return -1;
	}
	
	public List<Map<String, Object>> executeQuery(String sql, Object... params)
	{
		try
		{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			setPreparedStatement(pstmt, params);
			
			ResultSet rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			while(rs.next())
			{
				Map<String, Object> results = new HashMap<String, Object>();
				for(int i=1; i<=rsmd.getColumnCount(); i++)
				{
					String columnName = rsmd.getColumnName(i);
					
					int type = rsmd.getColumnType(i);
					if(type == Types.VARCHAR || type == Types.CHAR || type == Types.LONGNVARCHAR)
					{
						results.put(columnName, rs.getString(i));
					}
					else if(type == Types.INTEGER)
					{
						results.put(columnName, rs.getInt(i));
					}
					else if(type == Types.DOUBLE || type == Types.DECIMAL || type == Types.FLOAT)
					{
						results.put(columnName, rs.getDouble(i));
					}
					else{
						results.put(columnName, rs.getString(i));
						//logger.info("unsupported type " + type + "/ columnName:" + columnName);
					}
				}
				
				ret.add(results);
			}
			
			return ret;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public int queryCount(String sql, Object... params)
	{
		List<Map<String, Object>> rs = this.executeQuery(sql, params);
		
		//System.out.println(rs.get(0).get("count(1)").getClass());
		
		return Integer.parseInt((String)rs.get(0).get("count(1)"));
	}
	
	public static void main(String args[])
	{
		MySQLDBManager db = new MySQLDBManager();
		db.executeInsert("insert into libraries(name) values(?)", "java.mail");
		
		//int count = db.queryCount("select count(1) from docs where lib_id = ? and class = ?", 1, "javax.naming.Referenceable");
		
		//System.out.println(count);
		
		db.close();
	}
}
