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

	public void launch() {
		printStartupMessage();
	}
	
	/**
	 * 
	 */
	public HubwayRequestParameters getUserParameters() {
		HubwayRequestParameters userParams = null;
		
		ForecastIO fio = new ForecastIO("2e4135288e84ee81b4bb8418d67d6710");
	    fio.setUnits(ForecastIO.UNITS_US);
	    fio.getForecast("42.3963507", "-71.1226734");
	    System.out.println("Latitude: "+fio.getLatitude());
	    System.out.println("Longitude: "+fio.getLongitude());
	    System.out.println("Timezone: "+fio.getTimezone());
	    
	    FIOCurrently currently = new FIOCurrently(fio);
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
