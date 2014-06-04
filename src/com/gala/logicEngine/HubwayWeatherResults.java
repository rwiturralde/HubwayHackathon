package com.gala.logicEngine;

public class HubwayWeatherResults implements IHubwayResults {

	protected double _avgAvailability;
	protected int _expNumBikes;
	
	public HubwayWeatherResults(){
		_avgAvailability = 0.0;
		_expNumBikes = 0;
	}
	
	public HubwayWeatherResults(final double avgCapacity_, final int expNumBikes_){
		_avgAvailability = avgCapacity_;
		_expNumBikes = expNumBikes_;
	}
	
	public double getAvgCapacity(){
		return _avgAvailability;
	}
	
	public int getExpNumBikes(){
		return _expNumBikes;
	}
	
	public String printResults(){
		StringBuilder builder = new StringBuilder();
		builder.append("Average availability=");
		builder.append(_avgAvailability);
		builder.append(", Expected number of bikes available=");
		builder.append(_expNumBikes);
		return builder.toString();
	}
}
