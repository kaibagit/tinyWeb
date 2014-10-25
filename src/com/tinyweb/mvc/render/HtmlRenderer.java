package com.tinyweb.mvc.render;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tinyweb.Application;
import com.tinyweb.RequestContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlRenderer implements Renderer{
	
	private static String viewsPath = "views";

	public static void setViewsPath(String viewsPath) {
		HtmlRenderer.viewsPath = viewsPath;
	}

	@Override
	public void render(Map<String,Object> data,String viewName,HttpServletResponse response) throws IOException {
		Configuration cfg = new Configuration();  
		cfg.setDirectoryForTemplateLoading(
				new File( 
						Application.getRootRealPath(viewsPath)
				)
		); 
		Template template = cfg.getTemplate(viewName+".ftl","utf-8");
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
