package com.tinyweb;

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
	
	public static  void close(){
		factory.close();
	}
}
