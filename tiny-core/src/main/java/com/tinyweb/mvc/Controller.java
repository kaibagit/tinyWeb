package com.tinyweb.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinyweb.WebContext;
import com.tinyweb.mvc.render.RenderType;

public abstract class Controller {
	//自动在classpath下找到继承Controller的类
	//自动根据类名和方法吗映射成url
	//也可以根据注解映射
	
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	
	/**
	 * 获取参数
	 * @param paramName
	 * @return
	 */
	public String param(String paramName){
		HttpServletRequest request = WebContext.getRequest();
		return request.getParameter(paramName);
	}
	
	/**
	 * 获取Integer型参数
	 * @param paramName
	 * @return
	 */
	public Integer paramToInt(String paramName){
		String paramValue = param(paramName);
		if(paramValue != null){
			return Integer.valueOf(paramValue);
		}
		return null;
	}
	
	/**
	 * 获取Long型参数
	 * @param paramName
	 * @return
	 */
	public Long paramToLong(String paramName){
		String paramValue = param(paramName);
		if(paramValue != null){
			return Long.valueOf(paramValue);
		}
		return null;
	}
	
	/**
	 * 获取Boolean型参数
	 * @param paramName
	 * @return
	 */
	public Boolean paramToBool(String paramName){
		String paramValue = param(paramName);
		if(paramValue != null){
			return Boolean.valueOf(paramValue);
		}
		return null;
	}
	
	/**
	 * 获取Float型参数
	 * @param paramName
	 * @return
	 */
	public Float paramToFloat(String paramName){
		String paramValue = param(paramName);
		if(paramValue != null){
			return Float.valueOf(paramValue);
		}
		return null;
	}
	
	/**
	 * 获取Double型参数
	 * @param paramName
	 * @return
	 */
	public Double paramToDouble(String paramName){
		String paramValue = param(paramName);
		if(paramValue != null){
			return Double.valueOf(paramValue);
		}
		return null;
	}
	
	/**
	 * 添加属性，用于页面展现
	 * @param attrName
	 * @param attrValue
	 */
	public void addAttr(String attrName,Object attrValue){
		WebContext.addAttr(attrName, attrValue);
	}
	
	public void render(){
		
	}
	
	public void renderText(){
		WebContext.setRenderType(RenderType.Text);
	}
	
	public void renderHtml(){
		WebContext.setRenderType(RenderType.Html);
	}
	
	public void renderJson(){
		WebContext.setRenderType(RenderType.Json);
	}
	
	public void renderJson(Object data){
		WebContext.setRenderType(RenderType.Json);
	}
	
	public void renderXml(){
		WebContext.setRenderType(RenderType.Xml);
	}
	
	public void renderXml(Object data){
		WebContext.setRenderType(RenderType.Xml);
	}
	
	/**
	 * 重定向
	 * @param redirectPath
	 */
	public void redirectTo(String redirectPath){
		HttpServletResponse response = WebContext.getResponse();
		try {
			response.sendRedirect(redirectPath);
		} catch (IOException e) {
			logger.error("There is a error when redirecting", e);
		}
	}
}
