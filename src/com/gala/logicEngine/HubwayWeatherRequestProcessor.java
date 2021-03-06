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
	protected int							_resultThreshold;
	
	public HubwayWeatherRequestProcessor(final IDataRetriever<String> weatherDataRetriever_, 
			final IDataRetriever<StationStatus> stationStatusDataRetriever_,
			final int resultThreshold_){
		_weatherDataRetriever = weatherDataRetriever_;
		_stationStatusDataRetriever = stationStatusDataRetriever_;
		_resultThreshold = resultThreshold_;
	}
	
	public IHubwayResults processRequest(final HubwayRequestParameters parameters_){
		
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation(), 
				parameters_.getTimeOfDay(), parameters_.getDay(), 
				parameters_.getWeather(), QueryType.WEATHER_DATES);
		
		long queryStartTime, queryEndTime;
		
		queryStartTime = System.currentTimeMillis();
		List<String> datesMatchingWeather = _weatherDataRetriever.retrieveData(params);
		queryEndTime = System.currentTimeMillis();
		_logger.info(String.format("Weather data set size of %d returned in %d ms for params - Temp Range: %s Time of Day: %s", 
				datesMatchingWeather.size(), (queryEndTime - queryStartTime), parameters_.getWeather().getTemperature(), parameters_.getTimeOfDay()));
		
		// Get station status for those dates in time range
		params.setValidDates(datesMatchingWeather);
		params.setQueryType(QueryType.STATION_STATUS_FOR_DATETIME);
		queryStartTime = System.currentTimeMillis();
		List<StationStatus> stationStatusList = _stationStatusDataRetriever.retrieveData(params);
		queryEndTime = System.currentTimeMillis();
		_logger.info(String.format("Station status query returned %d results in %d ms.", 
				stationStatusList.size(), (queryEndTime - queryStartTime)));
		
		if (stationStatusList.size() < _resultThreshold) {
			_logger.warn(String.format("Station status result set size is under threshold of %d. Excluding day parameter and querying again.", 
					_resultThreshold));
			params.setExcludeDayParam(true);
			queryStartTime = System.currentTimeMillis();
			stationStatusList = _stationStatusDataRetriever.retrieveData(params);
			queryEndTime = System.currentTimeMillis();
			_logger.info(String.format("Station status query returned %d results in %d ms with day parameter excluded.", 
					stationStatusList.size(), (queryEndTime - queryStartTime)));
		}
		
		return convertResponseToResult(params, stationStatusList);
	}

	protected IHubwayResults convertResponseToResult(final MongoDbQueryParameters parameters_,
			final List<StationStatus> stationStatusList_){
		
		double avg = 0.0;
		int expectedNumBikes = 0;
		
		if (stationStatusList_ != null && stationStatusList_.size() > 0){
			_logger.info(String.format("Station Status list size: %d", stationStatusList_.size()));
			double total = 0.0;
			for (StationStatus ss : stationStatusList_){
				total += ss.getNumBikesAvailable();
			}
			
			avg = total / stationStatusList_.size();
			expectedNumBikes = (int)Math.floor(avg);
			_logger.info(String.format("Average number of bikes available %f", avg));
			_logger.info(String.format("Expected number of bikes available %d", expectedNumBikes));
		} else {
			_logger.info(String.format("Station Status list empty for station Id %s", parameters_.getStartStation().getId()));
		}
			
		return new HubwayWeatherResults(parameters_.getDay(), parameters_.getTimeOfDay(), 
				parameters_.getStartStation(), parameters_.getExcludeDayParam(), stationStatusList_.size(), 
				parameters_.getWeather(), expectedNumBikes);
	}
	
}
