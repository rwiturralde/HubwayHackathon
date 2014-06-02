package com.gala.logicEngine;

import java.util.ArrayList;
import java.util.List;

import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;

public class MongoDbQueryParameters {

	protected int _startStationId;
	protected TimeOfDay _timeOfDay;
	protected Temperature _temperature;
	protected List<String> _validDates;
	
	public MongoDbQueryParameters(){
		_startStationId = 0;
		_timeOfDay = TimeOfDay.MORNING;
		_temperature = Temperature.ZEROES;
		_validDates = new ArrayList<String>();
	}
	
	public MongoDbQueryParameters(final int startStationId_, final TimeOfDay tod_, final Temperature temp_, final List<String> dates_){
		_startStationId = startStationId_;
		_timeOfDay = tod_;
		_temperature = temp_;
		_validDates = dates_;
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
	
}
