package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class HubwayMongoDbPostProcessor implements IDataPostProcessor <String, Object>{

	private String id; 
	private String mongoId;
	private DateFormat hubwayDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
	
	public HubwayMongoDbPostProcessor(String id, DateFormat hubwayDateFormat){
		this.id = id;
	}
	
	public Map<String, Object> postProcessDataEntry(Map<String, Object> map) {
		//Map<String, Object> returnMap = new HashMap<String, Object>(map);
		Object mongoDocumentId;
		if ((mongoDocumentId =map.remove(id)) != null){
			map.put(mongoId, mongoDocumentId);
			
		} else {
			//log error
		}
		
		return map;
		
	}
	
	private void addDayOfWeekBand(Map<String, Object> map){
		
	}
	
	private void addTimeOfDayBand(Map<String, Object> map){
		
	}


}
