package com.gala.logicEngine;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public abstract class MongoDbDataRetriever<T> implements IDataRetriever <T> {

	static final Logger _logger = Logger.getLogger(MongoDbDataRetriever.class);
	
	protected boolean 						_isInitialized;
	protected DB 							_db;
	protected MongoClient 					_client;
	protected String 						_dbName;
	protected List<String> 					_collectionNames;
	protected HashMap<String, DBCollection> _collections;
	protected MongoDbQueryBuilder			_mongoDbQueryBuilder;
	
	public MongoDbDataRetriever(final MongoClient client_, final String dbName_, 
			final List<String> collNames_, final MongoDbQueryBuilder mongoDbQueryBuilder_){
		_client = client_;
		_dbName = dbName_;
		_collectionNames = collNames_;
		_mongoDbQueryBuilder = mongoDbQueryBuilder_;
		_isInitialized = false;
		_collections = new HashMap<String, DBCollection>();
	}
	
	protected boolean init(){
		
		try {
			_db = _client.getDB(_dbName);
			
			for (String collName : _collectionNames){
				_collections.put(collName, _db.getCollection(collName));
			}
			
			_isInitialized = true;
		} catch (Exception e) {
			_logger.error("Exception thrown while initializing MongoDbDataRetriever: " + e);
			return false;
		}
		return true;
	}
	
	protected DBCursor retrieveCursor(final QueryType queryType_, final MongoDbQueryParameters params_){
		MongoDbQueryObject queryObject = _mongoDbQueryBuilder.buildQuery(queryType_, params_);
		
		if (queryObject == null){
			_logger.warn(String.format("Unable to build query object using query type %s and the provided mongoQueryParams", queryType_));
			return null;
		}
		
		DBCollection coll = _collections.get(queryObject._collectionName);
		if (coll == null) {
			_logger.warn(String.format("Unable to retrieve collection %s using query type %s and the provided mongoQueryParams", queryObject._collectionName, queryType_));
			return null;
		}		
		
		DBCursor cursor = coll.find(queryObject._queryObject, queryObject._fieldsRequest);
		
		if (cursor == null){
			_logger.warn(String.format("Unable to retrieve DB cursor from collection %s with query type %s and the provided mongoQueryParams", queryObject._collectionName, queryType_));
			return null;
		}
		
		return cursor;
	}
	

}
