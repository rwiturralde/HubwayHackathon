package com.gala.logicEngine;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;
import com.gala.core.Weather;

public class HubwayWeatherResults extends AHubwayResults {

	protected Weather _weather;
	protected double _avgAvailability;
	protected int _expNumBikes;
	
	public HubwayWeatherResults(final Day day_, final TimeOfDay tod_, final Station startStation_, final boolean excludeDayParam_, final Weather weather_){
		super(day_, tod_, startStation_, excludeDayParam_);
		_avgAvailability = 0.0;
		_expNumBikes = 0;
		_weather = weather_;
	}
	
	public HubwayWeatherResults(final Day day_, final TimeOfDay tod_, final Station startStation_, final boolean excludeDayParam_,
			final Weather weather_, final double avgCapacity_, final int expNumBikes_){
		super(day_, tod_, startStation_, excludeDayParam_);
		_avgAvailability = avgCapacity_;
		_expNumBikes = expNumBikes_;
		_weather = weather_;
	}
	
	public double getAvgCapacity(){
		return _avgAvailability;
	}
	
	public int getExpNumBikes(){
		return _expNumBikes;
	}
	
	public String printResults(){
		
		String baseResult = super.printResults();
		
		StringBuilder builder = new StringBuilder();
		builder.append("\nWeather= ");
		builder.append(_weather);
		builder.append("\nAverage availability= ");
		builder.append(_avgAvailability);
		builder.append("\nExpected number of bikes available= ");
		builder.append(_expNumBikes);
		
		return String.format("%s%s", baseResult, builder.toString());
	}
}
