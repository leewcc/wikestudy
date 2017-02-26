package com.wikestudy.model.util;

import java.sql.Date;
import java.util.Calendar;

public class TimeToot {
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    private static final String ONE_DAY_AGO = "天前";  
    private static final String ONE_MONTH_AGO = "月前";  
    private static final String ONE_YEAR_AGO = "年前";  
  
  
    public static String format(long time ) {
    	long delta = new java.util.Date().getTime() - time;
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 45L * ONE_MINUTE) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 24L * ONE_HOUR) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 48L * ONE_HOUR) {  
            return "昨天";  
        }  
        if (delta < 30L * ONE_DAY) {  
            long days = toDays(delta);  
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }  
        if (delta < 12L * 4L * ONE_WEEK) {  
            long months = toMonths(delta);  
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        } else {  
            long years = toYears(delta);  
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
        }  
    }  
  
    private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {  
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toMonths(long date) {  
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toMonths(date) / 365L;  
    }
	
		
	//获取每一周的星期一
	public static Date getMonday(int delay){
		Calendar c = Calendar.getInstance();		

		//获取今天是周几
		int dayW = c.get(Calendar.DAY_OF_WEEK);
		
		switch (dayW) {
		case Calendar.MONDAY:
			break;

		case Calendar.TUESDAY:
			c.add(Calendar.DAY_OF_MONTH, -1);
			break;
			
		case Calendar.WEDNESDAY:
			c.add(Calendar.DAY_OF_MONTH, -2);
			break;
			
		case Calendar.THURSDAY:
			c.add(Calendar.DAY_OF_MONTH, -3);
			break;
			
		case Calendar.FRIDAY:
			c.add(Calendar.DAY_OF_MONTH, -4);
			break;
			
		case Calendar.SATURDAY:
			c.add(Calendar.DAY_OF_MONTH, -5);
			break;
			
		case Calendar.SUNDAY:
			c.add(Calendar.DAY_OF_MONTH, -6);
			break;
			
		default:
			break;
		}
		
		c.add(Calendar.DAY_OF_MONTH, delay * (-7));
		return new Date(c.getTime().getTime());
	}
	
	public static int calculateHour(long time){
		int hour = (int)(time / 1000 / 60 /60);
		return hour;
	}
	
}
