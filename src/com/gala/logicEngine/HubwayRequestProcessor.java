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
		//List<Date> datesMatchingWeather = _dataRetriever(QueryType.WEATHER_DATES, Temperature.ZEROES, Date.class);
		
		// Get station status for those dates in time range
		//List<StationStatus> stationStatusList = _dataRetriever(QueryType.STATION_STATUS_FOR_DATETIME, StationStatus.class);
		
		//if ((_dataRetriever.retrieveData()) != null)
		//	return results;
		
		_logger.info("Data could not be retrieved from the database.");
		return null;
	}
	
}
