package com.gala.ui;

import com.gala.logicEngine.IHubwayResults;

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
	
	void displayResults(IHubwayResults results_);
}
