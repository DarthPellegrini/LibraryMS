package com.systemhaus.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionUtil {

	private static SessionUtil instance=new SessionUtil();
	private StandardServiceRegistry ssr;  
    private Metadata meta;  
    private SessionFactory factory;  
	
    public static SessionUtil getInstance(){
            return instance;
    }
    
    public SessionUtil () {
    	ssr = new StandardServiceRegistryBuilder().configure(this.getClass().getResource("/hibernate.cfg.xml")).build();  
        meta = new MetadataSources(ssr).getMetadataBuilder().build();  
        factory = meta.getSessionFactoryBuilder().build();  
    }
	
    public Session getSession() {
    	return factory.openSession();
    }
    
    
    
}
