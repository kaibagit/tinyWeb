package com.tinyweb.mvc;

import java.lang.reflect.Method;

public class ActionDefinition {
	
	private String url;
	private Class<?> controllerClass;
	private Method actionMethod;
	
	public ActionDefinition(Class<?> controllerClass,Method actionMethod,String url){
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
		this.url = url;
	}
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}
	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Method getActionMethod() {
		return actionMethod;
	}
	public void setActionMethod(Method actionMethod) {
		this.actionMethod = actionMethod;
	}
}
