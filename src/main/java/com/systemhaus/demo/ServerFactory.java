package com.systemhaus.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerFactory {
	
	private static ApplicationContext context;
	
	public static Server createServer() {
		if (context == null) {
			context  = new ClassPathXmlApplicationContext("/application-context.xml");
		}
		return context.getBean(Server.class);
	}

}
