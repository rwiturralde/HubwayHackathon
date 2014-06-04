package com.gala.dataLoader;

import java.util.Map;

import org.apache.log4j.Logger;

public class MongoDbPostProcessorWeatherPrecipitation implements
		IDataPostProcessor<String, Object> {

	private static final Logger 	_logger = Logger.getLogger(MongoDbPostProcessorWeatherPrecipitation.class);

	protected String 		precipitationInputName;
	protected String 		precipitationOutputName;
	protected Double		precipCutoff;
	protected String		traceFlag;
	protected Double		traceAmount;
	

	public MongoDbPostProcessorWeatherPrecipitation(
			String precipitationInputName, String precipitationOutputName,
			Double precipCutoff, String traceFlag, Double traceAmount) {
		super();
		this.precipitationInputName = precipitationInputName;
		this.precipitationOutputName = precipitationOutputName;
		this.precipCutoff = precipCutoff;
		this.traceFlag = traceFlag;
		this.traceAmount = traceAmount;
	}
	
	public void postProcessDataEntry(Map<String, Object> map) {
		setPrecipFlag(map);
	}

	public void setPrecipFlag(Map<String, Object> map){
		Object precipObj;
		Double precipAmount;
		if ((precipObj =map.get(precipitationInputName)) != null){
			if (traceFlag.equals(precipObj.toString().replaceAll("\\s+",""))){
				precipAmount = traceAmount;
			} else if ("".equals(precipObj.toString().replaceAll("\\s+",""))){
				precipAmount = 0.0;
			} else {
				try {
					precipAmount = Double.parseDouble(precipObj.toString());
				} catch (Exception e){
					_logger.warn(String.format("Unable to parse precipitation %s to Double. No precipitation value will be set for this entry", precipObj.toString()));
					return;
				}
			}
			
			if (precipAmount >= precipCutoff){
				map.put(precipitationOutputName, true);
			} else {
				map.put(precipitationOutputName, false);
			}
			
		} else {
			_logger.warn(String.format("No value found in entry for %s", precipitationInputName));
		}
	}

}
