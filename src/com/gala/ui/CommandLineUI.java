package com.gala.ui;

/**
 * Command-line based Hubway UI.  Allows the user to choose various parameters of their starting
 * location.  We then display the results of the logic engine based on that input. 
 * 
 * @author Roberto
 *
 */
public class CommandLineUI implements IHubwayUI {

	public void launch() {
		printStartupMessage();
	}
	
	/**
	 * 
	 */
	public Object getUserParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayResults(Object results_) {
		// TODO Auto-generated method stub

	}

	protected void printStartupMessage() {
		
	}
	
}
