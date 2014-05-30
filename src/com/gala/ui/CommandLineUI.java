package com.gala.ui;

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
		
		Day chosenDay = getDayFromUser(scanner);
		
		if (chosenDay == null)
			return null;
		
		System.out.println("User chose " + chosenDay);
		
		scanner.close();
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
			if (dayOrdinalString.toLowerCase() == "q")
				return null;
			
			try {
				dayOrdinal = Integer.parseInt(dayOrdinalString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
			} 
			
			if (dayOrdinal < 0 || dayOrdinal > Day.values().length -1) {
				System.out.println("Invalid selection.  Please choose the number corresponding to a valid day.");
				continue;
			} else {
				// Valid selection.
				break;
			}
		}
		
		
		return Day.values()[dayOrdinal];
	}
	
}
