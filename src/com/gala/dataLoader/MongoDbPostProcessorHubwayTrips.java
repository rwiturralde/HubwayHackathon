package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.core.Day;
import com.gala.core.TimeOfDay;

public class MongoDbPostProcessorHubwayTrips extends MongoDbPostProcessorBase {

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorHubwayTrips.class);
	protected DateFormat 	hubwayDateFormat;
	protected String 		dateIdToSpan;
	protected String 		dayOfWeekSpanName;
	protected String 		timeOfDaySpanName;
	
	public MongoDbPostProcessorHubwayTrips(String id, String mongoId,
			String destinationName, DateFormat hubwayDateFormat,
			String dateIdToSpan, String dayOfWeekSpanName,
			String timeOfDaySpanName) {
		super(id, mongoId, destinationName);

		this.hubwayDateFormat = hubwayDateFormat; 
		this.dateIdToSpan = dateIdToSpan;
		this.dayOfWeekSpanName = dayOfWeekSpanName;
		this.timeOfDaySpanName = timeOfDaySpanName;
	}
	
	public Map<String, Map<String, Object>> postProcessDataEntry(Map<String, Object> map) {
		
		updateId(map);
		
		Object dateObjToSpan;
		if ((dateObjToSpan = map.get(dateIdToSpan)) != null){
			Date dateToSpan = null;
			try {
				dateToSpan = hubwayDateFormat.parse(dateObjToSpan.toString());
			} catch (ParseException e) {

				_logger.error(String.format("Error when trying to parse date %s. %s ", dateObjToSpan.toString(), e));
				e.printStackTrace();
			}
			
			if (dateToSpan != null){
				Calendar calendarDateToSpan = Calendar.getInstance();
				calendarDateToSpan.setTime(dateToSpan);
				addDayOfWeekSpan(map, calendarDateToSpan);
				addTimeOfDaySpan(map, calendarDateToSpan);
			}
		} else {
			_logger.warn(String.format("No value found in entry for %s", dateIdToSpan));
		}
		
		return wrapMap(map);
	}
	
	protected void addDayOfWeekSpan(Map<String, Object> map, Calendar dateToSpan){
		Day dayOfWeek = Day.getDay(dateToSpan.get(Calendar.DAY_OF_WEEK));
		if (dayOfWeek != null){
			map.put(dayOfWeekSpanName, dayOfWeek.name());
		} else{
			_logger.warn(String.format("Unable to retrieve Day enum from calendar day of week %s. No Day of week will be added.", dateToSpan.get(Calendar.DAY_OF_WEEK)));
		}
		
	}
	
	
	// Assumes timeOfDaySpans are ordered from earliest to latest
	protected void addTimeOfDaySpan(Map<String, Object> map, Calendar dateToSpan){
				
		TimeOfDay timeOfDay = TimeOfDay.getTimeOfDay(dateToSpan.get(Calendar.HOUR_OF_DAY), dateToSpan.get(Calendar.MINUTE));
		
		if (timeOfDay != null){
			map.put(timeOfDaySpanName, timeOfDay);
		} else{
			_logger.warn(String.format("Unable to retrieve TimeOfDay enum from time %s:%s. No Day of week will be added.", dateToSpan.get(Calendar.HOUR_OF_DAY), dateToSpan.get(Calendar.MINUTE)));
		}
	}

}
