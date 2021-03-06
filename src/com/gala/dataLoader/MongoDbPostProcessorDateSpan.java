package com.gala.dataLoader;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.core.Day;
import com.gala.core.TimeOfDay;

public abstract class MongoDbPostProcessorDateSpan implements IDataPostProcessor<String, Object> {

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorDateSpan.class);

	protected String 		dayOfWeekSpanName;
	protected String 		isWeekdayName;
	protected String 		timeOfDaySpanName;
	protected String 		mongoDateName;
	protected DateFormat	mongoDateFormat;
	
	public MongoDbPostProcessorDateSpan(String dayOfWeekSpanName,  
			String isWeekdayName, String timeOfDaySpanName, 
			String mongoDateName, DateFormat mongoDateFormat) {
		this.dayOfWeekSpanName = dayOfWeekSpanName;
		this.isWeekdayName = isWeekdayName;
		this.timeOfDaySpanName = timeOfDaySpanName;
		this.mongoDateName = mongoDateName;
		this.mongoDateFormat = mongoDateFormat;
	}
	
	public void postProcessDataEntry(Map<String, Object> map) {
				
		Calendar calendarDateToSpan = getCalendar(map);

		addDayOfWeekSpan(map, calendarDateToSpan);
		addTimeOfDaySpan(map, calendarDateToSpan);
		addDate(map, calendarDateToSpan);
	}
	
	abstract protected Calendar getCalendar(Map<String, Object> map);
	
	protected void addDayOfWeekSpan(Map<String, Object> map, Calendar dateToSpan){
		
		Day dayOfWeek = Day.fromCalendar(dateToSpan);

		if (dayOfWeek != null){
			map.put(dayOfWeekSpanName, dayOfWeek.name());
			map.put(isWeekdayName, dayOfWeek.isWeekday());
		} else{
			_logger.warn(String.format("Unable to retrieve Day enum from calendar day of week %s. No Day of week will be added.", dateToSpan.get(Calendar.DAY_OF_WEEK)));
		}
	}
	
	
	// Assumes timeOfDaySpans are ordered from earliest to latest
	protected void addTimeOfDaySpan(Map<String, Object> map, Calendar dateToSpan){
				
		TimeOfDay timeOfDay = TimeOfDay.getTimeOfDay(dateToSpan.get(Calendar.HOUR_OF_DAY), dateToSpan.get(Calendar.MINUTE));
		
		if (timeOfDay != null){
			map.put(timeOfDaySpanName, timeOfDay.name());
		} else{
			_logger.warn(String.format("Unable to retrieve TimeOfDay enum from time %s:%s. No Day of week will be added.", dateToSpan.get(Calendar.HOUR_OF_DAY), dateToSpan.get(Calendar.MINUTE)));
		}
	}
	
	protected void addDate(Map<String, Object> map, Calendar dateToSpan){
		
		if (mongoDateName != null && mongoDateFormat != null){
			map.put(mongoDateName, mongoDateFormat.format(dateToSpan.getTime()));
		} else{
			_logger.warn(String.format("Invalid mongoDateName: %s and/or invalid mongoDateFormat: %s", mongoDateName, mongoDateFormat));
		}
	}

}
