package com.gala.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.dataLoader.MongoDbWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetriever implements IDataRetriever<Station> {

	static final Logger _logger = Logger.getLogger(MongoDbDataRetriever.class);
	
	protected boolean 						_isInitialized;
	protected DB 							_db;
	protected MongoClient 					_client;
	protected String 						_dbName;
	protected List<String> 					_collectionNames;
	protected HashMap<String, DBCollection> _collections;
	
	public MongoDbDataRetriever(final MongoClient client_, final String dbName_, 
			final List<String> collNames_){
		_client = client_;
		_dbName = dbName_;
		_collectionNames = collNames_;
		_isInitialized = false;
		_collections = new HashMap<String, DBCollection>();
	}
	
	public boolean init(){
		
		try {
			_db = _client.getDB(_dbName);
			
			for (String collName : _collectionNames){
				_collections.put(collName, _db.getCollection(collName));
			}
			
			_isInitialized = true;
		} catch (Exception e) {
			_logger.error("Exception thrown while initializing MongoDbWriter: " + e);
			return false;
		}
		return true;
	}
	
	public List<Station> retrieveData(Object[] query_){
		
		List<Station> list = new ArrayList<Station>();
		
		String collName_ = "stations";
		DBCollection coll = _collections.get(collName_);
		if (coll == null) {
			_logger.warn("Collection not found: " + collName_);
			return null;
		}
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", "5");
		DBCursor cursor = coll.find(query);
		
		while(cursor.hasNext()){
			DBObject objRet = cursor.next();
			list.add(new Station(objRet.get("station").toString(), 
					Integer.parseInt(objRet.get("_id").toString()), 
					Double.parseDouble(objRet.get("lat").toString()), 
					Double.parseDouble(objRet.get("lng").toString())));
		}
		
		return list;
	}
}
