package com.gala.logicEngine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public abstract class MongoDbDataRetrieverBase<T> implements IDataRetriever <T> {

	static final Logger _logger = Logger.getLogger(MongoDbDataRetrieverBase.class);
	
	protected boolean 						_isInitialized;
	protected DB 							_db;
	protected MongoClient 					_client;
	protected String 						_dbName;
	protected List<String> 					_collectionNames;
	protected HashMap<String, DBCollection> _collections;
	protected MongoDbQueryBuilder			_mongoDbQueryBuilder;
	
	public MongoDbDataRetrieverBase(final MongoClient client_, final String dbName_, 
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
	
	protected Iterator<DBObject> retrieveCursor(final MongoDbQueryParameters params_){
		MongoDbQueryObject queryObject = _mongoDbQueryBuilder.buildQuery(params_);
		
		if (queryObject == null){
			_logger.warn(String.format("Unable to build query object using query type %s and the provided mongoQueryParams", params_._queryType));
			return null;
		}
		
		DBCollection coll = _collections.get(queryObject._collectionName);
		if (coll == null) {
			_logger.warn(String.format("Unable to retrieve collection %s using query type %s and the provided mongoQueryParams", queryObject._collectionName, params_._queryType));
			return null;
		}		
		
		Iterator<DBObject> returnIterator;
		if (queryObject._shouldAggregate){
			AggregationOutput aggregationOutput = coll.aggregate(queryObject._aggregateObject);
			if (aggregationOutput == null || aggregationOutput.results() == null ){
				_logger.warn(String.format("Unable to retrieve iterator from collection %s with query type %s and the provided mongoQueryParams", queryObject._collectionName, params_._queryType));
				return null;
			}
			returnIterator = aggregationOutput.results().iterator();
		} else{
			DBCursor cursor = coll.find(queryObject._queryObject, queryObject._fieldsRequest);
			if (cursor == null){
				_logger.warn(String.format("Unable to retrieve iterator from collection %s with query type %s and the provided mongoQueryParams", queryObject._collectionName, params_._queryType));
				return null;
			}
			returnIterator = cursor.iterator();
		}
		return returnIterator;
	}
	

}
