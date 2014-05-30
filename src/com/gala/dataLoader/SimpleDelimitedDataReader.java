package com.gala.dataLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @author Nathan
 * 
 * Simple implementation of IDataReader to read data from a 
 * Delimited file one line at a time and format it into a map of 
 * <String, Object> for data store insertion.
 *
 */
public class SimpleDelimitedDataReader implements IDataReader<String, Object> {

	private static final Logger 		_logger = Logger.getLogger(SimpleDelimitedDataReader.class);
	protected String 			filePath;
	protected BufferedReader 	delimitedReader;
	protected String[] 			delimitedHeaders; 
	String 						delimiter;
	boolean 					headersInFile;
	
	SimpleDelimitedDataReader(String filePath, boolean headersInFile, String[] delimitedHeaders, String delimiter){
		this.filePath = filePath;
		this.headersInFile = headersInFile;
		this.delimitedHeaders = delimitedHeaders;
		this.delimiter = delimiter;
	}
	
	/**
	 * Returns a Map containing the next DataEntry from a Delimited file. This is 
	 * simply a map of header to row value. If there are no more entries in 
	 * the file it closes the stream and returns null.
	 * 
	 * @return  The Map containing the next row in the Delimited. Or null if there
	 * 			are no more rows. 
	 * 
	 */
	public Map<String, Object> getNextDataEntry() {
			// Ensure csvReader is open and everything is configured correctly
			if (delimitedReader == null){
				delimitedReader = getBufferedReader();
				if (delimitedReader == null){
					return null;
				}
				if (headersInFile == true){
					String[] headers;
					if ((headers = getNextLine()) == null){
						_logger.info(String.format("File at %s is empty. Returning null.", filePath));
						return null;
					}
					if (delimitedHeaders == null){
						delimitedHeaders = headers;
					}					
				} else if (delimitedHeaders == null){
					_logger.error(String.format("%s is configured to not read headers from Delimited file and no csvHeaders are specified. Cannot continue reading file.", this.getClass()));
					return null;
				}
			}
			
			// Get next entry
			String[] dataEntryValues;
			if ((dataEntryValues = getNextLine()) == null){
				return null;
			}
			
			// Check that it lines up with the headers
			if (delimitedHeaders.length != dataEntryValues.length){
				_logger.error(String.format("Delimited Headers length do not match the delimited entry length. %s will assume that they are in the correct order and populate the map until the shorter array is exhausted.", this.getClass()));
			}
			
			// Initialize return map 
			Map<String, Object> returnMap = new HashMap<String, Object>(Math.min(dataEntryValues.length, delimitedHeaders.length));
			
			// Populate return map
			for (int i = 0; i < Math.min(dataEntryValues.length, delimitedHeaders.length); i++){
				returnMap.put(delimitedHeaders[i], dataEntryValues[i]);
			}
			
			return returnMap;
	}
	
	protected BufferedReader getBufferedReader(){
		try {
			return new BufferedReader(new FileReader(filePath));
		
		} catch (FileNotFoundException e) {
			_logger.error(String.format("Error when trying to open file %s. %s ", filePath, e));
			e.printStackTrace();
			return null;
		} 
	}
	
	// Assumes delimitedReader is initialized and open
	protected String[] getNextLine() {
		String dataEntryLine = "";
		try {
			if ((dataEntryLine = delimitedReader.readLine()) == null){
				if (delimitedReader != null) {
					try {
						delimitedReader.close();
					} catch (IOException e) {
						_logger.error(String.format("Error when trying to close file %s. %s ", filePath, e));
						e.printStackTrace();
					}
				}
				return null;
			}
			
			// strip all double quotes and split delimited row on comma
			dataEntryLine = dataEntryLine.replace("\"","");
			String[] dataEntryValues = dataEntryLine.split(delimiter);
			
			return dataEntryValues;
			
		} catch (IOException e) {
			_logger.error(String.format("Error when trying to read from file %s. %s ", filePath, e));
			e.printStackTrace();
		}
		return null;
	}	
	
	// Ensure stream is closed
	public void cleanup(){
		if (delimitedReader != null) {
			try {
				delimitedReader.close();
			} catch (IOException e) {
				_logger.error(String.format("Error when trying to close file %s. %s ", filePath, e));
				e.printStackTrace();
			}
		}
	}
}
