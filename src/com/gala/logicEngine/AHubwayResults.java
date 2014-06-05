package com.gala.logicEngine;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;

public abstract class AHubwayResults implements IHubwayResults {

	protected Day 		_dayOfWeek;
	protected TimeOfDay _timeOfDay;
	protected Station 	_startStation;
	protected boolean 	_excludeDayParam;
	protected int 		_sampleSetSize;
	
	protected AHubwayResults(final Day day_, final TimeOfDay tod_, 
			final Station startStation_, final boolean excludeDayParam_, 
			final int sampleSetSize_) {
		_dayOfWeek = day_;
		_timeOfDay = tod_;
		_startStation = startStation_;
		_excludeDayParam = excludeDayParam_;
		_sampleSetSize = sampleSetSize_;
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
	
	public int getSampleSetSize() {
		return _sampleSetSize;
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
	
	public void setSampleSetSize(final int sampleSetSize_){
		_sampleSetSize = sampleSetSize_;
	}
	
	public String printResults(){
		StringBuilder builder = new StringBuilder();
		builder.append("Sample set size= ");
		builder.append(_sampleSetSize);
		builder.append("\nDay of week= ");
		builder.append(_dayOfWeek);
		builder.append("\nTime of day= ");
		builder.append(_timeOfDay);
		builder.append("\nStarting station= ");
		builder.append(_startStation.printSummary());
		builder.append("\nExcluded day of week= ");
		builder.append(_excludeDayParam);
		return builder.toString();
	}
}
