package com.tinyweb;


public abstract class Model {
	
	public void save(){
		Repertory.db().save(this);
	}
	
	public void remove(){
		Repertory.db().delete(this);
	}
}
