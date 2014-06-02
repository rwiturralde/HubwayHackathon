package com.gala.logicEngine;

import java.util.Date;
import java.util.List;

import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/** 
 * Class for constructing mongo db query objects based on query type
 * @author Heather
 *
 */
public class MongoDbQueryBuilder implements IQueryBuilder{

	protected int _startStationId;
	protected TimeOfDay _timeOfDay;
	protected Temperature _temperature;
	protected List<Date> _validDates;
	
	public void setStartStationId(int startStationId_){
		_startStationId = startStationId_;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay_){
		_timeOfDay = timeOfDay_;
	}
	
	public void setTemperature(Temperature temperature_){
		_temperature = temperature_;
	}
	
	public void setValidDates(List<Date> validDates_){
		_validDates = validDates_;
	}
	
	public MongoDbQueryBuilder(){
		_startStationId = 0;
		_timeOfDay = TimeOfDay.MORNING;
		_temperature = Temperature.ZEROES;
	}
	
	public MongoDbQueryBuilder(final int startStationId_, 
			final TimeOfDay timeOfDay_,
			final Temperature temperature_){
		_startStationId = startStationId_;
		_timeOfDay = timeOfDay_;
		_temperature = temperature_;
	}
	
	public MongoDbQueryObject buildQuery(final QueryType query_){
		
		switch(query_) {
			case STATION_INFO:
				return buildStationInfoQuery();
			case STATION_NAMES:
				return buildStationNamesQuery();
			case WEATHER_DATES:
				return buildWeatherDatesQuery();
			case STATION_STATUS_FOR_DATETIME:
				return buildStationStatusQuery();
				
			default: return null;
		}
		
	}
	
	protected MongoDbQueryObject buildStationInfoQuery(){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("_id", _startStationId);
		return new MongoDbQueryObject(queryObj, new BasicDBObject(), "stations");
	}
	
	protected MongoDbQueryObject buildStationNamesQuery(){
		BasicDBObject queryObj = new BasicDBObject();
		BasicDBObject fieldsObj = new BasicDBObject();
		fieldsObj.append("station", true);
		return new MongoDbQueryObject(queryObj, fieldsObj, "stations");
	}
	
	/**
	 * Build the query object for getting the station statuses
	 * for the dates and time range provided.
	 * @return
	 */
	protected MongoDbQueryObject buildStationStatusQuery(){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("timeRange", _timeOfDay);
		queryObj.append("date", new BasicDBObject("$in", _validDates));
		return new MongoDbQueryObject(queryObj, new BasicDBObject(), "stationStatus");
	}
	
	/** 
	 * Build the query object for getting the list of 
	 * dates that had weather matching the temperature range given
	 * @return
	 */
	protected MongoDbQueryObject buildWeatherDatesQuery(){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("tempRange", _temperature);
		BasicDBObject fieldsObj = new BasicDBObject();
		fieldsObj.append("date", true);
		return new MongoDbQueryObject(queryObj, fieldsObj, "historicalWeather");
	}
	
}
