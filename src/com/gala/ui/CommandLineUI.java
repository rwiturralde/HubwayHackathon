package com.gala.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.TimeOfDay;

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
	protected Set<Station> _stations;
	
	protected CommandLineUI() {
		
	}
	
	public CommandLineUI(ForecastIO forecastIO_) {
		_forecastIO = forecastIO_;
		_stations = loadStations();
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
		
		Station chosenStation = getHubwayStationFromUser(scanner);
		
		if (chosenStation == null)
			return null;
		
		System.out.println("User chose " + chosenStation);
		
		TimeOfDay chosenTimeOfDay = getTimeOfDayFromUser(scanner);
		
		if (chosenTimeOfDay == null)
			return null;
		
		System.out.println("User chose " + chosenTimeOfDay);
		
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

	protected Calendar getForecastDateFromUser(Scanner scanner_) {
		
		String daySelectionString = "";
		int daySelectionInt = -1;
		
		while (true) {
			System.out.println("\n\nPlease choose when you play to leave for your trip or enter \'q\' to quit.  Valid days are:");
			
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
	
	protected Station getHubwayStationFromUser(Scanner scanner_) {
		
		String stationSelectionString = "";
		int stationSelectionInt = -1;
		
		Station[] stationArray = new Station[_stations.size()]; 
		stationArray = _stations.toArray(stationArray);
		
		while (true) {
			System.out.println("\n\nPlease choose the station from which you plan to depart or enter \'q\' to quit.  Valid stations are:");
			
			for(int i = 0; i < stationArray.length; i++) {
				System.out.println(i + " : " + stationArray[i].getName());
			}
			
			System.out.print("Enter selection: ");
			stationSelectionString = scanner_.nextLine();
			
			// Quit if user requested
			if (stationSelectionString.toLowerCase().equals("q"))
				return null;
			
			try {
				stationSelectionInt = Integer.parseInt(stationSelectionString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
			} 
			
			if (stationSelectionInt < 0 || stationSelectionInt >= stationArray.length) {
				System.out.println("Invalid selection.  Please choose the number corresponding to a valid station.");
				continue;
			} else {
				// Valid selection.
				break;
			}
		}
		
		return stationArray[stationSelectionInt];
		
	}
	
	protected TimeOfDay getTimeOfDayFromUser(Scanner scanner_) {
		
		String timeOfDayOrdinalString = "";
		int timeOfDayOrdinal = -1;
		
		while (true) {
			System.out.println("\n\nPlease choose the time of day you plan to leave or enter \'q\' to quit.  Valid times are:");
			
			for(TimeOfDay tod : TimeOfDay.values()) {
				System.out.println(tod.ordinal() + " : " + tod);
			}
			
			System.out.print("Enter selection: ");
			timeOfDayOrdinalString = scanner_.nextLine();
			
			// Quit if user requested
			if (timeOfDayOrdinalString.toLowerCase().equals("q"))
				return null;
			
			try {
				timeOfDayOrdinal = Integer.parseInt(timeOfDayOrdinalString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
			} 
			
			if (timeOfDayOrdinal < 0 || timeOfDayOrdinal >= TimeOfDay.values().length) {
				System.out.println("Invalid selection.  Please choose the number corresponding to a valid time of day.");
				continue;
			} else {
				// Valid selection.
				break;
			}
		}
		
		return TimeOfDay.values()[timeOfDayOrdinal];		
	}
	
	protected Set<Station> loadStations() {
		Set<Station> stations = new HashSet<Station>();
		
		Station station1 = new Station();
		station1.setId(20);
		station1.setName("Aquarium Station - 200 Atlantic Ave.");
		station1.setLatitude(42.35977);
		station1.setLongitude(-71.051601);
		station1.setMunicipality("Boston");
		
		stations.add(station1);
		
		Station station2 = new Station();
		station2.setId(44);
		station2.setName("Faneuil Hall - Union St. at North St.");
		station2.setLatitude(42.360583);
		station2.setLongitude(-71.056868);
		station2.setMunicipality("Boston");
		
		stations.add(station2);
		
		return stations;
	}
	
}
