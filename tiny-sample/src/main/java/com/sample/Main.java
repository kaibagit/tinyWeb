package com.sample;

import com.sample.models.Article;
import com.sample.models.Member;
import com.tinyweb.persistent.hibernate.Repertory;


public class Main {
	public static void main(String[] args){
		Repertory.beginTransaction();
		
		Member m = new Member();
		m.name = "luliru";
		m.age = 20;
		m.save();
		
		Article article = new Article();
		article.title = "国庆放假7天";
		article.content = "啦啦啦啦啦";
		
		m.publish(article);
		
		Repertory.commitTransaction();
		
		Repertory.beginTransaction();
		
		Member m2 = Repertory.find(Member.class,m.id);
		System.out.println(m2.name);
		System.out.println(m2.articles.get(0).title);
		
		Repertory.commitTransaction();
		
		Repertory.close();
	}
}
