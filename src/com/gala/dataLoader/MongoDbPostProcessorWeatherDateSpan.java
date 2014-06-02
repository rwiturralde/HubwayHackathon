package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

public class MongoDbPostProcessorWeatherDateSpan extends MongoDbPostProcessorDateSpan{

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorWeatherDateSpan.class);
	protected DateFormat 	weatherDateFormat;
	protected String 		timeId;
	protected String 		dateId;
	
	
	public MongoDbPostProcessorWeatherDateSpan(String id, String mongoId,
			String destinationName, String dayOfWeekSpanName,
			String timeOfDaySpanName, String mongoDateName,
			DateFormat mongoDateFormat, DateFormat weatherDateFormat, 
			String dateId,	String timeId) {
		super(id, mongoId, destinationName, dayOfWeekSpanName, timeOfDaySpanName,
				mongoDateName, mongoDateFormat);
		this.weatherDateFormat = weatherDateFormat;
		this.dateId = dateId;
		this.timeId = timeId;		
	}

	protected Calendar getCalendar(Map<String, Object> map) {
		String date = null;
		String time = null;
				
		Object dateObj;
		Object timeObj;
		
		if ((dateObj = map.get(dateId)) != null){			
			date = dateObj.toString();
		} else {
			_logger.debug(String.format("No value found in entry for %s. Unable to create Calendar.", dateId));
			return null;
		}
		
		if ((timeObj = map.get(timeId)) != null){			
			time = timeObj.toString();
		} else {
			_logger.debug(String.format("No value found in entry for %s. Unable to create Calendar.", timeId));
			return null;
		}
		
		Date dateToSpan = null;
		try {
			dateToSpan = weatherDateFormat.parse(String.join("", date, time));
		} catch (ParseException e) {
			_logger.error(String.format("Error when trying to parse date %s and time %s. %s ", date, time, e));
			e.printStackTrace();
		}
		
		if (dateToSpan != null){
			Calendar calendarDateToSpan = Calendar.getInstance();
			calendarDateToSpan.setTime(dateToSpan);
			return calendarDateToSpan;
		} else {
			_logger.warn(String.format("No valid date found for weather entry."));
			return null;
		}
	}

}
