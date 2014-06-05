package com.gala.logicEngine;

import java.util.HashMap;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;

public class HubwayTripResults extends AHubwayResults{

	protected HashMap<String, Double> _possibleDestinations;	
	
	public HubwayTripResults(final Day day_, final TimeOfDay tod_, final Station startStation_, final boolean excludeDayParam_,
			final int sampleSetSize_){
		super(day_, tod_, startStation_, excludeDayParam_, sampleSetSize_);
		_possibleDestinations = new HashMap<String, Double>();
	}
	
	public HubwayTripResults(final Day day_, final TimeOfDay tod_, final Station startStation_, final boolean excludeDayParam_, 
			final int sampleSetSize_, final HashMap<String, Double> possibleDestinations_) {
		super(day_, tod_, startStation_, excludeDayParam_, sampleSetSize_);
		_possibleDestinations = possibleDestinations_;
	}
	
	public HashMap<String, Double> getPossibleDestinations(){
		return _possibleDestinations;
	}
	
	public void getPossibleDestinations(final HashMap<String, Double> dest_){
		_possibleDestinations = dest_;
	}
	
	public String printResults(){
		
		String baseResult = super.printResults();
		StringBuilder builder = new StringBuilder();
		
		for (String key : _possibleDestinations.keySet()) {
			builder.append("\nEnding station name= ");
			builder.append(key);
			builder.append(", Probability= ");
			builder.append(_possibleDestinations.get(key));
		}
		
		return String.format("%s%s", baseResult, builder.toString());
	}
}
