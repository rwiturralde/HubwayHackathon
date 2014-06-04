package com.gala.logicEngine;

public class HubwayWeatherResults extends HubwayResults {

	protected double _avgCapacity;
	protected int _expNumBikes;
	
	public HubwayWeatherResults(){
		_avgCapacity = 0.0;
		_expNumBikes = 0;
	}
	
	public HubwayWeatherResults(final double avgCapacity_, final int expNumBikes_){
		_avgCapacity = avgCapacity_;
		_expNumBikes = expNumBikes_;
	}
	
	public double getAvgCapacity(){
		return _avgCapacity;
	}
	
	public int getExpNumBikes(){
		return _expNumBikes;
	}
}
