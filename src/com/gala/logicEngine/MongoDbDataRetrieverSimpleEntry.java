package com.gala.logicEngine;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverSimpleEntry <K, V> extends MongoDbDataRetriever<Entry<K, V>> {

	static final Logger _logger 			= Logger.getLogger(MongoDbDataRetrieverSingleObj.class);
	protected String 	_keyToRetrieve;
	protected String 	_valueToRetrieve;
	
	public MongoDbDataRetrieverSimpleEntry(MongoClient client_, String dbName_,
			List<String> collNames_, MongoDbQueryBuilder mongoDbQueryBuilder_,
			String keyToRetrieve_, String valueToRetrieve_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_keyToRetrieve = keyToRetrieve_;
		_valueToRetrieve = valueToRetrieve_;
	}

	@SuppressWarnings("unchecked")
	public List<Entry<K,V>> retrieveData(MongoDbQueryParameters params_) {
		DBCursor cursor = retrieveCursor(params_);
		
		if (cursor == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<Entry<K,V>> returnList = new ArrayList<Entry<K,V>>();
		while(cursor.hasNext()){
			DBObject responseObj = cursor.next();
			Object retrievedKeyObj = responseObj.get(_keyToRetrieve);
			if (retrievedKeyObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _keyToRetrieve));
				continue;
			}
			Object retrievedValueObj = responseObj.get(_valueToRetrieve);
			if (retrievedValueObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _valueToRetrieve));
				continue;
			}
			
			try {
				K key = (K) retrievedKeyObj;
				V value = (V) retrievedValueObj;
				Entry<K,V> newEntry = new SimpleEntry<K,V>(key, value);
				returnList.add(newEntry);
			} catch (Exception e){
				_logger.warn(String.format("Unable to cast db entry %s or %s to specified type. Exception: %s", retrievedKeyObj, retrievedValueObj, e));
			}			
		}
		
		return returnList;
	}

}
