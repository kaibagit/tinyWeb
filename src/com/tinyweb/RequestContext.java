package com.tinyweb;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tinyweb.mvc.render.RenderType;

public class RequestContext {
	
	private static ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>();
	
	private static ThreadLocal<Map<String,Object>> attrMaps = new ThreadLocal<Map<String,Object>>();
	
	private static ThreadLocal<RenderType> renderTypes = new ThreadLocal<RenderType>();
	
	public static void put(HttpServletRequest request){
		requests.set(request);
	}
	
	public static HttpServletRequest get(){
		return requests.get();
	}
	
	public static void addAttr(String attrName,Object attrValue){
		Map<String,Object> attrMap = attrMaps.get();
		if(attrMap == null){
			attrMap = new HashMap<String,Object>();
		}
		if(attrValue instanceof Model){
			Map<String,Object> modelProp = new HashMap<String,Object>();
			Class<?> modelClass = attrValue.getClass();
			Field[] fields = modelClass.getDeclaredFields();
			for(Field field:fields){
				field.setAccessible(true);
				try {
					Object value = field.get(attrValue);
					modelProp.put(field.getName(), value);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			attrMap.put(attrName, modelProp);
		}else{
			attrMap.put(attrName, attrValue);
		}
		attrMaps.set(attrMap);;
	}
	
	public static Map<String,Object> getAtttMap(){
		return attrMaps.get();
	}
	
	public static void setRenderType(RenderType renderType){
		renderTypes.set(renderType);
	}
	
	public static RenderType getRenderType(){
		return renderTypes.get();
	}
}
