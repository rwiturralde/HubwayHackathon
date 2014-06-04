package com.gala.ui;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;
import com.gala.core.Weather;

public class HubwayRequestParameters {

	protected Day _day;
	protected TimeOfDay _timeOfDay;
	protected Station _startStation;
	protected Weather _weather;
	protected RequestType _requestType;
	
	public HubwayRequestParameters() {
		_day = null;
		_timeOfDay = null;
		_startStation = null;
		_weather = null;
		_requestType = null;
	}
	
	public HubwayRequestParameters(Day day_, TimeOfDay timeOfDay_, Station startStation_, Weather weather_, RequestType requestType_) {
		_day = day_;
		_timeOfDay = timeOfDay_;
		_startStation = startStation_;
		_weather = weather_;
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

	public Weather getWeather() {
		return _weather;
	}


	public void setWeather(Weather weather_) {
		this._weather = weather_;
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
		sb.append("Weather: ");
		sb.append(_weather);
		
		
		return sb.toString();
	}
}
