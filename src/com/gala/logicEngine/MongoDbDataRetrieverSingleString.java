package com.gala.logicEngine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverSingleString extends MongoDbDataRetriever<String>{
	
	static final Logger _logger 			= Logger.getLogger(MongoDbDataRetrieverSingleString.class);
	protected String 	_columnToRetrieve;
	
	public MongoDbDataRetrieverSingleString(final MongoClient client_,
			final String dbName_, final List<String> collNames_, 
			final MongoDbQueryBuilder mongoDbQueryBuilder_, 
			final String columnToRetrieve_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_columnToRetrieve = columnToRetrieve_;
	}

	public List<String> retrieveData(final QueryType queryType_, final MongoDbQueryParameters params_){
		
		DBCursor cursor = retrieveCursor(queryType_, params_);
		
		if (cursor == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<String> returnList = new ArrayList<String>();
		while(cursor.hasNext()){
			DBObject responseObj = cursor.next();
			Object retrievedObj = responseObj.get(_columnToRetrieve);
			if (retrievedObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _columnToRetrieve));
				continue;
			}
			returnList.add(responseObj.toString());
		}
		
		return returnList;
	}
}
