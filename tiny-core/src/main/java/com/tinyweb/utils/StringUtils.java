package com.tinyweb.utils;

public class StringUtils {
	public static boolean isBlank(String string){
		if(string == null || string.trim().equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String string){
		return !isBlank(string);
	}
}
