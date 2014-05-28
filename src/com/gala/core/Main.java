package com.gala.core;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
	
	static final Logger _logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		_logger.info("Launching application...");
		
		try {
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("appContext.xml");
			appContext.registerShutdownHook();
		} catch (Exception e) {
			_logger.error(e);
		}
		
		_logger.info("Exiting Main...");
	}

}
