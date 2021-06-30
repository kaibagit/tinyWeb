package com.tinyweb.persistent.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class Repertory {
	
	private static SessionFactory factory;
	
	private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
	
	static{
		Configuration cfg = new AnnotationConfiguration().configure();
		factory = cfg.buildSessionFactory();
	}
	
	public static Session db(){
		return sessions.get();
	}
	
	/**
	 * 根据主键获取对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T find(Class<T> clazz,Serializable id){
		return (T) db().get(clazz, id);
	}
	
	/**
	 * 根据主键获取对象，如果不存在，则抛出异常
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fetch(Class<T> clazz,Serializable id){
		T t = (T) db().get(clazz, id);
		if(t == null){
			throw new RuntimeException(clazz+" Serializable="+id+" not existed");
		}
		return t;
	}
	
	/**
	 * 获取所有对象
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> all(Class<T> clazz){
		Query query = db().createQuery("from ".concat(clazz.getName()));
		return query.list();
	}
	
	public static void beginTransaction(){
		Session session = factory.openSession();
		session.beginTransaction();
		
		sessions.set(session);
	}
	
	public static void commitTransaction(){
		Session session = sessions.get();
		session.getTransaction().commit();
		session.close();
	}
	
	public static void rollbackTransaction(){
		Session session = sessions.get();
		session.getTransaction().rollback();
		session.close();
	}
	
	public static  void close(){
		factory.close();
	}
}
