package com.sample.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Query;

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
	private Boolean active;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="author")
	public List<Article> articles;
	
	public Member(){
		Date now = new Date();
		createAt = now;
		updateAt = now;
		active = true;
	}
	
	public static Member find(Long id){
		return Repertory.find(Member.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Member> findActive(){
		Query query = Repertory.db().createQuery("from Member where active=true");
		return query.list();
	}

	public void publish(Article article){
		article.author = this;
		article.save();
	}
	
	public void deleteArticle(Article article){
		if(this.id.equals(article.author.id)){
			article.destroy();
		}else{
			throw new RuntimeException("Only the author can delete the article.");
		}
	}
	
	public void activate(){
		active = true;
	}
	
	public void deactivate(){
		active = false;
	}
	
	public boolean isActive(){
		return active;
	}
}
