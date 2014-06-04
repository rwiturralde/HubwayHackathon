package com.gala.logicEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.gala.core.Day;
import com.gala.core.StationStatus;
import com.gala.core.TimeOfDay;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverStationStatus extends
		MongoDbDataRetrieverBase<StationStatus> {

	protected String _numBikesAvailableKey;
	protected String _capacityKey;
	protected String _dateKey;
	protected String _dayKey;
	protected String _timeOfDayKey;
	protected SimpleDateFormat _dateFormat;
	
		
	public MongoDbDataRetrieverStationStatus(final MongoClient client_,
			final String dbName_, final List<String> collNames_,
			final MongoDbQueryBuilder mongoDbQueryBuilder_, 
			final String numBikesAvailableKey_, final String capacityKey_, 
			final String dateKey_, final String dayKey_, final String timeOfDayKey_,
			final SimpleDateFormat dateFormat_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_numBikesAvailableKey = numBikesAvailableKey_;
		_capacityKey = capacityKey_;
		_dateKey = dateKey_;
		_dayKey = dayKey_;
		_timeOfDayKey = timeOfDayKey_;
		_dateFormat = dateFormat_;
	}

	public List<StationStatus> retrieveData(MongoDbQueryParameters params_) {
		Iterator<DBObject> iterator =  retrieveCursor(params_);
		
		if (iterator == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<StationStatus> returnList = new ArrayList<StationStatus>();
		while(iterator.hasNext()){
			DBObject responseObj = iterator.next();
			
			int numBikesAvailable = Integer.MIN_VALUE;
			int capacity = Integer.MIN_VALUE;
			Date date = null;
			Day day = null;
			TimeOfDay timeOfDay = null;
			
			Object numBikesAvailObj = responseObj.get(_numBikesAvailableKey);
			if (numBikesAvailObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _numBikesAvailableKey));
			} else {
				try {
					numBikesAvailable = Integer.parseInt(numBikesAvailObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", numBikesAvailObj.toString(), _numBikesAvailableKey));
				}				
			}
						
			Object capacityObj = responseObj.get(_capacityKey);
			if (capacityObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _capacityKey));
			} else {
				try {
					capacity = Integer.parseInt(capacityObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", capacityObj.toString(), _capacityKey));
				}				
			}
			
			Object dateObj = responseObj.get(_dateKey);
			if (dateObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _dateKey));
			} else {
				try {
					date = _dateFormat.parse(dateObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a date. Station Status will not be fully populated.", dateObj.toString(), _dateKey));
				}				
			}
			
			Object dayObj = responseObj.get(_dayKey);
			if (dayObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _dayKey));
			} else {
				try {
					day = Day.valueOf(dayObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a Day. Station Status will not be fully populated.", dayObj.toString(), _dayKey));
				}				
			}
			
			Object timeOfDayObj = responseObj.get(_timeOfDayKey);
			if (timeOfDayObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _timeOfDayKey));
			} else {
				try {
					timeOfDay = TimeOfDay.valueOf(timeOfDayObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", timeOfDayObj.toString(), _timeOfDayKey));
				}				
			}
			
			returnList.add(new StationStatus(numBikesAvailable, capacity, date, day, timeOfDay));
		}
		
		return returnList;
	}

}
