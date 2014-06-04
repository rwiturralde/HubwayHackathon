package com.gala.logicEngine;

import java.util.HashMap;

public class HubwayTripResults extends HubwayResults {

	protected HashMap<String, Double> _possibleDestinations;
	
	public HubwayTripResults(){
		_possibleDestinations = new HashMap<String, Double>();
	}
	
	public HubwayTripResults(final HashMap<String, Double> possibleDestinations_) {
		_possibleDestinations = possibleDestinations_;
	}
	
	public HashMap<String, Double> getPossibleDestinations(){
		return _possibleDestinations;
	}
	
	public void getPossibleDestinations(final HashMap<String, Double> dest_){
		_possibleDestinations = dest_;
	}
	
	public String printResults(){
		StringBuilder builder = new StringBuilder();
		
		for (String key : _possibleDestinations.keySet()) {
			builder.append("Station name=");
			builder.append(key);
			builder.append(", Probability=");
			builder.append(_possibleDestinations.get(key));
		}
		
		return builder.toString();
	}
}
