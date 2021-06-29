package com.sample.controllers;

import java.util.List;

import com.sample.models.Member;
import com.tinyweb.Repertory;
import com.tinyweb.mvc.Controller;

public class Member_Controller extends Controller{
	
	public void index(){
		List<Member> members = Repertory.all(Member.class);
		addAttr("members", members);
		renderHtml();
	}
	
	public void add(){
	}
	
	public void create(){
		Member member = new Member();
		member.name = param("name");
		member.age = paramToInt("age");
		member.save();
		
		redirectTo("index");
	}
	
	public void edit(){
		Member member = Member.find(paramToLong("id"));
		addAttr("member", member);
	}
	
	public void update(){
		Member member = Member.find(paramToLong("id"));
		member.name = param("name");
		member.age = paramToInt("age");
		member.update();
		
		redirectTo("index");
	}
	
	public void show(){
		Long id = paramToLong("id");
		Member member = Repertory.find(Member.class, id);
		addAttr("member",member);
	}
	
	public void destroy(){
		Member member = Member.find(paramToLong("id"));
		member.destroy();
		
		redirectTo("index");
	}
}
