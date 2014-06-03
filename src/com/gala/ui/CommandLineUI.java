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

import dme.forecastiolib.FIODaily;
import dme.forecastiolib.FIODataPoint;
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
		String queryTypeSelectionString = "";
		int queryTypeSelectionInt = -1;
		
		RequestType returnRequestType = null;
		
		while (true) {
			System.out.println("\n\nPlease choose the query type you would like to continue with or enter \'q\' to quit.  Valid Queries are:");
			
			int i = 0;
			for(RequestType requestType : RequestType.values()) {
				System.out.println(i + " : " + requestType.getPrintSummary());
				i++;
			}
			
			System.out.print("Enter selection: ");
			queryTypeSelectionString = _scanner.nextLine();
			
			// Quit if user requested
			if (queryTypeSelectionString.toLowerCase().equals("q"))
				return null;
			
			try {
				queryTypeSelectionInt = Integer.parseInt(queryTypeSelectionString);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number.");
				continue;
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

	protected Calendar getForecastDateFromUser() {
		
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
			daySelectionString = _scanner.nextLine();
			
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
	
	protected Station getHubwayStationFromUser() {
		
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
			stationSelectionString = _scanner.nextLine();
			
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
	
	protected TimeOfDay getTimeOfDayFromUser() {
		
		String timeOfDayOrdinalString = "";
		int timeOfDayOrdinal = -1;
		
		while (true) {
			System.out.println("\n\nPlease choose the time of day you plan to leave or enter \'q\' to quit.  Valid times are:");
			
			for(TimeOfDay tod : TimeOfDay.values()) {
				System.out.println(tod.ordinal() + " : " + tod);
			}
			
			System.out.print("Enter selection: ");
			timeOfDayOrdinalString = _scanner.nextLine();
			
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
		
		return stations;
	}
	
}
