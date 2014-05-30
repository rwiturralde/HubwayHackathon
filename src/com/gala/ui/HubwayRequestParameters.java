package com.gala.ui;

import com.gala.core.Day;
import com.gala.core.TimeOfDay;

public class HubwayRequestParameters {

	protected Day _day;
	protected TimeOfDay _timeOfDay;
	protected int _startStationId;
	
	public HubwayRequestParameters(Day day_, TimeOfDay timeOfDay_, int startStationId_) {
		_day = day_;
		_timeOfDay = timeOfDay_;
		_startStationId = startStationId_;
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



	public int getStartStationId() {
		return _startStationId;
	}



	public void setStartStationId(int startStationId_) {
		this._startStationId = startStationId_;
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
		sb.append("StartStationId: ");
		sb.append(_startStationId);
		
		return sb.toString();
	}
}
