package com.gala.core;

public enum Temperature {
	HUNDREDS (100, "Hundreds"),
	NINETIES (90, "Nineties"),
	EIGHTIES (80, "Eighties"),
	SEVENTIES (70, "Seventies"),
	SIXTIES (60, "Sixties"),
	FIFTIES (50, "Fifties"),
	FORTIES (40, "Forties"),
	THIRTIES (30, "Thirties"),
	TWENTIES (20, "Twenties"),
	TENS (10, "Tens"),
	ZEROES (0, "Zeroes");	
		
	protected int _lowerLimit;
	protected String _printName;
	
	Temperature(int lowerLimit_, String printName_) {
		_lowerLimit = lowerLimit_;
		_printName = printName_;
	}
	
	public int getTempLowerLimit() {
		return _lowerLimit;
	}
	
	
	public static Temperature getTemperature(int temp_){
		// ASSUMES ORDER OF THE ENUM VALUES ABOVE!!!!!!!!!!!!!!!!
		for (Temperature t : Temperature.values()){
			if (temp_ >= t.getTempLowerLimit()){
				return t;
			} 
		}		
		return null;
	}
	
	@Override
	public String toString() {
		return _printName;
	}
}
