package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

public class HubwayMongoDbPostProcessor implements IDataPostProcessor <String, Object>{

	static final Logger _logger = Logger.getLogger(HubwayMongoDbPostProcessor.class);
	private String id; 
	private String mongoId;
	private DateFormat hubwayDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
	private String dateIdToSpan;
	private String dayOfWeekSpanName;
	private String timeOfDaySpanName;
		
	public HubwayMongoDbPostProcessor(String id, String mongoId, DateFormat hubwayDateFormat, String dateIdToSpan, String dayOfWeekSpanName, String timeOfDaySpanName){
		this.id = id;
		this.mongoId = mongoId;
		this.hubwayDateFormat = hubwayDateFormat; 
		this.dateIdToSpan = dateIdToSpan;
		this.dayOfWeekSpanName = dayOfWeekSpanName;
		this.timeOfDaySpanName = timeOfDaySpanName;
	}
	
	public Map<String, Object> postProcessDataEntry(Map<String, Object> map) {
		//Map<String, Object> returnMap = new HashMap<String, Object>(map);
		Object mongoDocumentId;
		if ((mongoDocumentId =map.remove(id)) != null){
			map.put(mongoId, mongoDocumentId);
			
		} else {
			//log error
		}
		
		Object dateObjToSpan;
		if ((dateObjToSpan = map.get(dateIdToSpan)) != null){
			Date dateToSpan = null;
			try {
				dateToSpan = hubwayDateFormat.parse(dateObjToSpan.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (dateToSpan != null){
				addDayOfWeekSpan(map, dateToSpan);
				addTimeOfDaySpan(map, dateToSpan);
			}
		} else {
			//log error
		}
		
		return map;
		
	}
	
	private void addDayOfWeekSpan(Map<String, Object> map, Date dateToSpan){
		
	}
	
	private void addTimeOfDaySpan(Map<String, Object> map, Date dateToSpan){
		
	}


}
