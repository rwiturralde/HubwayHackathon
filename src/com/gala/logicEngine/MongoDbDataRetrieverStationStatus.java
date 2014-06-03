package com.gala.logicEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gala.core.Day;
import com.gala.core.StationStatus;
import com.gala.core.TimeOfDay;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverStationStatus extends
		MongoDbDataRetriever<StationStatus> {

	protected String _numBikesAvailableId;
	protected String _capacityId;
	protected String _dateId;
	protected String _dayId;
	protected String _timeOfDayId;
	protected SimpleDateFormat _dateFormat;
	
		
	public MongoDbDataRetrieverStationStatus(final MongoClient client_,
			final String dbName_, final List<String> collNames_,
			final MongoDbQueryBuilder mongoDbQueryBuilder_, 
			final String numBikesAvailableId_, final String capacityId_, 
			final String dateId_, final String dayId_, final String timeOfDayId_,
			final SimpleDateFormat dateFormat_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_numBikesAvailableId = numBikesAvailableId_;
		_capacityId = capacityId_;
		_dateId = dateId_;
		_dayId = dayId_;
		_timeOfDayId = timeOfDayId_;
		_dateFormat = dateFormat_;
	}

	public List<StationStatus> retrieveData(QueryType queryType_, MongoDbQueryParameters params_) {
		DBCursor cursor = retrieveCursor(queryType_, params_);
		
		if (cursor == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<StationStatus> returnList = new ArrayList<StationStatus>();
		while(cursor.hasNext()){
			DBObject responseObj = cursor.next();
			Object numBikesAvailObj = responseObj.get(_numBikesAvailableId);
			
			int numBikesAvailable = Integer.MIN_VALUE;
			int capacity = Integer.MIN_VALUE;
			Date date = null;
			Day day = null;
			TimeOfDay timeOfDay = null;
			
			if (numBikesAvailObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _numBikesAvailableId));
			} else {
				try {
					numBikesAvailable = Integer.parseInt(numBikesAvailObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", numBikesAvailObj.toString(), _numBikesAvailableId));
				}				
			}
						
			Object capacityObj = responseObj.get(_capacityId);
			if (capacityObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _capacityId));
			} else {
				try {
					capacity = Integer.parseInt(capacityObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", capacityObj.toString(), _capacityId));
				}				
			}
			
			Object dateObj = responseObj.get(_dateId);
			if (dateObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _dateId));
			} else {
				try {
					date = _dateFormat.parse(dateObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a date. Station Status will not be fully populated.", dateObj.toString(), _dateId));
				}				
			}
			
			Object dayObj = responseObj.get(_dayId);
			if (dayObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _dayId));
			} else {
				try {
					day = Day.valueOf(dayObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a Day. Station Status will not be fully populated.", dayObj.toString(), _dayId));
				}				
			}
			
			Object timeOfDayObj = responseObj.get(_timeOfDayId);
			if (timeOfDayObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _timeOfDayId));
			} else {
				try {
					timeOfDay = TimeOfDay.valueOf(timeOfDayObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", timeOfDayObj.toString(), _timeOfDayId));
				}				
			}
			
			returnList.add(new StationStatus(numBikesAvailable, capacity, date, day, timeOfDay));
		}
		
		return returnList;
	}

}
