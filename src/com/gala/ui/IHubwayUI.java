package com.gala.ui;

/**
 * Interface for getting input from the user and displaying results to the user
 * 
 * @author Roberto
 *
 */
public interface IHubwayUI {
	
	void launch();
	
	HubwayRequestParameters getUserParameters();
	
	void displayResults(Object results_);
}
