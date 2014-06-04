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
}
