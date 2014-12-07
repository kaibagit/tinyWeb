package com.tinyweb.mvc.render;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinyweb.Application;
import com.tinyweb.WebContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlRenderer implements Renderer{
	
	private static Logger logger = LoggerFactory.getLogger(HtmlRenderer.class);
	
	private static String viewsPath = "views";

	public static void setViewsPath(String viewsPath) {
		HtmlRenderer.viewsPath = viewsPath;
	}

	@Override
	public void render(Map<String,Object> data,String viewName,HttpServletResponse response) throws IOException {
		File freemarkerViewFile = new File(Application.getRootRealPath(viewsPath+"/"+viewName+".ftl"));
		if(freemarkerViewFile.exists()){
			renderByFreemarker(data,viewName,response);
		}else{
			renderByJsp(data,viewName,response);
		}
	}
	
	private void renderByJsp(Map<String,Object> data,String viewName,HttpServletResponse response) throws IOException{
		try {
			HttpServletRequest request = WebContext.getRequest();
			for(Map.Entry<String, Object> cell:data.entrySet()){
				request.setAttribute(cell.getKey(), cell.getValue());
			}
			request.getRequestDispatcher("/"+viewsPath+"/"+viewName+".jsp").forward(request, response);
		} catch (ServletException e) {
			throw new IOException(e);
		}
	}
	
	private void renderByFreemarker(Map<String,Object> data,String viewName,HttpServletResponse response) throws IOException {
		Configuration cfg = new Configuration();  
		cfg.setDirectoryForTemplateLoading(
				new File( 
						Application.getRootRealPath(viewsPath)
				)
		); 
		
		Template template = null;
		
		try{
			 template = cfg.getTemplate(viewName+".ftl",Application.getEncoding());
		}catch(IOException e){
			logger.info("template {} not exist", viewName,e);
		}
		
		if(template != null ){
			try {
				StringWriter stringWriter = new StringWriter();
				BufferedWriter writer = new BufferedWriter(stringWriter); 
				template.process(WebContext.getAtttMap(),writer);
				response.setCharacterEncoding(Application.getEncoding());
				response.getWriter().println(stringWriter.toString());
				response.getWriter().close();
			} catch (TemplateException e) {
				throw new IOException(e);
			}
		}
	}

}
