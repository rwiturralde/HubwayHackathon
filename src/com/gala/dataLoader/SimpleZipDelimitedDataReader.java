package com.gala.dataLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

public class SimpleZipDelimitedDataReader extends SimpleDelimitedDataReader {

	private static final Logger 	_logger = Logger.getLogger(SimpleZipDelimitedDataReader.class);
	
	SimpleZipDelimitedDataReader(String filePath, boolean headersInFile,
			String[] delimitedHeaders, String delimiter) {
		super(filePath, headersInFile, delimitedHeaders, delimiter);
	}
	
	@SuppressWarnings("resource")
	protected BufferedReader getBufferedReader(){
		try {
			ZipFile zf = new ZipFile(filePath);
			ZipEntry ze = (ZipEntry) zf.entries().nextElement();
			return new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));			
		
		} catch (FileNotFoundException e) {
			_logger.error(String.format("Error when trying to open file %s. %s ", filePath, e));
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			_logger.error(String.format("Error when trying to read file %s. %s ", filePath, e));
			e.printStackTrace();
			return null;
		} 
	}

}
