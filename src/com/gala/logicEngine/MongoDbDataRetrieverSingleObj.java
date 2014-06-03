package com.gala.logicEngine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverSingleObj<T> extends MongoDbDataRetriever<T>{
	
	static final Logger _logger 			= Logger.getLogger(MongoDbDataRetrieverSingleObj.class);
	protected String 	_columnToRetrieve;
	
	public MongoDbDataRetrieverSingleObj(final MongoClient client_,
			final String dbName_, final List<String> collNames_, 
			final MongoDbQueryBuilder mongoDbQueryBuilder_, 
			final String columnToRetrieve_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_columnToRetrieve = columnToRetrieve_;
	}

	@SuppressWarnings("unchecked")
	public List<T> retrieveData(final MongoDbQueryParameters params_){
		
		DBCursor cursor = retrieveCursor(params_);
		
		if (cursor == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<T> returnList = new ArrayList<T>();
		while(cursor.hasNext()){
			DBObject responseObj = cursor.next();
			Object retrievedObj = responseObj.get(_columnToRetrieve);
			if (retrievedObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _columnToRetrieve));
				continue;
			}
			try {
				returnList.add((T) retrievedObj);
			} catch (Exception e){
				_logger.warn(String.format("Unable to cast db entry %s to specified type. Exception: %s", retrievedObj, e));
			}			
		}
		
		return returnList;
	}
}