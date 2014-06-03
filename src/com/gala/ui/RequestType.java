package com.gala.ui;

public enum RequestType {
	WEATHER ("BIKE DEPLETION PREDICTOR: User will enter planned departure date, planned departure time of day, and planned departure station. \n\t Application will return the expected number of bikes at that station based on historical weather data."),
	TRIP ("DESTINATION PREDICTOR: User will enter departure date, departure time of day, and departure station. \n\t Application will return the expected destination with a probability ranking based on historical trip data.");

	protected String _printSummary;
	
	RequestType(String printSummary_) {
		_printSummary = printSummary_;
	}
	
	public String getPrintSummary(){
		return _printSummary;
	}
}
