package com.wikestudy.test;

import java.util.regex.Pattern;

// 天信用的一些字符串的匹配
public class TianXinRegularExp {
	
	// 检验字符串是否是纯数字
	public static boolean matchNumber(String number) {
        return  Pattern.matches("[0-9]+", number);
	}
	
	public static boolean matchNumber(String number1, String number2, String number3) {
        return  Pattern.matches("[0-9]+", number1) 
        		&& Pattern.matches("[0-9]+", number2) 
        		&& Pattern.matches("[0-9]+", number3);
	}
}
