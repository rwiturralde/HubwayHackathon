package com.gala.dataLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.core.Main;

/**
 * Simple implementation of IDataReader to 
 */
public class SimpleCsvDataReader implements IDataReader<String, Object> {

	static final Logger _logger = Logger.getLogger(SimpleCsvDataReader.class);
	private String 			filePath;
	private BufferedReader 	csvReader;
	private String[] 		csvHeaders; 
	String 					cvsSplitBy = ",";
	boolean 				headersInFile;
	
	SimpleCsvDataReader(String filePath, boolean headersInFile, String[] csvHeaders){
		this.filePath = filePath;
		this.headersInFile = headersInFile;
		this.csvHeaders = csvHeaders;
	}
	
	/**
	 * Returns a Map containing the next DataEntry from a CSV file. This is 
	 * simply a map of header to row value. If there are no more entries in 
	 * the file it closes the stream and returns null.
	 * 
	 * @return  The Map containing the next row in the CSV. Or null if there
	 * 			are no more rows. 
	 * 
	 */
	public Map<String, Object> getNextDataEntry() {
		try {
	 
			// Ensure csvReader is open and everything is configured correctly
			if (csvReader == null){
				csvReader = new BufferedReader(new FileReader(filePath));
				if (headersInFile == true){
					String[] headers;
					if ((headers = getNextLine()) == null){
						_logger.info(String.format("File at %s is empty. Returning null.", filePath));
						return null;
					}
					if (csvHeaders == null){
						csvHeaders = headers;
					}					
				} else if (csvHeaders == null){
					_logger.error(String.format("%s is configured to not read headers from CSV and no csvHeaders are specified. Cannot continue reading file.", this.getClass()));
					return null;
				}
			}
			
			// Get next entry
			String[] dataEntryValues;
			if ((dataEntryValues = getNextLine()) == null){
				return null;
			}
			
			// Check that it lines up with the headers
			if (csvHeaders.length != dataEntryValues.length){
				_logger.error(String.format("CSV Headers length do not match the csv entry length. %s will assume that they are in the correct order and populate the map until the shorter array is exhausted.", this.getClass()));
			}
			
			// Initialize return map 
			Map<String, Object> returnMap = new HashMap<String, Object>(Math.min(dataEntryValues.length, csvHeaders.length));
			
			// Populate return map
			for (int i = 0; i < Math.min(dataEntryValues.length, csvHeaders.length); i++){
				returnMap.put(csvHeaders[i], dataEntryValues[i]);
			}
			
			return returnMap;
			
		} catch (FileNotFoundException e) {
			_logger.error(String.format("Error when trying to open file %s. %s ", filePath, e));
			e.printStackTrace();
		} 
		return null;
	}
	
	// Assumes csvReader is initialized and open
	private String[] getNextLine() {
		String dataEntryLine = "";
		try {
			if ((dataEntryLine = csvReader.readLine()) == null){
				if (csvReader != null) {
					try {
						csvReader.close();
					} catch (IOException e) {
						_logger.error(String.format("Error when trying to close file %s. %s ", filePath, e));
						e.printStackTrace();
					}
				}
				return null;
			}
			
			// split csv row on comma
			String[] dataEntryValues = dataEntryLine.split(cvsSplitBy);
			
			return dataEntryValues;
			
		} catch (IOException e) {
			_logger.error(String.format("Error when trying to read from file %s. %s ", filePath, e));
			e.printStackTrace();
		}
		return null;
	}	
	
	// Ensure stream is closed
	public void cleanup(){
		if (csvReader != null) {
			try {
				csvReader.close();
			} catch (IOException e) {
				_logger.error(String.format("Error when trying to close file %s. %s ", filePath, e));
				e.printStackTrace();
			}
		}
	}
}
