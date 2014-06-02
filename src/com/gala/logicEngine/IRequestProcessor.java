package com.gala.logicEngine;

import com.gala.ui.HubwayRequestParameters;

/**
 * Interface for taking a set of request parameters, processing the data, and returning the result
 * @author Heather
 *
 */
public interface IRequestProcessor {
	
	HubwayResults processRequest(HubwayRequestParameters parameters_);
}
