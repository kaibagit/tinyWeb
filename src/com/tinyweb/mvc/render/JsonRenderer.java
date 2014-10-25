package com.tinyweb.mvc.render;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class JsonRenderer implements Renderer{

	@Override
	public void render(Map<String, Object> data, String viewName,HttpServletResponse response) throws IOException {
		String json = JSON.toJSONString(data);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(json);
		response.getWriter().close();
	}

}
