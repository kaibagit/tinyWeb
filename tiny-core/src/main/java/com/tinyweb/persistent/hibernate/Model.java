package com.tinyweb.persistent.hibernate;

import java.util.Date;


public abstract class Model {
	
	public Date createAt;
	public Date updateAt;
	
	public Model(){
		Date now = new Date();
		createAt = now;
		updateAt = now;
	}
	
	public void validate(){
	}
	
	public void save(){
		this.validate();
		Repertory.db().save(this);
	}
	
	public void update(){
		this.validate();
		updateAt = new Date();
		Repertory.db().flush();
	}
	
	public void destroy(){
		Repertory.db().delete(this);
	}
}
