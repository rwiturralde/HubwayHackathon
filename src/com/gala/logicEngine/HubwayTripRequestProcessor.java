package com.gala.logicEngine;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.core.StationStatus;
import com.gala.ui.HubwayRequestParameters;

public class HubwayTripRequestProcessor implements IRequestProcessor {

	static final Logger 						_logger = Logger.getLogger(HubwayTripRequestProcessor.class);
	
	protected IDataRetriever<SimpleEntry<String, Integer>> _endStationDataRetriever;
	protected IDataRetriever<SimpleEntry<String, Station>> _stationInfoRetriever;
	protected int										   _numResultsToReturn;
	
	public HubwayTripRequestProcessor(final IDataRetriever<SimpleEntry<String, Integer>> endStationDataRetriever_,
			final IDataRetriever<SimpleEntry<String, Station>> stationInfoRetriever_,
			final int numResults_) {
		_endStationDataRetriever = endStationDataRetriever_;
		_stationInfoRetriever = stationInfoRetriever_;
		_numResultsToReturn = numResults_;
	}

	public HubwayResults processRequest(HubwayRequestParameters parameters_) {
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation().getId(), 
				parameters_.getTimeOfDay(), parameters_.getTemperature(), null, QueryType.WEATHER_DATES);
		List<SimpleEntry<String,Integer>> endStationCounts = _endStationDataRetriever.retrieveData(params);
		
		HashMap<String, Double> probMap = calculateProbabilites(endStationCounts);
		
		// List returned from query is sorted in descending order
		List<String> topStations = new ArrayList<String>(_numResultsToReturn);
		for(int i = 0; i < endStationCounts.size(); i++) {
			if (i == _numResultsToReturn)
				break;
			
			topStations.add(endStationCounts.get(i).getKey());
		}
		
		// Get station status for those dates in time range
		params.setStationList(topStations);
		params.setQueryType(QueryType.STATION_NAMES_FOR_LIST);
		List<SimpleEntry<String, Station>> stationInfoList = _stationInfoRetriever.retrieveData(params);
		
		return convertResponseToResult(probMap, stationInfoList);
	}
	
	protected HashMap<String, Double> calculateProbabilites(final List<SimpleEntry<String,Integer>> endStationCounts_){
		
		int totalTrips = 0;
		for (SimpleEntry<String,Integer> entry : endStationCounts_){
			totalTrips += entry.getValue();
		}
		
		HashMap<String, Double> endStationProbabilities = new HashMap<String, Double>();
		for (SimpleEntry<String,Integer> entry : endStationCounts_){
			double val = entry.getValue()/totalTrips;
			endStationProbabilities.put(entry.getKey(), val);
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
	protected HubwayResults convertResponseToResult(final HashMap<String, Double> probMap, 
			final List<SimpleEntry<String, Station>> stationInfoList) {
		
		HashMap<String, Double> resultMap = new HashMap<String,Double>();
		for(SimpleEntry<String, Station> entry : stationInfoList) {
			String stationName = entry.getValue().getName();
			resultMap.put(stationName, probMap.get(entry.getKey()));
		}
		
		return new HubwayTripResults(resultMap);
	}
	
	

}
