package cn.zju.blf.video;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GlobalSetting {
	
	private Logger logger = Logger.getLogger(GlobalSetting.class);
	
	private static GlobalSetting singleton = null;
	
	public boolean IS_QUESTIONNAIRE;
	
	public static GlobalSetting getInstance()
	{
		if(singleton == null)
		{
			synchronized (GlobalSetting.class) {
				if(singleton == null)
				{
					singleton = new GlobalSetting();
				}
			}
		}
		return singleton;
	}
	
	private GlobalSetting()
	{
		init();
	}
	
	private void init()
	{
		try
		{
			Properties props = new Properties();
			
			props.load(new InputStreamReader(GlobalSetting.class.getResourceAsStream("/config/setting.properties")));
			
			IS_QUESTIONNAIRE = Boolean.parseBoolean(props.getProperty("questionnaire"));
			
		}catch(Exception e)
		{
			logger.info("read properties error: " + e.getMessage());
		}
	}
}
