package com.tinyweb;

import java.io.File;

public class Application {
	private static String rootRealPath;
	
	public static synchronized void setRootRealPath(String rootRealPath){
		if(Application.rootRealPath != null){
			throw new RuntimeException("应用的绝对地址已经被初始化，不能重复设置");
		}
		Application.rootRealPath = rootRealPath;
	}
	
	/**
	 * 获得根目录的绝对路径
	 * @return
	 */
	public static String getRootRealPath(){
		return rootRealPath;
	}
	
	/**
	 * 根据文件对根目录的相对路径，得到绝对路径
	 * @param relativePath
	 * @return
	 */
	public static String getRootRealPath(String relativePath){
		StringBuilder rootRealPath = new StringBuilder(getRootRealPath());
		if(!relativePath.startsWith("/") && !relativePath.startsWith("\\")){
			rootRealPath.append(File.separatorChar);
		}
		rootRealPath.append(relativePath);
		return rootRealPath.toString();
	}
	
}
