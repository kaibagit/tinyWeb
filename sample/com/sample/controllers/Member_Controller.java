package com.sample.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.models.Member;
import com.tinyweb.Repertory;
import com.tinyweb.mvc.Controller;

public class Member_Controller extends Controller{
	
	public void index(){
		List<Member> members = Repertory.all(Member.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Member m:members){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("id", m.id);
			cell.put("name", m.name);
			cell.put("age", m.age);
			list.add(cell);
		}
		
		addAttr("members", list);
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
		addAttr("id", member.id);
		addAttr("name", member.name);
		addAttr("age", member.age);
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
