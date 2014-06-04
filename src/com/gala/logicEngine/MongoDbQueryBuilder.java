package com.gala.logicEngine;

import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/** 
 * Class for constructing mongo db query objects based on query type
 * @author Heather
 *
 */
public class MongoDbQueryBuilder implements IQueryBuilder{
	
	public MongoDbQueryObject buildQuery(final MongoDbQueryParameters params_){
		
		switch(params_._queryType) {
			case STATION_INFO:
				return buildStationInfoQuery(params_);
			case STATION_NAMES:
				return buildStationNamesQuery(params_);
			case WEATHER_DATES:
				return buildWeatherDatesQuery(params_);
			case STATION_STATUS_FOR_DATETIME:
				return buildStationStatusQuery(params_);
			case TRIP_END_STATION:
				return buildTripEndQuery(params_);
			case STATION_NAMES_FOR_LIST:
				return buildStationNamesForListQuery(params_);
				
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
		
		// Optionally include day parameter
		if (!params_.getExcludeDayParam()) {
			queryObj.append("isWeekday", params_.getDay().isWeekday());
		}
		
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
	
	protected MongoDbQueryObject buildTripEndQuery(final MongoDbQueryParameters params_){
		
		// Match on time of day and starting station
		BasicDBObject matchFields = new BasicDBObject();
		matchFields.append("spannedStartTime", params_.getTimeOfDay().name());
		matchFields.append("start_station", Integer.toString(params_.getStartStationId()));
		
		// Optionally include day parameter
		if (!params_.getExcludeDayParam()) {
			matchFields.append("isWeekday", params_.getDay().isWeekday());
		}
		
		DBObject matchObject = new BasicDBObject("$match", matchFields);
		
		// group by ending station
		BasicDBObject groupFields = new BasicDBObject( "_id", "$end_station");
		groupFields.put("count", new BasicDBObject( "$sum", 1));
		DBObject groupObject = new BasicDBObject("$group", groupFields );
		
		// sort the results in descending order
		BasicDBObject sortFields = new BasicDBObject("count", -1);
		DBObject sortObject = new BasicDBObject("$sort", sortFields );
		
		// Do not change the order of the defined query objects
		// it determines the order in which queries are executed
		return new MongoDbQueryObject(Arrays.asList(matchObject,groupObject, sortObject),"trips");
	}
	
	protected MongoDbQueryObject buildStationNamesForListQuery(final MongoDbQueryParameters params_){
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.append("_id", new BasicDBObject("$in", params_.getStationList()));
		return new MongoDbQueryObject(queryObj, new BasicDBObject(), "stations");
	}
	
}
