package com.sample.controllers;

import java.util.List;

import com.sample.models.Member;
import com.tinyweb.Repertory;
import com.tinyweb.mvc.Controller;

public class Member_Controller extends Controller{
	
	public void index(){
		List<Member> members = Repertory.all(Member.class);
		addAttr("members", members);
	}
	
	public void show(){
		Long id = paramToLong("id");
		Member member = Repertory.find(Member.class, id);
		addAttr("member",member);
		renderHtml();
	}
}
