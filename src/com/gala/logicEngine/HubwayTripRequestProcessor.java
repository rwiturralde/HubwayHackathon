package com.gala.logicEngine;

import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.gala.core.StationStatus;
import com.gala.ui.HubwayRequestParameters;

public class HubwayTripRequestProcessor implements IRequestProcessor {

	static final Logger 						_logger = Logger.getLogger(HubwayTripRequestProcessor.class);
	
	protected IDataRetriever<String> 			_weatherDataRetriever;
	protected IDataRetriever<StationStatus>		_stationStatusDataRetriever;
	
	public HubwayTripRequestProcessor() {
		// TODO Auto-generated constructor stub
	}

	public HubwayResults processRequest(HubwayRequestParameters parameters_) {
		return null;
	}
	
	protected HubwayResults convertResponseToResult(List<StationStatus> stationStatusList_, Station station_){
		return null;		
	}

}
