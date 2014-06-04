package com.gala.logicEngine;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.ui.HubwayRequestParameters;

public class HubwayTripRequestProcessor implements IRequestProcessor {

	static final Logger 						_logger = Logger.getLogger(HubwayTripRequestProcessor.class);
	
	protected IDataRetriever<SimpleEntry<String, Integer>> _tripsDataRetriever;
	protected IDataRetriever<Station> 					   _stationInfoRetriever;
	protected int										   _numResultsToReturn;
	protected int										   _resultThreshold;
	
	public HubwayTripRequestProcessor(final IDataRetriever<SimpleEntry<String, Integer>> tripsDataRetriever_,
			final IDataRetriever<Station> stationInfoRetriever_, final int numResults_, final int resultThreshold_) {
		_tripsDataRetriever = tripsDataRetriever_;
		_stationInfoRetriever = stationInfoRetriever_;
		_numResultsToReturn = numResults_;
		_resultThreshold = resultThreshold_;
	}

	public IHubwayResults processRequest(final HubwayRequestParameters parameters_) {
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation().getId(), 
				parameters_.getTimeOfDay(), parameters_.getDay(), parameters_.getTemperature(), QueryType.TRIP_END_STATION);
		
		long queryStartTime, queryEndTime;
		queryStartTime = System.currentTimeMillis();
		List<SimpleEntry<String,Integer>> endStationCounts = _tripsDataRetriever.retrieveData(params);
		queryEndTime = System.currentTimeMillis();
		
		int totalTrips = calculateTotalTrips(endStationCounts);
		_logger.info(String.format("Trips query returned %d results in %d ms.", 
				totalTrips, (queryEndTime - queryStartTime)));
		
		if (totalTrips < _resultThreshold) {
			_logger.warn(String.format("Trips results set size is under threshold of %d. Excluding day parameter and querying again.", _resultThreshold));
			params.setExcludeDayParam(true);
			queryStartTime = System.currentTimeMillis();
			endStationCounts = _tripsDataRetriever.retrieveData(params);
			queryEndTime = System.currentTimeMillis();
			
			totalTrips = calculateTotalTrips(endStationCounts);
			_logger.info(String.format("Trips query returned %d results in %d ms.", 
					totalTrips, (queryEndTime - queryStartTime)));
		}
		
		HashMap<String, Double> probMap = calculateProbabilites(endStationCounts, totalTrips);
		
		// List returned from query is sorted in descending order
		List<String> topStations = new ArrayList<String>(_numResultsToReturn);
		for(int i = 0; i < endStationCounts.size(); i++) {
			if (i == _numResultsToReturn)
				break;
			
			topStations.add(endStationCounts.get(i).getKey());
		}
		
		// Get station info for the top stations
		params.setStationList(topStations);
		params.setQueryType(QueryType.STATION_NAMES_FOR_LIST);
		queryStartTime = System.currentTimeMillis();
		List<Station> stationInfoList = _stationInfoRetriever.retrieveData(params);
		queryEndTime = System.currentTimeMillis();
		_logger.info(String.format("Station data query returned %d results in %d ms.", 
				stationInfoList.size(), (queryEndTime - queryStartTime)));		
		
		return convertResponseToResult(probMap, stationInfoList);
	}
	
	protected int calculateTotalTrips(final List<SimpleEntry<String,Integer>> endStationCounts_) {
		int totalTrips = 0;
		for (SimpleEntry<String,Integer> entry : endStationCounts_){
			totalTrips += entry.getValue();
		}
		return totalTrips;
	}
	
	protected HashMap<String, Double> calculateProbabilites(final List<SimpleEntry<String,Integer>> endStationCounts_, final int totalTrips){
		
		HashMap<String, Double> endStationProbabilities = new HashMap<String, Double>();
		
		if (totalTrips != 0){
			for (SimpleEntry<String,Integer> entry : endStationCounts_){
				double val = entry.getValue()/(double)totalTrips;
				endStationProbabilities.put(entry.getKey(), val);
			}
		}
		
		return endStationProbabilities;		
	}
	
	/**
	 * Combine the station info with the probability map to create
	 * the list of station names to probabilities
	 * @param probMap
	 * @param stationInfoList
	 * @return
	 */
	protected IHubwayResults convertResponseToResult(final HashMap<String, Double> probMap, 
			final List<Station> stationInfoList) {
		
		HashMap<String, Double> resultMap = new HashMap<String,Double>();
		for(Station entry : stationInfoList) {
			String stationName = entry.getName();
			resultMap.put(stationName, probMap.get(Integer.toString(entry.getId())));
		}
		
		return new HubwayTripResults(resultMap);
	}
	
	

}
