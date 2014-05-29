package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.gala.core.TimeOfDay;

/**
 * @author Nathan
 *
 * Simple implementation of IDataPostProcessor to take a map of 
 * <String, Object>, format it for MongoDB and decorate it for 
 * data store insertion.
 * 
 */
public class HubwayMongoDbPostProcessor implements IDataPostProcessor <String, Object>{

	static final Logger _logger = Logger.getLogger(HubwayMongoDbPostProcessor.class);
	private String id; 
	private String mongoId;
	private List<String> destinationNames;
	private DateFormat hubwayDateFormat;
	private String dateIdToSpan;
	private String dayOfWeekSpanName;
	private String timeOfDaySpanName;
	private List<Entry<Entry<Integer, Integer>, TimeOfDay>> timeOfDaySpans;
		
	public HubwayMongoDbPostProcessor(String id, String mongoId, List<String> destinationNames, DateFormat hubwayDateFormat, String dateIdToSpan, String dayOfWeekSpanName, String timeOfDaySpanName){
		this.id = id;
		this.mongoId = mongoId;
		this.hubwayDateFormat = hubwayDateFormat; 
		this.dateIdToSpan = dateIdToSpan;
		this.dayOfWeekSpanName = dayOfWeekSpanName;
		this.timeOfDaySpanName = timeOfDaySpanName;
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
				Calendar calendarDateToSpan = Calendar.getInstance();
				calendarDateToSpan.setTime(dateToSpan);
				addDayOfWeekSpan(map, calendarDateToSpan);
				addTimeOfDaySpan(map, calendarDateToSpan);
			}
		} else {
			//log error
		}
		
		//return map;
		return null;
	}
	
	private void addDayOfWeekSpan(Map<String, Object> map, Calendar dateToSpan){
		
	}
	
	
	// Assumes timeOfDaySpans are ordered from earliest to latest
	private void addTimeOfDaySpan(Map<String, Object> map, Calendar dateToSpan){
		
		int dateToSpanHour = dateToSpan.get(Calendar.HOUR_OF_DAY);
		int dateToSpanMinutes = dateToSpan.get(Calendar.MINUTE);
		
		for (Entry<Entry<Integer, Integer>, TimeOfDay> timeOfDaySpan : timeOfDaySpans){
			if (dateToSpanHour >= timeOfDaySpan.getKey().getKey() && dateToSpanMinutes >= timeOfDaySpan.getKey().getValue()){
				
			}
		}
		
		
	}


}
