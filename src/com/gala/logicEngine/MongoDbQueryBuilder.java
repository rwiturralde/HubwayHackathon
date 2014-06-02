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
	
	public Object[] buildQuery(final QueryType query_){
		
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
	
	protected Object[] buildStationInfoQuery(){
		Object[] queryObjArr = new BasicDBObject[2];
		queryObjArr[0] = "stations";
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("_id", _startStationId);
		queryObjArr[1] = queryObj;
		return queryObjArr;
	}
	
	protected Object[] buildStationNamesQuery(){
		Object[] queryObjArr = new BasicDBObject[3];
		queryObjArr[0] = "stations";
		BasicDBObject queryObj = new BasicDBObject();
		queryObjArr[1] = queryObj;
		BasicDBObject fieldsObj = new BasicDBObject();
		fieldsObj.append("station", true);
		queryObjArr[2] = fieldsObj;
		return queryObjArr;
	}
	
	/**
	 * Build the query object for getting the station statuses
	 * for the dates and time range provided.
	 * @return
	 */
	protected Object[] buildStationStatusQuery(){
		Object[] queryObjArr = new BasicDBObject[2];
		queryObjArr[0] = "stationStatus";
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("timeRange", _timeOfDay);
		queryObj.append("date", new BasicDBObject("$in", _validDates));
		queryObjArr[1] = queryObj;
		return queryObjArr;
	}
	
	/** 
	 * Build the query object for getting the list of 
	 * dates that had weather matching the temperature range given
	 * @return
	 */
	protected Object[] buildWeatherDatesQuery(){
		Object[] queryObjArr = new BasicDBObject[3];
		queryObjArr[0] = "historicalWeather";
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("tempRange", _temperature);
		queryObjArr[1] = queryObj;
		BasicDBObject fieldsObj = new BasicDBObject();
		fieldsObj.append("date", true);
		queryObjArr[2] = fieldsObj;
		return queryObjArr;
	}
	
}
