package com.gala.dataLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MongoDbPostProcessorCoordinator<K, V> implements IPostProcessorCoordinator<K, V> {

	private static final Logger 				_logger = Logger.getLogger(MongoDbPostProcessorCoordinator.class);
	protected List<IDataPostProcessor<K,V>> 	postProcessorList; 
	protected List<String>						destinationNames;
	
	public MongoDbPostProcessorCoordinator(
			List<IDataPostProcessor<K, V>> postProcessorList, List<String> destinationNames) {
		this.postProcessorList = postProcessorList;
		this.destinationNames = destinationNames;
	}
	
	
	public Map<String, Map<K, V>> postProcessDataEntry(Map<K, V> map) {
		for (IDataPostProcessor<K, V> postProc : postProcessorList){
			postProc.postProcessDataEntry(map);
		}		
		return wrapMap(map);
	}
	
	
	protected Map<String, Map<K, V>> wrapMap(Map<K, V> map){
		Map<String, Map<K, V>> returnMap = new HashMap<String, Map<K,V>>();
		for (String destinationName : destinationNames){
			returnMap.put(destinationName, new HashMap<K,V>(map));
		}	
		
		return returnMap;
	}
}
