package com.tinyweb.mvc.render;

/**
 * 渲染类型
 * @author kaiba
 *
 */
public enum RenderType {
	None("none"),Text("text"),Html("html"),Json("json"),Xml("xml");
	
	private String typeName;
	
	private RenderType(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
