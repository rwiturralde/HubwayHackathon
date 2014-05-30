package com.gala.dataLoader;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;

public class MongoDbPostProcessorStationStatusDateSpans extends
		MongoDbPostProcessorDateSpans {

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorStationStatusDateSpans.class);
	protected String yearId;
	protected String monthId;
	protected String dayId;
	protected String hourId;
	protected String minId;
	
	public MongoDbPostProcessorStationStatusDateSpans(String id, String mongoId,
			String destinationName, DateFormat hubwayDateFormat,
			String dateIdToSpan, String dayOfWeekSpanName,
			String timeOfDaySpanName, String yearId, String monthId, 
			String dayId, String hourId, String minId) {
		super(id, mongoId, destinationName, hubwayDateFormat, dateIdToSpan,
				dayOfWeekSpanName, timeOfDaySpanName);
		this.yearId = yearId;
		this.monthId = monthId;
		this.dayId = dayId;
		this.hourId = hourId;
		this.minId = minId;
	}

	public Map<String, Map<String, Object>> postProcessDataEntry(Map<String, Object> map) {
		
		updateId(map);
		
		Calendar calendarDateToSpan = getCalendar(map);
		
		addDayOfWeekSpan(map, calendarDateToSpan);
		addTimeOfDaySpan(map, calendarDateToSpan);
		
		return wrapMap(map);
	}

	@Override
	protected Calendar getCalendar(Map<String, Object> map) {
		int year = 0;
		int month = 0;
		int day = 0; 
		int hour = 0;
		int min = 0;
		
		Object yearObj;
		Object monthObj;
		Object dayObj;
		Object hourObj;
		Object minObj;
		
		if ((yearObj = map.get(yearId)) != null){			
			year = Integer.parseInt(yearObj.toString());
		} else {
			_logger.debug(String.format("No value found in entry for %s", yearId));
		}
		
		if ((monthObj = map.get(monthId)) != null){			
			month = Integer.parseInt(monthObj.toString());
		} else {
			_logger.debug(String.format("No value found in entry for %s", monthId));
		}
		
		if ((dayObj = map.get(dayId)) != null){			
			day = Integer.parseInt(dayObj.toString());
		} else {
			_logger.debug(String.format("No value found in entry for %s", dayId));
		}
		
		if ((hourObj = map.get(hourId)) != null){			
			hour = Integer.parseInt(hourObj.toString());
		} else {
			_logger.debug(String.format("No value found in entry for %s", hourId));
		}
		
		if ((minObj = map.get(minId)) != null){			
			min = Integer.parseInt(minObj.toString());
		} else {
			_logger.debug(String.format("No value found in entry for %s", minId));
		}
				
		Calendar calendarDateToSpan = Calendar.getInstance();
		calendarDateToSpan.set(Calendar.YEAR, year);
		calendarDateToSpan.set(Calendar.MONTH, month);
		calendarDateToSpan.set(Calendar.DAY_OF_MONTH, day);
		calendarDateToSpan.set(Calendar.HOUR_OF_DAY, hour);
		calendarDateToSpan.set(Calendar.MINUTE, min);
		
		return calendarDateToSpan;
	}
	
	
}
