package com.tinyweb.mvc;

import javax.servlet.http.HttpServletRequest;

import com.tinyweb.RequestContext;
import com.tinyweb.mvc.render.RenderType;

public abstract class Controller {
	//自动在classpath下找到继承Controller的类
	//自动根据类名和方法吗映射成url
	//也可以根据注解映射
	
	/**
	 * 获取参数
	 * @param paramName
	 * @return
	 */
	public String param(String paramName){
		HttpServletRequest request = RequestContext.get();
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
		RequestContext.addAttr(attrName, attrValue);
	}
	
	public void render(){
		
	}
	
	public void renderText(){
		RequestContext.setRenderType(RenderType.Text);
	}
	
	public void renderHtml(){
		RequestContext.setRenderType(RenderType.Html);
	}
	
	public void renderJson(){
		RequestContext.setRenderType(RenderType.Json);
	}
	
	public void renderJson(Object data){
		RequestContext.setRenderType(RenderType.Json);
	}
	
	public void renderXml(){
		RequestContext.setRenderType(RenderType.Xml);
	}
	
	public void renderXml(Object data){
		RequestContext.setRenderType(RenderType.Xml);
	}
}
