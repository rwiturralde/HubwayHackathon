package com.gala.dataLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


/**
 * Class for writing to a mongo db instance.
 * @author Heather
 *
 */
public class MongoDbWriter implements IDataWriter<String, Object>{

	static final Logger _logger = Logger.getLogger(MongoDbWriter.class);
	
	protected boolean 						_isInitialized;
	protected MongoClient 					_client;
	protected String 						_dbName;
	protected List<String> 					_collectionNames;
	protected DB 							_db;	
	protected HashMap<String, DBCollection> _collections;
	
	public MongoDbWriter(final MongoClient client_, final String dbName_, final List<String> collNames_){
		_client = client_;
		_dbName = dbName_;
		_collectionNames = collNames_;
		_isInitialized = false;
	}
	
	/* (non-Javadoc)
	 * @see com.gala.dataLoader.IDataWriter#init()
	 */
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
	
	/* (non-Javadoc)
	 * @see com.gala.dataLoader.IDataWriter#isInitialized()
	 */
	public boolean isInitialized(){
		return _isInitialized;
	}

	
	/* (non-Javadoc)
	 * @see com.gala.dataLoader.IDataWriter#writeEntry(java.util.Map, java.lang.String)
	 */
	public boolean writeEntry(final Map<String,Object> dataEntry_, final String collName_){
		
		if (dataEntry_ != null) {
			try {
				BasicDBObject dbEntry = convertEntryToDbObject(dataEntry_);
				DBCollection coll = _collections.get(collName_);
				if (coll == null) {
					_logger.warn("Collection not found: " + collName_);
					return false;
				}
				coll.insert(dbEntry);
			} catch (Exception e) {
				_logger.warn("Exception thrown while writing entry to database: " + e);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Converts a given entry into an object that can be written to the db
	 * @param dataEntry_ entry to convert
	 * @return object to be written to mongodb
	 */
	protected BasicDBObject convertEntryToDbObject(Map<String,Object> dataEntry_){
		BasicDBObject dbObj = new BasicDBObject();
		
		for (Entry<String, Object> pair : dataEntry_.entrySet()) {
			dbObj.append(pair.getKey(), pair.getValue());
		}
		
		return dbObj;
	}
	
}
