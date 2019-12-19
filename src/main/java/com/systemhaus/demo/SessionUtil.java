package com.systemhaus.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {

	private static SessionUtil instance=new SessionUtil();
    private SessionFactory factory;  
	
    public static SessionUtil getInstance(){
            return instance;
    }
    
    public SessionUtil () {
    	Configuration conf = new Configuration().configure(this.getClass().getResource("/hibernate.cfg.xml"));
        factory = conf.buildSessionFactory();  
    }
	
    public Session getSession() {
    	return factory.openSession();
    }
    
    
    
}
