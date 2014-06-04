package com.gala.logicEngine;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;

public abstract class AHubwayResults implements IHubwayResults {

	protected Day _dayOfWeek;
	protected TimeOfDay _timeOfDay;
	protected Station _startStation;
	protected boolean _excludeDayParam;
	
	protected AHubwayResults(final Day day_, final TimeOfDay tod_, 
			final Station startStation_, final boolean excludeDayParam_) {
		_dayOfWeek = day_;
		_timeOfDay = tod_;
		_startStation = startStation_;
		_excludeDayParam = excludeDayParam_;
	}
	
	public Day getDayOfWeek(){
		return _dayOfWeek;
	}
	
	public TimeOfDay getTimeOfDay(){
		return _timeOfDay;
	}
	
	public Station getStartStation(){
		return _startStation;
	}
	
	public boolean getExcludeDayParam(){
		return _excludeDayParam;
	}
	
	public void setDayOfWeek(final Day day_){
		_dayOfWeek = day_;
	}
	
	public void setTimeOfDay(final TimeOfDay tod_){
		_timeOfDay = tod_;
	}
	
	public void setStartStation(final Station startStation_){
		_startStation = startStation_;
	}
	
	public void setExcludeDayParam(final boolean excludeDayParam_){
		_excludeDayParam = excludeDayParam_;
	}
	
	public String printResults(){
		StringBuilder builder = new StringBuilder();
		builder.append("Day of week= ");
		builder.append(_dayOfWeek);
		builder.append("\nTime of day= ");
		builder.append(_timeOfDay);
		builder.append("\nStarting station= ");
		builder.append(_startStation);
		builder.append("\nExcluded day of week= ");
		builder.append(_excludeDayParam);
		return builder.toString();
	}
}
