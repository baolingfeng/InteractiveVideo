package cn.zju.blf.video.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.zju.blf.video.dao.FocusUIAction;
import cn.zju.blf.video.dao.LowLevelEvent;

public class SQLiteDBManager {
	private Connection conn;
	
	public SQLiteDBManager(String dbFile)
	{
		try{
			Class.forName("org.sqlite.JDBC");
			
			System.out.println(dbFile);
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public List<LowLevelEvent> queryMouseEvent()
	{
		return queryEvent("select *, b.timestamp t_flag, 'MOUSE' as type from tbl_mouse_event a left join tbl_click_action b on a.timestamp = b.timestamp");
	}
	
	public List<LowLevelEvent> queryKeyboardEvent()
	{
		return queryEvent("select *, b.timestamp t_flag, 'KEY' as type from tbl_key_event a left join tbl_key_action b on a.timestamp = b.timestamp");
	}
	
	public List<LowLevelEvent> queryEvent(String sql)
	{
		try{
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			List<LowLevelEvent> events = new ArrayList<LowLevelEvent>();
			while(rs.next())
			{
				if("UNKNOWN".equals(rs.getString("event_name")))
				{
					continue;
				}
				
				LowLevelEvent e = new LowLevelEvent();
				e.setTimestamp(rs.getString("timestamp"));
				e.setEventName(rs.getString("event_name"));
				e.setPx(rs.getInt("p_x"));
				e.setPy(rs.getInt("p_y"));
				e.setWindowName(rs.getString("window_name"));
				e.setWinRectLeft(rs.getInt("win_rect_left"));
				e.setWinRectTop(rs.getInt("win_rect_top"));
				e.setWinRectRight(rs.getInt("win_rect_right"));
				e.setWinRectBottom(rs.getInt("win_rect_bottom"));
				e.setProcessName(rs.getString("process_name"));
				e.setParentWindow(rs.getString("parent_window"));
				e.setType(rs.getString("type"));
				
				if(!(rs.getString("t_flag") == null || "".equals(rs.getString("t_flag"))))
				{
					FocusUIAction action = new FocusUIAction();
					action.setActionName(rs.getString("action_name"));
					action.setActionType(rs.getString("action_type"));
					action.setActionValue(rs.getString("action_value"));
					action.setBoundLeft(rs.getInt("bound_left"));
					action.setBoundTop(rs.getInt("bound_top"));
					action.setBoundRight(rs.getInt("bound_right"));
					action.setBoundBottom(rs.getInt("bound_bottom"));
					action.setParentActionName(rs.getString("action_parent_name"));
					action.setParentActionType(rs.getString("action_parent_type"));
					
					e.setAction(action);
					//System.out.println(action);
				}
				
				events.add(e);
				
				//System.out.println(e);
			}
			
			return events;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void close() 
	{
		try{
			conn.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[])
	{
		SQLiteDBManager db = new SQLiteDBManager(SQLiteDBManager.class.getResource("/log.db3").getPath());
		db.queryKeyboardEvent();
		db.close();
	}
}
