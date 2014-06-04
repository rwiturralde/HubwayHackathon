package com.gala.logicEngine;

import java.util.List;

import com.gala.core.Day;
import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;

public class MongoDbQueryParameters {

	protected int 			_startStationId;
	protected TimeOfDay 	_timeOfDay;
	protected Day 			_day;
	protected Temperature 	_temperature;
	protected List<String> 	_validDates;
	protected QueryType		_queryType;
	protected List<String> 	_stationList;
	protected boolean 		_excludeDayParam;
	
	public MongoDbQueryParameters(final int startStationId_, final TimeOfDay tod_, final Day day_,
			final Temperature temp_, final QueryType queryType_){
		_startStationId = startStationId_;
		_timeOfDay = tod_;
		_day = day_;
		_temperature = temp_;
		_queryType = queryType_;
		_excludeDayParam = false;
	}
	
	public int getStartStationId(){
		return _startStationId;
	}
	
	public TimeOfDay getTimeOfDay(){
		 return _timeOfDay;
	}
	
	public Day getDay(){
		 return _day;
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
	
	public List<String> getStationList(){
		return _stationList;
	}
	
	public boolean getExcludeDayParam() {
		return _excludeDayParam;
	}
	
	public void setStartStationId(int startStationId_){
		_startStationId = startStationId_;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay_){
		_timeOfDay = timeOfDay_;
	}
	
	public void setDay(Day day_){
		_day = day_;
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
	
	public void setStationList(List<String> stationList_){
		_stationList = stationList_;
	}
	
	public void setExcludeDayParam(final boolean excludeDay_) {
		_excludeDayParam = excludeDay_;
	}
	
}
