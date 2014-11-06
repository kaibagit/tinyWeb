package com.tinyweb;


public abstract class Model {
	
	public void save(){
		Repertory.db().save(this);
	}
	
	public void update(){
		Repertory.db().flush();
	}
	
	public void destroy(){
		Repertory.db().delete(this);
	}
}
