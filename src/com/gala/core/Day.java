package com.gala.core;

public enum Day {
	SUNDAY (false, 1, "Sunday"), 
	MONDAY (true, 2, "Monday"), 
	TUESDAY (true, 3, "Tuesday"), 
	WEDNESDAY (true, 4, "Wednesday"),
    THURSDAY (true, 5, "Thursday"), 
    FRIDAY (true, 6, "Friday"), 
    SATURDAY (false, 7, "Saturday");
	
	protected boolean _isWeekday;
	protected String _printName;
	protected int _calendarIntDay;
	
	Day(boolean isWeekday_, int calendarIntDay_, String printName_) {
		_isWeekday = isWeekday_;
		_calendarIntDay = calendarIntDay_;
		_printName = printName_;
	}
	
	public boolean isWeekday() {
		return _isWeekday;
	}
	
	public int getCalendarInt(){
		return _calendarIntDay;
	}
	
	public static Day getDay(int calendarInt_){
		for (Day d : Day.values()){
			if (calendarInt_ == d._calendarIntDay){
				return d;
			} 
		}		
		return null;
	}
	
	@Override
	public String toString() {
		return _printName;
	}
}
