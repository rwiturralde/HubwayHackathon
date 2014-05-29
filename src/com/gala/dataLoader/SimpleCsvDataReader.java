package com.gala.dataLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleCsvDataReader implements IDataReader<String, Object> {

	private String 			filePath;
	private BufferedReader 	csvReader;
	private String[] 		csvHeaders; 
	String 					cvsSplitBy = ",";
	boolean 				headersInFile;
	
	SimpleCsvDataReader(String filePath, String[] csvHeaders, boolean headersInFile){
		this.filePath = filePath;
		this.csvHeaders = csvHeaders;
		this.headersInFile = headersInFile;
	}
	
	public Map<String, Object> getNextDataEntry() {
		
			 
		try {
	 
			if (csvReader == null){
				csvReader = new BufferedReader(new FileReader(filePath));
				if (headersInFile == true){
					String [] headers = getNextLine();
					if (csvHeaders == null){
						csvHeaders = headers;
					}					
				} else if (csvHeaders == null){
					// Error and return 
				}
			}
			
			String[] dataEntryValues;
			if ((dataEntryValues = getNextLine()) == null){
				return null;
			}
			
			
			if (csvHeaders.length != dataEntryValues.length){
				//Log Error
			}
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			
			for (int i = 0; i < Math.min(dataEntryValues.length, csvHeaders.length); i++){
				returnMap.put(csvHeaders[i], dataEntryValues[i]);
			}
			
			return returnMap;
			
		} catch (FileNotFoundException e) {
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
						e.printStackTrace();
					}
				}
				return null;
			}
			
			String[] dataEntryValues = dataEntryLine.split(cvsSplitBy);
			
			return dataEntryValues;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public void cleanup(){
		if (csvReader != null) {
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
