package com.gala.core;

import java.util.Date;

public class StationStatus {

	protected int _numBikesAvailable;
	protected Date _date;
	protected TimeOfDay _timeOfDay;
	
	public int getNumBikesAvailable() {
		return _numBikesAvailable;
	}
	public void setNumBikesAvailable(int numBikesAvailable_) {
		_numBikesAvailable = numBikesAvailable_;
	}
	public Date getDate() {
		return _date;
	}
	public void setDate(Date date_) {
		_date = date_;
	}
	public TimeOfDay getTimeOfDay() {
		return _timeOfDay;
	}
	public void setTimeOfDay(TimeOfDay timeOfDay_) {
		_timeOfDay = timeOfDay_;
	}

}
