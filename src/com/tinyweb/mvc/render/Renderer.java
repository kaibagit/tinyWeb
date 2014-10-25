package com.tinyweb.mvc.render;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface Renderer {
	
	public void render(Map<String,Object> data,String viewName,HttpServletResponse response) throws IOException;
}
