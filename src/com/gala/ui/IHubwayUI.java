package com.gala.ui;

import com.gala.logicEngine.HubwayResults;

/**
 * Interface for getting input from the user and displaying results to the user
 * 
 * @author Roberto
 *
 */
public interface IHubwayUI {
	
	void launch();
	
	void close();
	
	HubwayRequestParameters getUserParameters();
	
	void displayResults(HubwayResults results_);
}
