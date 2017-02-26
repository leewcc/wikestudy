package com.wikestudy.model.util;

public class SaltValue {

	public static String saltValue="5#%#%!2314%&%2";
	
	public static String getSaltValue(String value) {
		return MD5.getMD5Code(value+saltValue);
	}
	
}
