package com.gala.dataLoader;

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
public class MongoDbPostProcessorId implements IDataPostProcessor <String, Object>{

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorId.class);
	protected String 		id; 
	protected String 		mongoId;
	protected String 		destinationName;
		
	public MongoDbPostProcessorId(String id, String mongoId,
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
	public void postProcessDataEntry(Map<String, Object> map) {
		
		updateId(map);
	}
	
	protected void updateId(Map<String, Object> map){
		Object mongoDocumentId;
		if (id == null){
			return;
		}
		if ((mongoDocumentId =map.remove(id)) != null){
			map.put(mongoId, mongoDocumentId);
			
		} else {
			_logger.warn(String.format("No value found in entry for %s", id));
		}
	}

	

}
