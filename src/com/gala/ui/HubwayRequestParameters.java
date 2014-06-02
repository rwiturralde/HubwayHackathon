package com.gala.ui;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;

public class HubwayRequestParameters {

	protected Day _day;
	protected TimeOfDay _timeOfDay;
	protected Station _startStation;
	
	public HubwayRequestParameters(Day day_, TimeOfDay timeOfDay_, Station startStation_) {
		_day = day_;
		_timeOfDay = timeOfDay_;
		_startStation = startStation_;
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



	public Station getStartStationId() {
		return _startStation;
	}



	public void setStartStationId(Station startStation_) {
		this._startStation = startStation_;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Day: ");
		sb.append(_day);
		sb.append(" | ");
		sb.append("TimeOfDay: ");
		sb.append(_timeOfDay);
		sb.append(" | ");
		sb.append("StartStation: ");
		sb.append(_startStation);
		
		return sb.toString();
	}
}
