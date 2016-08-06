package cn.zju.blf.video.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static double calcTimeDiff(String t1, String t2, String format)
	{
		try
		{
			SimpleDateFormat df=new SimpleDateFormat(format);
			return (df.parse(t2).getTime() - df.parse(t1).getTime()) * 1.0 / 1000;
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String timeToStr(Date d, String format)
	{
		DateFormat df = new SimpleDateFormat(format);

		return df.format(d);

	}
	
	public static double calcTimeDiff(String t1, String t2)
	{
		return calcTimeDiff(t1, t2, "YYYY-MM-dd HH:mm:ss.SSS");
	}
	
	public static void main(String args[])
	{
		double d = TimeUtil.calcTimeDiff("2016-07-01 12:20:10.120", "2016-07-01 12:31:15.300", "YYYY-MM-dd HH:mm:ss.SSS");
		
		System.out.println(d);
	}
}	
