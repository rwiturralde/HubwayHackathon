package com.gala.core;

public enum TimeOfDay {
	EVENING (16, 0, "Evening - (4pm - 11:59pm)"),
	MIDDAY (11, 0, "Midday - (11am - 4pm)"),
	MORNING (0, 0, "Morning - (12am - 11am)");
	
	protected int _startHour;
	protected int _startMinute;
	protected String _printName;
	
	TimeOfDay(int startHour_, int startMinute_, String printName_) {
		_startHour = startHour_;
		_startMinute = startMinute_;
		_printName = printName_;
	}
	
	public int getStartHour() {
		return _startHour;
	}
	
	public int getStartMinute() {
		return _startMinute;
	}
	
	public static TimeOfDay getTimeOfDay(int startHour_, int startMinute_){
		// RELIES ON THE ORDERING OF THE ENUM VALUES ABOVE !!!!!!!!!!!!!!!!!!!
		for (TimeOfDay tod : TimeOfDay.values()){
			if (startHour_ > tod.getStartHour() || (startHour_ == tod.getStartHour() && startMinute_ >= tod.getStartMinute())){
				return tod;
			} 
		}		
		return null;
	}
	
	@Override
	public String toString() {
		return _printName;
	}
}
