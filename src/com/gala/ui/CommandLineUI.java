package com.gala.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.gala.core.Day;

import dme.forecastiolib.ForecastIO;

/**
 * Command-line based Hubway UI.  Allows the user to choose various parameters of their starting
 * location.  We then display the results of the logic engine based on that input. 
 * 
 * @author Roberto
 *
 */
public class CommandLineUI implements IHubwayUI {

	// Number of days into the future (incl today) that we'll allow for forecasting
	protected final int FORECAST_RANGE = 3;
	protected final SimpleDateFormat _dateFormat = new SimpleDateFormat("E M d, Y");
	
	protected ForecastIO _forecastIO;
	
	protected CommandLineUI() {
		
	}
	
	public CommandLineUI(ForecastIO forecastIO_) {
		_forecastIO = forecastIO_;
	}
	
	public void launch() {
		printStartupMessage();
	}
	

	public HubwayRequestParameters getUserParameters() {
		HubwayRequestParameters userParams = null;
		Scanner scanner = new Scanner(System.in);
		
		//Day chosenDay = getDayFromUser(scanner);
		Calendar chosenCal = getForecastDateFromUser(scanner);
		
		if (chosenCal == null)
			return null;
		
		System.out.println("User chose " + chosenCal.getTime());
		
		scanner.close();
		return userParams;
	}

	public void displayResults(Object results_) {
		// TODO Auto-generated method stub

	}

	protected void printStartupMessage() {
		System.out.println("*************************************************");
		System.out.println("*                                               *");
		System.out.println("*             Hubway Forecaster                 *");
		System.out.println("*                                               *");
		System.out.println("*************************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	
	protected Day getDayFromUser(Scanner scanner_) {
		
		String dayOrdinalString = "";
		int dayOrdinal = -1;
		
		while (true) {
			System.out.println("Please choose a day of the week or enter \'q\' to quit.  Valid days are:");
			
			for(Day day : Day.values()) {
				System.out.println(day.ordinal() + " : " + day);
			}
			
			System.out.print("Enter selection: ");
			dayOrdinalString = scanner_.nextLine();
			
			// Quit if user requested
			if (dayOrdinalString.toLowerCase().equals("q"))
				return null;
			
			try {
				dayOrdinal = Integer.parseInt(dayOrdinalString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
			} 
			
			if (dayOrdinal < 0 || dayOrdinal >= Day.values().length) {
				System.out.println("Invalid selection.  Please choose the number corresponding to a valid day.");
				continue;
			} else {
				// Valid selection.
				break;
			}
		}
		
		return Day.values()[dayOrdinal];
	}
	
	protected Calendar getForecastDateFromUser(Scanner scanner_) {
		
		String daySelectionString = "";
		int daySelectionInt = -1;
		
		while (true) {
			System.out.println("Please choose when you play to leave for your trip or enter \'q\' to quit.  Valid days are:");
			
			Calendar tempCal = Calendar.getInstance();
			for(int i = 0; i < FORECAST_RANGE; i++) {
				System.out.println(i + " : " + _dateFormat.format(tempCal.getTime()));
				tempCal.add(Calendar.DATE, 1);
			}
			
			System.out.print("Enter selection: ");
			daySelectionString = scanner_.nextLine();
			
			// Quit if user requested
			if (daySelectionString.toLowerCase().equals("q"))
				return null;
			
			try {
				daySelectionInt = Integer.parseInt(daySelectionString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
			} 
			
			if (daySelectionInt < 0 || daySelectionInt >= FORECAST_RANGE) {
				System.out.println("Invalid selection.  Please choose the number corresponding to a valid day.");
				continue;
			} else {
				// Valid selection.
				break;
			}
		}
		
		Calendar returnCal = Calendar.getInstance();
		returnCal.add(Calendar.DATE, daySelectionInt);
		return returnCal;
	}
	
}
