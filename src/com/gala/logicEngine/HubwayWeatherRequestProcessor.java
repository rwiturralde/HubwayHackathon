package com.gala.logicEngine;

import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.core.StationStatus;
import com.gala.ui.HubwayRequestParameters;

public class HubwayWeatherRequestProcessor implements IRequestProcessor{

	static final Logger _logger = Logger.getLogger(HubwayWeatherRequestProcessor.class);
	
	protected IDataRetriever<String> _weatherDataRetriever;
	protected IDataRetriever<StationStatus> _stationStatusDataRetriever;
	
	public HubwayWeatherRequestProcessor(final IDataRetriever<String> weatherDataRetriever_, 
			final IDataRetriever<StationStatus> stationStatusDataRetriever_){
		_weatherDataRetriever = weatherDataRetriever_;
		_stationStatusDataRetriever = stationStatusDataRetriever_;
	}
	
	public HubwayResults processRequest(HubwayRequestParameters parameters_){
		
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation().getId(), 
				parameters_.getTimeOfDay(), parameters_.getTemperature(), null, QueryType.WEATHER_DATES);
		List<String> datesMatchingWeather = _weatherDataRetriever.retrieveData(params);
		_logger.info(String.format("Weather data set size of %d for params - Temp Range: %s Time of Day: %s", 
				datesMatchingWeather.size(), parameters_.getTemperature(), parameters_.getTimeOfDay()));
		
		// Get station status for those dates in time range
		params.setValidDates(datesMatchingWeather);
		params.setQueryType(QueryType.STATION_STATUS_FOR_DATETIME);
		List<StationStatus> stationStatusList = _stationStatusDataRetriever.retrieveData(params);
		
		return convertResponseToResult(stationStatusList, parameters_.getStartStation());
	}

	protected HubwayResults convertResponseToResult(List<StationStatus> stationStatusList_, Station station_){
		HubwayResults results = new HubwayWeatherResults();
		
		if (stationStatusList_ != null && stationStatusList_.size() > 0){
			_logger.info(String.format("Station Status list size: %d", stationStatusList_.size()));
			double total = 0.0;
			for (StationStatus ss : stationStatusList_){
				total += ss.getNumBikesAvailable();
			}
			
			double avg = total / stationStatusList_.size();
			int expectedNumBikes = (int)Math.floor(avg);
			_logger.info(String.format("Average number of bikes available %f", avg));
			_logger.info(String.format("Expected number of bikes available %d", expectedNumBikes));
			results = new HubwayWeatherResults(avg, expectedNumBikes);
		} else {
			_logger.info(String.format("Station Status list empty for station Id %s", station_.getId()));
		}
			
		return results;
		
	}
	
}
