package com.wikestudy.model.util;

import java.text.NumberFormat;

public class InputUtil {
	private static NumberFormat nt = NumberFormat.getPercentInstance();
	static{
		nt.setMinimumFractionDigits(0);
	}
	
	public static String inputNum(double num){
		return nt.format(num);
	}
	
	public static String input(Object o) {
		if(o == null)
			return "";
		
		return o.toString();
	}
	
	public static String input(String s) {
		if(s == null)
			return "";
		
		return s;
	}
	
	
	public static String inputPhoto(String photo, int id, boolean type) {
		if(photo == null || "".equals(photo))
			return "/wikestudy/dist/images/portrait/defualt.jpg";
		else{
			if(type)
				return  "/wikestudy/dist/images/portrait/" + "t" + id + "/"+ photo;
			else
				return  "/wikestudy/dist/images/portrait/" + "s" + id + "/"+ photo;
		}
	}
	
	private static final long DAY = 1000 * 3600 * 12;
	private static final long HOUR = 1000 * 3600;
	private static final long MINUTE = 1000 * 60;
	
	public static String inputTime(long time) {
		int day = 0;
		int hour = 0;
		int minute = 0;
		
		day = (int)(time / DAY);
		if(day > 0){
			time -= day * DAY;
			hour = (int)(time / hour);
			if(hour > 0)
				return day + "天 " + hour + "小时";
			
			else{
				minute = (int)(time / MINUTE);
				return day + "天 " + minute + "分钟";
			}
				
		}else{
			hour = (int)(time / HOUR);
			if(hour > 0){
				time -= hour * HOUR;
				minute = (int)(time/MINUTE);
				if(minute > 0)
					return hour + "小时 " + minute + "分钟";
				else
					return hour + "小时";
			}else{
				minute = (int)(time / MINUTE);
				return minute + "分钟";
			}
		}
	}
	
	private static final int SIZE = 1024;
	public static String inputSize(long size) {
		long s = size / SIZE ;
		if(s >= SIZE){
			s = s / SIZE;
			if(s >= SIZE){
				s /= SIZE;
				return s + "GB";
			}else
				return s + "MB";
				
		}else
			return s + "KB";
			
	}
	
	public static String convert(String strData){
		if (strData == null)
	    {
	        return "";
	    }
	    strData = replaceString(strData, "&", "&amp;");
	    strData = replaceString(strData, "<", "&lt;");
	    strData = replaceString(strData, ">", "&gt;");
	    strData = replaceString(strData, "'", "&apos;");
	    strData = replaceString(strData, "\"", "&quot;");
	    return strData;
	}
	
	public static String replaceString(String strData, String regex, String replacement)
	{
	    if (strData == null)
	    {
	        return null;
	    }
	    int index;
	    index = strData.indexOf(regex);
	    String strNew = "";
	    if (index >= 0)
	    {
	        while (index >= 0)
	        {
	            strNew += strData.substring(0, index) + replacement;
	            strData = strData.substring(index + regex.length());
	            index = strData.indexOf(regex);
	        }
	        strNew += strData;
	        return strNew;
	    }
	    return strData;
	}
	
}
