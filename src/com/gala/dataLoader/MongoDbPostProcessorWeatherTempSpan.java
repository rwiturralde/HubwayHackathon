package com.gala.dataLoader;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gala.core.Temperature;

public class MongoDbPostProcessorWeatherTempSpan implements IDataPostProcessor<String, Object>{

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorWeatherDateSpan.class);
	protected String 				tempId;
	protected String 				tempMongoId;
	
	
	public MongoDbPostProcessorWeatherTempSpan(String tempId, String tempMongoId) {
		this.tempId = tempId;
		this.tempMongoId = tempMongoId;
	}

	public void postProcessDataEntry(Map<String, Object> map) {
		addSpannedTemperature(map);		
	}
	
	protected void addSpannedTemperature(Map<String, Object> map){

		Object tempObj;
		int tempInt;
		
		if ((tempObj = map.get(tempId)) != null){
			try{
				tempInt = Integer.parseInt(tempObj.toString());
			} catch (Exception e){
				_logger.error(String.format("Failed to parse \"%s\" as an int. No temperature will be added.", tempId));
				return;
			}
		} else {
			_logger.debug(String.format("No value found in entry for %s", tempId));
			return;
		}
		
		Temperature tempEnum = Temperature.getTemperature(tempInt);
		
		if (tempEnum != null){
			map.put(tempMongoId, tempEnum.name());
		} else{
			_logger.warn(String.format("Unable to retrieve temp enum from temp %s. No temperature will be added.", tempInt));
		}
	}
}
