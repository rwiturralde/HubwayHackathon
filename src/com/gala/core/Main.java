package com.gala.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoServerSelectionException;
import com.mongodb.ServerAddress;

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
			//MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://defaultUser:defaultUser@ds033459.mongolab.com:33459/hubwayhackathon"));
			
			ServerAddress server = new ServerAddress("ds033459.mongolab.com", 33459);
			List<MongoCredential> creds = new ArrayList<MongoCredential>();
			creds.add(MongoCredential.createMongoCRCredential("defaultUser", "hubwayhackathon", "defaultUser".toCharArray()));
			
			MongoClient mongo = new MongoClient(server, creds);
			
			DB db = mongo.getDB("hubwayhackathon");
			
			DBCollection coll = db.getCollection("testCollection");
			
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
			    .append("type", "database")
			    .append("count", 1)
			    .append("time", new Date().toString())
			    .append("info", new BasicDBObject("userId", user.getUserId()).append("age", user.getAge()));
			coll.insert(doc);

			DBObject myDoc = coll.findOne();
			_logger.info(myDoc);
					
		} catch (UnknownHostException e) {
			_logger.error(e);
		}
		
		_logger.info("Exiting main...");
	}

}
