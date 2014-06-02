package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

public class MongoDbPostProcessorHubwayDateSpan extends MongoDbPostProcessorDateSpan{

	public MongoDbPostProcessorHubwayDateSpan(String dayOfWeekSpanName,
			String timeOfDaySpanName, String mongoDateName,
			DateFormat mongoDateFormat, String dateIdToSpan, 
			DateFormat hubwayDateFormat) {
		super(dayOfWeekSpanName, timeOfDaySpanName,	mongoDateName, mongoDateFormat);
		this.dateIdToSpan = dateIdToSpan;
		this.hubwayDateFormat = hubwayDateFormat;
	}

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorHubwayDateSpan.class);
	protected String 				dateIdToSpan;	
	protected DateFormat 			hubwayDateFormat;
	
	protected Calendar getCalendar(Map<String, Object> map) {
		
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
				return calendarDateToSpan;
			} else {
				_logger.warn(String.format("No valid date found for entry for %s", dateIdToSpan));				
			}
		} else {
			_logger.warn(String.format("No value found in entry for %s", dateIdToSpan));
		}
		return null;
	}
	
}
