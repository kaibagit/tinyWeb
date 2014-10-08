package com.sample.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tinyweb.Model;
import com.tinyweb.Repertory;

@Entity
@Table(name="members")
public class Member extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	public String name;
	public Integer age;
	public Date createAt;
	public Date updateAt;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="author")
	public List<Article> articles;
	
	public Member(){
		Date now = new Date();
		createAt = now;
		updateAt = now;
	}
	
	public static Member get(Long id){
		return (Member) Repertory.db().get(Member.class, id);
	}

	public void publish(Article article){
		article.author = this;
		article.save();
	}
	
	public void deleteArticle(Article article){
		if(this.id.equals(article.author.id)){
			article.remove();
		}else{
			throw new RuntimeException("Only the author can delete zhe article.");
		}
	}
}
