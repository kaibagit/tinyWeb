package com.tinyweb.mvc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinyweb.Application;
import com.tinyweb.WebContext;
import com.tinyweb.mvc.render.HtmlRenderer;
import com.tinyweb.mvc.render.JsonRenderer;
import com.tinyweb.mvc.render.RenderType;
import com.tinyweb.utils.StringUtils;

import freemarker.template.TemplateException;

public class Dispatcher extends GenericServlet{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String,ActionDefinition> actionMap = new HashMap<String,ActionDefinition>();
	
	private ServletConfig config;
	
	public void init(ServletConfig config) throws ServletException{
		//创建Controller并与url对应
			//找出所有class
			//找出所有继承自Controller类的java类
			//根据路径名生成路径与java对象绑定
		this.config = config;
		String customViewsPath = config.getInitParameter("viewsPath");
		if(StringUtils.isNotBlank(customViewsPath)){
			HtmlRenderer.setViewsPath(customViewsPath);
		}
		
		String classPath = this.getClass().getResource("/").getPath();
		String scanPackage = config.getInitParameter("scanPackage");
		String scanPath = classPath+scanPackage.replaceAll("\\.", "/");
		 List<File> classFileList = new ControllerClassFinder(scanPath).find();
		 
		 List<String> controllerClassNameList = new ArrayList<String>();
		 for(File classFile:classFileList){
			 String classFilePath = classFile.getPath();
			 String className = classFilePath.replace(classPath, "")
					 .replace(new Character(File.separatorChar).toString() , ".")
					 .replace(".class", "");
			 controllerClassNameList.add(className);
		 }
		 
		 for(String className:controllerClassNameList){
			 try {
				Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
				if( Controller.class.isAssignableFrom(clazz) ){
					String controllerUrlPath = clazz.getName().replaceAll(scanPackage, "").toLowerCase().replaceFirst("\\.", "");
					if(controllerUrlPath.endsWith("_controller")){
						controllerUrlPath = controllerUrlPath.replaceAll("_controller", "");
					}else{
						controllerUrlPath.replace("controller", "");
					}
					
					Method[] methodArr = clazz.getMethods();
					for(Method method:methodArr){
						String actionUrlPath = method.getName().toString();
						String integralUrlPath = "/"+controllerUrlPath+"/"+actionUrlPath;
						ActionDefinition actionDefinition = new ActionDefinition(clazz,method,integralUrlPath);
						actionMap.put(integralUrlPath, actionDefinition);
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		 }
	}
	
	public ServletConfig getServletConfig(){
		return this.config;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		WebContext.putRequest(request );
		WebContext.putResponse(response);
		
		String requestUri = request.getServletPath();
		
		String uriSuffix = getUriSuffix(requestUri);
		if(".json".equalsIgnoreCase(uriSuffix)){
			WebContext.setRenderType(RenderType.Json);
		}else if(".xml".equalsIgnoreCase(uriSuffix)){
			WebContext.setRenderType(RenderType.Xml);
		}else{
			WebContext.setRenderType(RenderType.Html);
		}
		
		//去掉URI后缀
		int dotIndex = requestUri.lastIndexOf(".");
		if(dotIndex > 0){
			requestUri = requestUri.substring(0, dotIndex);
		}
		
		ActionDefinition actionDefinition = actionMap.get(requestUri);
		
		try {
			actionDefinition.getActionMethod().invoke(actionDefinition.getControllerClass().newInstance(), null);
			render(requestUri,response);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取URI的后缀
	 * @param uri
	 * @return
	 */
	private String getUriSuffix(String uri){
		String lastGroup = null;
		
		Pattern p = Pattern.compile("\\.\\w+");
		Matcher m = p.matcher(uri);
		
		boolean result = m.find();
		while (result) {
			lastGroup = m.group();
			result = m.find();
		}
		return lastGroup;
	}
	
	/**
	 * 渲染
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private void render(String requestUri,HttpServletResponse response) throws IOException{
		RenderType renderType = WebContext.getRenderType();
		if(renderType == RenderType.Html){
			new HtmlRenderer().render(WebContext.getAtttMap(), requestUri, response);
		}else if(renderType == RenderType.Json){
			new JsonRenderer().render(WebContext.getAtttMap(), requestUri, response);
		}
	}
	
}
