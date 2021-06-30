package com.sample.models;

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
import com.tinyweb.exception.ValidateException;
import com.tinyweb.utils.StringUtils;

@Entity
@Table(name="members")
public class Member extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	public String name;
	public Integer age;
	private Boolean active;
	public String password_sign;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="author")
	public List<Article> articles;
	
	public Member(){
		active = true;
	}
	
	public void validate(){
		if(StringUtils.isBlank(name)){
			throw new ValidateException("name can't be empty");
		}
		if(age == null){
			throw new ValidateException("age can't be empty");
		}
		if(age<1){
			throw new ValidateException("age must be greater than 0");
		}
		if(age > 150){
			throw new ValidateException("age must be less than 150");
		}
	}
	
	public static Member find(Long id){
		return Repertory.find(Member.class, id);
	}

	public static Member findByName(String name) {
//		Query query = Repertory.db().createQuery("from Member where name=?");
//		query.setString()
		return null;
	}
	
	public static Member fetch(Long id){
		return Repertory.fetch(Member.class, id);
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
			throw new SecurityException("Only the author can delete the article.");
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
