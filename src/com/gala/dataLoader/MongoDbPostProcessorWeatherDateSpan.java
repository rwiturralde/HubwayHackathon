package com.gala.dataLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;

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

	public void postProcessDataEntry(Map<String, Object> map) {
		
		updateId(map);
		
		Calendar calendarDateToSpan = getCalendar(map);
		
		addDayOfWeekSpan(map, calendarDateToSpan);
		addTimeOfDaySpan(map, calendarDateToSpan);
		addDate(map, calendarDateToSpan);
		
		addDerpTemperature(map);
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
			dateToSpan = weatherDateFormat.parse(String.format("%s%s", date, time));
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
	
	// TODO fix this (refactor to a series of postprocessors)
	protected void addDerpTemperature(Map<String, Object> map){
		String tempMongoId = "spannedTemperature";
		String tempId = "DryBulbFarenheit";
		Object tempObj;
		int tempInt;
		
		if ((tempObj = map.get(tempId)) != null){
			try{
				tempInt = Integer.parseInt(tempObj.toString());
			} catch (Exception e){
				_logger.error(String.format("Failed to parse \"%s\" as an int. No temperature will be added.", tempId));
				return;
			}
		} else {
			_logger.debug(String.format("No value found in entry for %s", tempId));
			return;
		}
		
		Temperature tempEnum = Temperature.getTemperature(tempInt);
		
		if (tempEnum != null){
			map.put(tempMongoId, tempEnum.name());
		} else{
			_logger.warn(String.format("Unable to retrieve temp enum from temp %s. No temperature will be added.", tempInt));
		}
		
		


	}
}
