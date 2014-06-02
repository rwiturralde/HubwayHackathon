package com.gala.logicEngine;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.StationStatus;
import com.gala.core.Temperature;
import com.gala.ui.HubwayRequestParameters;

public class HubwayRequestProcessor implements IRequestProcessor{

	static final Logger _logger = Logger.getLogger(HubwayRequestProcessor.class);
	
	protected IDataRetriever _dataRetriever;
	
	public HubwayRequestProcessor(IDataRetriever dataRetriever_){
		_dataRetriever = dataRetriever_;
	}
	
	public HubwayResults processRequest(HubwayRequestParameters parameters_){
		HubwayResults results = new HubwayResults();
		
		// Get dates with weather matching temp
		MongoDbQueryParameters params = new MongoDbQueryParameters(parameters_.getStartStation().getId(), 
				parameters_.getTimeOfDay(), parameters_.getTemperature(), null);
		List<String> datesMatchingWeather = _dataRetriever.retrieveData(QueryType.WEATHER_DATES, params);
		_logger.info(String.format("Weather data set size of %s for params - Temp Range: %s Time of Day: %s", 
				datesMatchingWeather.size(), Temperature.SEVENTIES, parameters_.getTimeOfDay()));
		
		// Get station status for those dates in time range
		params.setValidDates(datesMatchingWeather);
		List<StationStatus> stationStatusList = _dataRetriever.retrieveData(QueryType.STATION_STATUS_FOR_DATETIME, params);
		
		if (stationStatusList != null && stationStatusList.size() > 0){
			_logger.info(String.format("Station Status list size: %s", stationStatusList.size()));
			double avg = 0.0;
			for (StationStatus ss : stationStatusList){
			}
		} else {
			_logger.info(String.format("Station Status list empty for parameters - %s", parameters_.getStartStation().getId()));
		}
			
		return null;
	}
	
}
