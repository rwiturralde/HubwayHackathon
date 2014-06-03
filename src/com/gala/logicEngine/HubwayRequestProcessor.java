package com.gala.logicEngine;

import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.core.StationStatus;
import com.gala.ui.HubwayRequestParameters;

public class HubwayRequestProcessor implements IRequestProcessor{

	static final Logger _logger = Logger.getLogger(HubwayRequestProcessor.class);
	
	protected IDataRetriever _dataRetriever;
	
	public HubwayRequestProcessor(IDataRetriever dataRetriever_){
		_dataRetriever = dataRetriever_;
	}
	
	public HubwayResults processRequest(HubwayRequestParameters parameters_){
		
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation().getId(), 
				parameters_.getTimeOfDay(), parameters_.getTemperature(), null);
		List<String> datesMatchingWeather = _dataRetriever.retrieveData(QueryType.WEATHER_DATES, params);
		_logger.info(String.format("Weather data set size of %d for params - Temp Range: %s Time of Day: %s", 
				datesMatchingWeather.size(), parameters_.getTemperature(), parameters_.getTimeOfDay()));
		
		// Get station status for those dates in time range
		params.setValidDates(datesMatchingWeather);
		List<StationStatus> stationStatusList = _dataRetriever.retrieveData(QueryType.STATION_STATUS_FOR_DATETIME, params);
		
		return convertResponseToResult(stationStatusList, parameters_.getStartStation());
	}

	protected HubwayResults convertResponseToResult(List<StationStatus> stationStatusList_, Station station_){
		HubwayResults results = new HubwayResults();
		
		if (stationStatusList_ != null && stationStatusList_.size() > 0){
			_logger.info(String.format("Station Status list size: %d", stationStatusList_.size()));
			double total = 0.0;
			for (StationStatus ss : stationStatusList_){
				total += ss.getNumBikesAvailable() / ss.getCapactity();
			}
			
			double avg = total / stationStatusList_.size();
			_logger.info(String.format("Average availability calculated as %f%", avg));
			int expectedNumBikes = (int) Math.floor(avg * station_.getCapacity());
			_logger.info(String.format("Expected number of bikes available %f%", expectedNumBikes));
			results = new HubwayResults(avg, expectedNumBikes);
		} else {
			_logger.info(String.format("Station Status list empty for station Id %s", station_.getId()));
		}
			
		return results;
		
	}
	
}
