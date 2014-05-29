package com.gala.core;

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
	
	Day(boolean isWeekday_, String printName_) {
		_isWeekday = isWeekday_;
		_printName = printName_;
	}
	
	public boolean isWeekday() {
		return _isWeekday;
	}
	
	@Override
	public String toString() {
		return _printName;
	}
}
