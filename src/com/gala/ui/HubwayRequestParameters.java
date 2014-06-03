package com.gala.ui;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;

public class HubwayRequestParameters {

	protected Day _day;
	protected TimeOfDay _timeOfDay;
	protected Station _startStation;
	protected Temperature _temperature;
	protected RequestType _requestType;
	
	public HubwayRequestParameters() {
		_day = null;
		_timeOfDay = null;
		_startStation = null;
		_temperature = null;
		_requestType = null;
	}
	
	public HubwayRequestParameters(Day day_, TimeOfDay timeOfDay_, Station startStation_, Temperature temperature_, RequestType requestType_) {
		_day = day_;
		_timeOfDay = timeOfDay_;
		_startStation = startStation_;
		_temperature = temperature_;
		_requestType = requestType_;				
	}
	
	public RequestType getRequestType() {
		return _requestType;
	}

	public void setRequestType(RequestType requestType_) {
		_requestType = requestType_;
	}
	
	public Day getDay() {
		return _day;
	}

	public void setDay(Day day_) {
		this._day = day_;
	}

	public TimeOfDay getTimeOfDay() {
		return _timeOfDay;
	}

	public void setTimeOfDay(TimeOfDay timeOfDay_) {
		this._timeOfDay = timeOfDay_;
	}

	public Station getStartStation() {
		return _startStation;
	}

	public void setStartStation(Station startStation_) {
		this._startStation = startStation_;
	}

	public Temperature getTemperature() {
		return _temperature;
	}


	public void setTemperature(Temperature temperature_) {
		this._temperature = temperature_;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RequestType: ");
		sb.append(_requestType);
		sb.append(" | ");
		sb.append("Day: ");
		sb.append(_day);
		sb.append(" | ");
		sb.append("TimeOfDay: ");
		sb.append(_timeOfDay);
		sb.append(" | ");
		sb.append("StartStation: ");
		sb.append(_startStation);
		sb.append(" | ");
		sb.append("Temperature: ");
		sb.append(_temperature);
		
		
		return sb.toString();
	}
}
