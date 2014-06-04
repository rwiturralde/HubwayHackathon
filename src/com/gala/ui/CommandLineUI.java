package com.gala.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.gala.core.Day;
import com.gala.core.Station;
import com.gala.core.Temperature;
import com.gala.core.TimeOfDay;
import com.gala.logicEngine.HubwayResults;

import dme.forecastiolib.FIODaily;
import dme.forecastiolib.FIOHourly;
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
	protected final int FORECAST_RANGE = 5;
	protected final SimpleDateFormat _dateFormat = new SimpleDateFormat("EEE MMM d, yyyy");
	
	protected ForecastIO _forecastIO;
	protected Set<Station> _stations;
	protected Scanner _scanner;
	
	protected CommandLineUI() {
		
	}
	
	public CommandLineUI(ForecastIO forecastIO_) {
		_forecastIO = forecastIO_;
		_forecastIO.setUnits(ForecastIO.UNITS_US);
		_stations = loadStations();
		_scanner = null;
	}
	
	public void launch() {
		_scanner = new Scanner(System.in);
		printStartupMessage();
	}
	
	public void close() {
		if (_scanner != null)
			_scanner.close();
	}

	public HubwayRequestParameters getUserParameters() {
		HubwayRequestParameters userParams = new HubwayRequestParameters();
		
		// Get request type
		RequestType requestType = getRequestTypeFromUser();
		if (requestType == null)
			return null;
		
		userParams.setRequestType(requestType);
		
		Calendar chosenCal;
		Station chosenStation;
		TimeOfDay chosenTimeOfDay;
		
		switch (userParams._requestType){
		case WEATHER:
			// Get the day the user plans to depart
			chosenCal = getForecastDateFromUser();
			if (chosenCal == null)
				return null;
			
			userParams.setDay(Day.fromCalendar(chosenCal));
			
			// Get the time of day the user plans to leave
			chosenTimeOfDay = getTimeOfDayFromUser();
			if (chosenTimeOfDay == null)
				return null;
			
			userParams.setTimeOfDay(chosenTimeOfDay);
			
			// Get the departing station from the user
			chosenStation = getHubwayStationFromUser();
			if (chosenStation == null)
				return null;
			
			userParams.setStartStation(chosenStation);
			
			// Get the forecast and temperature for the day and time chosen by the user.
			_forecastIO.getForecast(chosenStation.getLatitude().toString(), chosenStation.getLongitude().toString());
			Temperature forecastTemp = getTemperature(chosenCal, chosenTimeOfDay);
			
			if (forecastTemp == null)
				return null;
			
			userParams.setTemperature(forecastTemp);
			
			break;
		case TRIP:
			// Get the day the user plans to depart
			chosenCal = getForecastDateFromUser();
			if (chosenCal == null)
				return null;
			
			userParams.setDay(Day.fromCalendar(chosenCal));
			
			// Get the departing station from the user
			chosenStation = getHubwayStationFromUser();
			if (chosenStation == null)
				return null;
			
			userParams.setStartStation(chosenStation);
			
			// Get the time of day the user plans to leave
			chosenTimeOfDay = getTimeOfDayFromUser();
			if (chosenTimeOfDay == null)
				return null;
			
			userParams.setTimeOfDay(chosenTimeOfDay);
			break;
			
		default: return null;
		}
		

		return userParams;
	}

	private RequestType getRequestTypeFromUser() {
		int queryTypeSelectionInt = -1;
		
		RequestType returnRequestType = null;
		
		while (true) {
			System.out.println("\n\nPlease choose the query type you would like to continue with or enter \'q\' to quit.  Valid Queries are:");
			
			int i = 0;
			for(RequestType requestType : RequestType.values()) {
				System.out.println(i + " : " + requestType.getPrintSummary());
				i++;
			}
			
			queryTypeSelectionInt = getSelectionFromUser();
			
			switch (queryTypeSelectionInt) {
				case -1: return null; // user requested quit
				case -2: continue; // invalid user selection
			} 
			
			try {
				returnRequestType = RequestType.values()[queryTypeSelectionInt];
				break;
			} catch (Exception e){
				System.out.println("Invalid selection. Please choose the number corresponding to a valid station.");
				continue;
			}
			
		}
		
		return returnRequestType;
	}

	public void displayResults(HubwayResults results_) {
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

	protected Calendar getForecastDateFromUser() {
		int daySelectionInt = -1;
		
		while (true) {
			System.out.println("\n\nPlease choose when you play to leave for your trip or enter \'q\' to quit.  Valid days are:");
			
			Calendar tempCal = Calendar.getInstance();
			for(int i = 0; i < FORECAST_RANGE; i++) {
				System.out.println(i + " : " + _dateFormat.format(tempCal.getTime()));
				tempCal.add(Calendar.DATE, 1);
			}
			
			daySelectionInt = getSelectionFromUser();
			
			switch (daySelectionInt) {
				case -1: return null; // user requested quit
				case -2: continue; // invalid user selection
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
	
	protected Station getHubwayStationFromUser() {
		int stationSelectionInt = -1;
		
		Station[] stationArray = new Station[_stations.size()]; 
		stationArray = _stations.toArray(stationArray);
		
		while (true) {
			System.out.println("\n\nPlease choose the station from which you plan to depart or enter \'q\' to quit.  Valid stations are:");
			
			for(int i = 0; i < stationArray.length; i++) {
				System.out.println(i + " : " + stationArray[i].getName());
			}
			
			stationSelectionInt = getSelectionFromUser();
			
			switch (stationSelectionInt) {
				case -1: return null; // user requested quit
				case -2: continue; // invalid user selection
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
	
	protected TimeOfDay getTimeOfDayFromUser() {
		int timeOfDayOrdinal = -1;
		
		while (true) {
			System.out.println("\n\nPlease choose the time of day you plan to leave or enter \'q\' to quit.  Valid times are:");
			
			for(TimeOfDay tod : TimeOfDay.values()) {
				System.out.println(tod.ordinal() + " : " + tod);
			}
			
			timeOfDayOrdinal = getSelectionFromUser();
			
			switch (timeOfDayOrdinal) {
				case -1: return null; // user requested quit
				case -2: continue; // invalid user selection
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

	/**
	 * Get the forecasted temperature for a given day and time range using the Forecast
	 * 
	 * @param cal_
	 * @param timeOfDay_
	 * @return
	 */
	protected Temperature getTemperature(Calendar cal_, TimeOfDay timeOfDay_) {
		
		int dayOffset = cal_.get(Calendar.DAY_OF_WEEK) - Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int hourOffset = 0;
		
		// Hacky.  Assign arbitrary times for each TimeOfDay value so we can get the hourly forecast
		// for that time.  Approximate.
		switch(timeOfDay_) {
			case MORNING:
				hourOffset = 8 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				break;
			case MIDDAY:
				hourOffset = 12 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				break;
			case EVENING:
				hourOffset = 17 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				break;
			default:
				hourOffset = 0;
		}

		int totalHourOffset = (dayOffset * 24) + hourOffset;
		
		if (totalHourOffset < 0) {
			System.out.println("You've chosen a time in the past.  Defaulting to the current forecast");
			totalHourOffset = 0;
		}
		
		//Forecast.io only supports 48 hours of hourly forecast data, though the 
		// shitty Java wrapper for the API claims it has 49 hours of forecast and 
		// throws an IndexOutOfBounds if you ask for 49.
		if (totalHourOffset <= 48) {
			FIOHourly hourly = new FIOHourly(_forecastIO);  
			
			if (hourly.hours() < 0 || hourly.hours() < totalHourOffset || hourly.getHour(totalHourOffset).temperature() == null) {
				System.out.println("Insufficient forecast data for the chosen day...");
				return null;
			} else {
				System.out.println("Hourly - Time: " + hourly.getHour(totalHourOffset).time() + "GMT Temp: " + hourly.getHour(totalHourOffset).temperature());
				return Temperature.getTemperature(hourly.getHour(totalHourOffset).temperature().intValue());
			}
		} else {
			// Insufficient hourly data.  Use the high for the day in question.
			FIODaily daily = new FIODaily(_forecastIO);
			
			if (daily.days() < 0 || daily.days() < dayOffset || daily.getDay(dayOffset).temperatureMax() == null) {
				System.out.println("Insufficient forecast data for the chosen day...");
				return null;
			} else {
				System.out.println("Daily - Time: " + daily.getDay(dayOffset).time() + "GMT Temp: " + daily.getDay(dayOffset).temperatureMax());
				return Temperature.getTemperature(daily.getDay(dayOffset).temperatureMax().intValue());
			}
		}
	}

	
	/**
	 * Get an integer from the user at the command line.  The user can enter Q to quit.
	 * @return The integer entered by the user.  -1 if the user asked to quit.  -2 if
	 * the user entered an invalid selection.
	 */
	protected int getSelectionFromUser() {
		String selectionString = "";
		int selectionInt = -2;
		
		System.out.print("Enter selection: ");
		
		selectionString = _scanner.nextLine().trim();
		
		// Quit if user requested.  -1 for Quit.
		if (selectionString.toLowerCase().equals("q"))
			return -1;
		
		try {
			selectionInt = Integer.parseInt(selectionString);
		} catch (NumberFormatException e) {
			System.out.println("Invalid number.");
			return -2;
		} 
		
		return selectionInt;
	}
	
	/**
	 * Get the set of valid stations for the user to choose from.  These should
	 * be stations for which we have all the necessary data to get forecasts and 
	 * calculate our metrics.
	 * 
	 * @return Set of stations from which the user can choose to depart.
	 */
	protected Set<Station> loadStations() {
		Set<Station> stations = new HashSet<Station>();
		
		Station station1 = new Station();
		station1.setId(20);
		station1.setName("Aquarium Station - 200 Atlantic Ave.");
		station1.setLatitude(42.35977);
		station1.setLongitude(-71.051601);
		station1.setMunicipality("Boston");
		station1.setCapacity(19);
		stations.add(station1);
		
		Station station2 = new Station();
		station2.setId(44);
		station2.setName("Faneuil Hall - Union St. at North St.");
		station2.setLatitude(42.360583);
		station2.setLongitude(-71.056868);
		station2.setMunicipality("Boston");
		station2.setCapacity(19);
		stations.add(station2);
		
		Station station3 = new Station();
		station3.setId(22);
		station3.setName("South Station - 700 Atlantic Ave.");
		station3.setLatitude(42.352175);
		station3.setLongitude(-71.055547);
		station3.setMunicipality("Boston");
		station3.setCapacity(47);
		stations.add(station3);
		
		Station station4 = new Station();
		station4.setId(57);
		station4.setName("Columbus Ave. at Mass. Ave.");
		station4.setLatitude(42.340799);
		station4.setLongitude(-71.081572);
		station4.setMunicipality("Boston");
		station4.setCapacity(11);
		stations.add(station4);
		
		return stations;
	}
	
}
