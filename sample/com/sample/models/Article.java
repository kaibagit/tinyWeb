package com.sample.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tinyweb.Model;
import com.tinyweb.Repertory;

@Entity
@Table(name="articles")
public class Article extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	public String title;
	public String content;
	public Date createAt;
	public Date updateAt;
	@ManyToOne
	public Member author;
	
	public Article(){
		Date now = new Date();
		createAt = now;
		updateAt = now;
	}
	
}
