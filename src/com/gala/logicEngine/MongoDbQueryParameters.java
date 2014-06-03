package com.gala.logicEngine;

import java.util.List;

import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;

public class MongoDbQueryParameters {

	protected int 			_startStationId;
	protected TimeOfDay 	_timeOfDay;
	protected Temperature 	_temperature;
	protected List<String> 	_validDates;
	protected QueryType		_queryType;
	
	public MongoDbQueryParameters(final int startStationId_, final TimeOfDay tod_, final Temperature temp_, final List<String> dates_, final QueryType queryType_){
		_startStationId = startStationId_;
		_timeOfDay = tod_;
		_temperature = temp_;
		_validDates = dates_;
		_queryType = queryType_;
	}
	
	public int getStartStationId(){
		return _startStationId;
	}
	
	public TimeOfDay getTimeOfDay(){
		 return _timeOfDay;
	}
	
	public Temperature getTemperature(){
		return _temperature;
	}
	
	public List<String> getValidDates(){
		return _validDates;
	}
	
	public QueryType getQueryType(){
		return _queryType;
	}
	
	public void setStartStationId(int startStationId_){
		_startStationId = startStationId_;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay_){
		_timeOfDay = timeOfDay_;
	}
	
	public void setTemperature(Temperature temperature_){
		_temperature = temperature_;
	}
	
	public void setValidDates(List<String> validDates_){
		_validDates = validDates_;
	}
	
	public void setQueryType(QueryType queryType_){
		_queryType = queryType_;
	}
	
	
	
}
