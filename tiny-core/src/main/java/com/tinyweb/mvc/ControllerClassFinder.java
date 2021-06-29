package com.tinyweb.mvc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControllerClassFinder {
	
	private String scanDir;
	
	private List<File> classFileList = new ArrayList<File>();
	
	public ControllerClassFinder(String scanDir){
		this.scanDir = scanDir;
	}
	
	public List<File> find(){
		findClasses(new File(scanDir));
		return classFileList;
	}
	
	private void findClasses(File dir){
		for(File file:dir.listFiles()){
			if( file.isFile() && file.getName().toLowerCase().endsWith("controller.class")){
				classFileList.add(file);
			}else if(file.isDirectory()){
				findClasses(file);
			}
		}
	}
}
