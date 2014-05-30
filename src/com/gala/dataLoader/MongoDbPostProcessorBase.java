package com.gala.dataLoader;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Nathan
 *
 * Simple implementation of IDataPostProcessor to take a map of 
 * <String, Object>, format it for MongoDB and decorate it for 
 * data store insertion.
 * 
 */
public class MongoDbPostProcessorBase implements IDataPostProcessor <String, Object>{

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorBase.class);
	protected String 		id; 
	protected String 		mongoId;
	protected String 		destinationName;
		
	public MongoDbPostProcessorBase(String id, String mongoId,
			String destinationName) {
		this.id = id;
		this.mongoId = mongoId;
		this.destinationName = destinationName;
	}
	

	/**
	 * Takes in a map from Hubway intended to be loaded to a MongoDb store. 
	 * It then decorates the data to give it a proper ID, and add meta data 
	 * about the trip times. 
	 * 
	 * @param   The map to be post processed.
	 * @return  The map that has been post processed
	 * 
	 */
	public Map<String, Map<String, Object>> postProcessDataEntry(Map<String, Object> map) {
		
		updateId(map);
		
		return wrapMap(map);
	}
	
	protected void updateId(Map<String, Object> map){
		Object mongoDocumentId;
		if ((mongoDocumentId =map.remove(id)) != null){
			map.put(mongoId, mongoDocumentId);
			
		} else {
			_logger.warn(String.format("No value found in entry for %s", id));
		}
	}
	
	protected Map<String, Map<String, Object>> wrapMap(Map<String, Object> map){
		Map<String, Map<String, Object>> returnMap = new HashMap<String, Map<String,Object>>();
		returnMap.put(destinationName, map);
			
		return returnMap;
	}
	

}
