package com.wikestudy.model.filter;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingFilter {
	/**
	 * @param 要过滤的字符
	 * @return 过滤之后的字符
	 */
	 public static String filter(String message) { 
	        if (message == null) 
	            return (null); 
	        char content[] = new char[message.length()]; 
	        message.getChars(0, message.length(), content, 0); 
	        StringBuffer result = new StringBuffer(content.length + 50); 
	        for (int i = 0; i < content.length; i++) { 
	            switch (content[i]) { 
	            case '<': 
	                result.append("&lt;"); 
	                break; 
	            case '>': 
	                result.append("&gt;"); 
	                break; 
	            case '&': 
	                result.append("&amp;"); 
	                break; 
	            case '"': 
	                result.append("&quot;"); 
	                break; 
	            default: 
	                result.append(content[i]); 
	            } 
	        } 
	        return (result.toString()); 
	    } 
	 /**
		 * @param 要检查的字符
		 * @return flase 非数字 true 数字
		 */
	 public static boolean isNumber(String num) {
		 if(num==null) return false;
		 String rex ="^\\d+$";
		 Pattern p=Pattern.compile(rex);
		 Matcher m=p.matcher(num);
		 if(m.find()) {
			 return true;
		 }
		 else return false;
	 }
	 public static void main(String[] args) {
		System.out.println(isNumber("0"));
	}

	 //匹配正整数
	 public static boolean isLargeTZero(String num){
		 if(num.matches("[1-9]{1}[0-9]*"))
			 return true;
		 
		return false;
	 }
}
