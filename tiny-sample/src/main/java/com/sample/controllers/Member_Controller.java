package com.sample.controllers;

import java.util.List;

import com.sample.models.Member;
import com.sample.service.SigninService;
import com.tinyweb.persistent.hibernate.Repertory;
import com.tinyweb.error.Result;
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

	public void signin() {
		String name = param("name");
		String password_sign = param("password_sign");

		Result<Member> result = SigninService.signin(name, password_sign);
		result.except("wrong-password", e -> {
			redirectTo("signin");
		}).success(member -> {
			redirectTo("index");
		});
	}
}
