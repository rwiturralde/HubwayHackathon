package com.gala.logicEngine;

import com.mongodb.BasicDBObject;

/** 
 * Class for constructing mongo db query objects based on query type
 * @author Heather
 *
 */
public class MongoDbQueryBuilder implements IQueryBuilder{
	
	public MongoDbQueryObject buildQuery(final QueryType query_, final MongoDbQueryParameters params_){
		
		switch(query_) {
			case STATION_INFO:
				return buildStationInfoQuery(params_);
			case STATION_NAMES:
				return buildStationNamesQuery(params_);
			case WEATHER_DATES:
				return buildWeatherDatesQuery(params_);
			case STATION_STATUS_FOR_DATETIME:
				return buildStationStatusQuery(params_);
				
			default: return null;
		}
	}
	
	protected MongoDbQueryObject buildStationInfoQuery(final MongoDbQueryParameters params_){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("_id", params_.getStartStationId());
		return new MongoDbQueryObject(queryObj, new BasicDBObject(), "stations");
	}
	
	protected MongoDbQueryObject buildStationNamesQuery(final MongoDbQueryParameters params_){
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
	protected MongoDbQueryObject buildStationStatusQuery(final MongoDbQueryParameters params_){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("spannedTime", params_.getTimeOfDay().name());
		queryObj.append("station_id", Integer.toString(params_.getStartStationId()));
		queryObj.append("formattedDate", new BasicDBObject("$in", params_.getValidDates()));
		return new MongoDbQueryObject(queryObj, new BasicDBObject(), "stationStatus");
	}
	
	/** 
	 * Build the query object for getting the list of 
	 * dates that had weather matching the temperature range given
	 * @return
	 */
	protected MongoDbQueryObject buildWeatherDatesQuery(final MongoDbQueryParameters params_){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("spannedTemperature", params_.getTemperature().name());
		queryObj.append("spannedTime", params_.getTimeOfDay().name());
		BasicDBObject fieldsObj = new BasicDBObject();
		fieldsObj.append("formattedDate", true);
		fieldsObj.append("spannedTime", true);
		return new MongoDbQueryObject(queryObj, fieldsObj, "historicalWeather");
	}
	
}