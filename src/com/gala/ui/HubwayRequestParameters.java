package com.gala.ui;

import com.gala.core.Day;
import com.gala.core.TimeOfDay;

public class HubwayRequestParameters {

	protected Day _day;
	protected TimeOfDay _timeOfDay;
	protected int _startStationId;
	
	protected HubwayRequestParameters() {
		
	}
	
	public HubwayRequestParameters(Day day_, TimeOfDay timeOfDay_, int startStationId_) {
		_day = day_;
		_timeOfDay = timeOfDay_;
		_startStationId = startStationId_;
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
