package com.gala.core;

import java.util.Calendar;

public enum Day {
	SUNDAY (false, "Sunday"), 
	MONDAY (true, "Monday"), 
	TUESDAY (true, "Tuesday"), 
	WEDNESDAY (true, "Wednesday"),
    THURSDAY (true, "Thursday"), 
    FRIDAY (true, "Friday"), 
    SATURDAY (false, "Saturday");
	
	protected boolean _isWeekday;
	protected String _printName;
	protected int _calendarIntDay;
	
	Day(boolean isWeekday_, String printName_) {
		_isWeekday = isWeekday_;
		_printName = printName_;
	}
	
	public boolean isWeekday() {
		return _isWeekday;
	}
	
	public static Day fromCalendar(Calendar cal_) {
		
		Day returnDay = null;
		
		switch(cal_.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 
				returnDay = Day.SUNDAY;
				break;
			case Calendar.MONDAY: 
				returnDay = Day.MONDAY;
				break;
			case Calendar.TUESDAY: 
				returnDay = Day.TUESDAY;
				break;
			case Calendar.WEDNESDAY: 
				returnDay = Day.WEDNESDAY;
				break;
			case Calendar.THURSDAY: 
				returnDay = Day.THURSDAY;
				break;
			case Calendar.FRIDAY: 
				returnDay = Day.FRIDAY;
				break;
			case Calendar.SATURDAY: 
				returnDay = Day.SATURDAY;
				break;
			}
			
			return returnDay;
	}
	
	@Override
	public String toString() {
		return _printName;
	}
}
