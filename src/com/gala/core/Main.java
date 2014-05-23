package com.gala.core;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Hello world!");
		System.out.println("Hello world again!");
		
		try {
			MongoClient mongo = new MongoClient("localhost");
			
			DB db = mongo.getDB("testDatabase");
			DBCollection coll = db.getCollection("testCollection");
			
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
			    .append("type", "database")
			    .append("count", 1)
			    .append("info", new BasicDBObject("x", 203).append("y", 102));
			coll.insert(doc);

			DBObject myDoc = coll.findOne();
			System.out.println(myDoc);
					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Exiting main...");
	}

}
