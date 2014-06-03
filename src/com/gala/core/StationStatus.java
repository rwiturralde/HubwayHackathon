package com.gala.core;

import java.util.Date;

public class StationStatus {

	protected int _numBikesAvailable;
	protected int _capactity;
	protected Date _date;
	protected Day _day;
	protected TimeOfDay _timeOfDay;
	
	public StationStatus(int numBikesAvailable_, int capactity_, Date date_,
			Day day_, TimeOfDay timeOfDay_) {
		super();
		_numBikesAvailable = numBikesAvailable_;
		_capactity = capactity_;
		_date = date_;
		_day = day_;
		_timeOfDay = timeOfDay_;
	}
	public int getCapactity() {
		return _capactity;
	}
	public void setCapactity(int capactity) {
		_capactity = capactity;
	}
	public Day getDay() {
		return _day;
	}
	public void setDay(Day day) {
		_day = day;
	}	
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
