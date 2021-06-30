package com.sample.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tinyweb.persistent.hibernate.Model;
import com.tinyweb.exception.ValidateException;
import com.tinyweb.utils.StringUtils;

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
	
	public void validate(){
		if(StringUtils.isBlank(title)){
			throw new ValidateException("title can't be empty");
		}
		if(StringUtils.isBlank(content)){
			throw new ValidateException("content can't be empty");
		}
	}
	
}
