package com.gala.core;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
	
	static final Logger _logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		_logger.info("Hello world!");
		
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("appContext.xml");
		HubwayUser user = appContext.getBean("springTestUser", HubwayUser.class);
		_logger.info("Test user info id: " + user.getUserId() + " age: " + user.getAge());
		
		try {
			MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://defaultUser:defaultUser@ds033459.mongolab.com:33459/hubwayhackathon"));
			
			DB db = mongo.getDB("hubwayhackathon");
			DBCollection coll = db.getCollection("testCollection");
			
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
			    .append("type", "database")
			    .append("count", 1)
			    .append("time", new Date().toString())
			    .append("info", new BasicDBObject("x", 203).append("y", 102));
			coll.insert(doc);

			DBObject myDoc = coll.findOne();
			_logger.info(myDoc);
					
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		_logger.info("Exiting main...");
	}

}
