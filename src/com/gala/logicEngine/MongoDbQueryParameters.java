package com.gala.logicEngine;

import java.util.List;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;
import com.gala.core.Weather;

public class MongoDbQueryParameters {

	protected Station		_startStation;
	protected TimeOfDay 	_timeOfDay;
	protected Day 			_day;
	protected Weather 		_weather;
	protected List<String> 	_validDates;
	protected QueryType		_queryType;
	protected List<String> 	_stationList;
	protected boolean 		_excludeDayParam;
	
	public MongoDbQueryParameters(final Station startStation_, final TimeOfDay tod_, final Day day_,
			final Weather weather_, final QueryType queryType_){
		_startStation = startStation_;
		_timeOfDay = tod_;
		_day = day_;
		_weather = weather_;
		_queryType = queryType_;
		_excludeDayParam = false;
	}
	
	public Station getStartStation(){
		return _startStation;
	}
	
	public TimeOfDay getTimeOfDay(){
		 return _timeOfDay;
	}
	
	public Day getDay(){
		 return _day;
	}
	
	public Weather getWeather(){
		return _weather;
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
	
	public void setStartStation(Station startStation_){
		_startStation = startStation_;
	}
	
	public void setTimeOfDay(TimeOfDay timeOfDay_){
		_timeOfDay = timeOfDay_;
	}
	
	public void setDay(Day day_){
		_day = day_;
	}
	
	public void setWeather(Weather weather_){
		_weather = weather_;
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
