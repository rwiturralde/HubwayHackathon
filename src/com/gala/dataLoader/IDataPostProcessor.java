package com.gala.dataLoader;

import java.util.Map;

/**
 * @author Nathan
 * 
 * Interface intended to specify the function necessary to 
 * clean and decorate input data for data store insertion.
 *
 * @param <K> Type of the key in the map entry
 * @param <V> Type of the value in the map entry
 */
public interface IDataPostProcessor<K,V> {
		
	/**
	 * Takes in a map intended to be loaded to a data store. 
	 * It then decorates the data as implemented.
	 * 
	 * @param Input map to post process
	 * @return Map of Destination collection name to map entry
	 */
	public void postProcessDataEntry(Map<K,V> map);
}
