package com.gala.ui;

import dme.forecastiolib.FIOCurrently;
import dme.forecastiolib.ForecastIO;

/**
 * Command-line based Hubway UI.  Allows the user to choose various parameters of their starting
 * location.  We then display the results of the logic engine based on that input. 
 * 
 * @author Roberto
 *
 */
public class CommandLineUI implements IHubwayUI {

	protected ForecastIO _forecastIO;
	
	protected CommandLineUI() {
		
	}
	
	public CommandLineUI(ForecastIO forecastIO_) {
		_forecastIO = forecastIO_;
	}
	
	public void launch() {
		printStartupMessage();
	}
	
	/**
	 * 
	 */
	public HubwayRequestParameters getUserParameters() {
		HubwayRequestParameters userParams = null;
		
		_forecastIO.setUnits(ForecastIO.UNITS_US);
		_forecastIO.getForecast("42.3963507", "-71.1226734");
	    System.out.println("Latitude: " + _forecastIO.getLatitude());
	    System.out.println("Longitude: " + _forecastIO.getLongitude());
	    System.out.println("Timezone: " + _forecastIO.getTimezone());
	    
	    FIOCurrently currently = new FIOCurrently(_forecastIO);
	    //Print currently data
	    System.out.println("\nCurrently\n");
	    String [] f  = currently.get().getFieldsArray();
	    for(int i = 0; i<f.length;i++)
	        System.out.println(f[i]+": "+currently.get().getByKey(f[i]));


		
		return userParams;
	}

	public void displayResults(Object results_) {
		// TODO Auto-generated method stub

	}

	protected void printStartupMessage() {
		System.out.println("*************************************************");
		System.out.println("*                                               *");
		System.out.println("*             Hubway Trip Predictor             *");
		System.out.println("*                                               *");
		System.out.println("*************************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	
}
