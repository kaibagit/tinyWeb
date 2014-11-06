package com.tinyweb;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		String encoding = context.getInitParameter("encoding");
		if(encoding == null){
			encoding = "utf-8";
		}
		
		Application.setRootRealPath(context.getRealPath("/"));
		Application.setEncoding(encoding);
	}

}
