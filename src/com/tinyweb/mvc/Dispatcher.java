package com.tinyweb.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tinyweb.RenderType;
import com.tinyweb.RequestContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Dispatcher extends GenericServlet{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String,ActionDefinition> actionMap = new HashMap<String,ActionDefinition>();
	
	public void init(ServletConfig config) throws ServletException{
		//创建Controller并与url对应
			//找出所有class
			//找出所有继承自Controller类的java类
			//根据路径名生成路径与java对象绑定
		
		
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

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String requestUri = request.getServletPath();
		//去掉后缀
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
	 * 渲染
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private void render(String requestUri,HttpServletResponse response) throws IOException{
		RenderType renderType = RequestContext.getRenderType();
		if(renderType == RenderType.Html){
			Configuration cfg = new Configuration();  
			cfg.setDirectoryForTemplateLoading(new File("/home/kaiba/workspace/tinyWeb/WebContent/views"));  
			Template template = cfg.getTemplate(requestUri+".ftl","utf-8");
			try {
				StringWriter stringWriter = new StringWriter();
				BufferedWriter writer = new BufferedWriter(stringWriter); 
				template.process(RequestContext.getAtttMap(),writer);
				response.getWriter().println(stringWriter.toString());
				response.getWriter().close();
			} catch (TemplateException e) {
				throw new IOException(e);
			}
		}
	}
	
}
