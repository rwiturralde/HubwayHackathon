package com.gala.dataLoader;

import java.util.Map;

/**
 * @author Nathan
 *
 * Interface intended to specify the function necessary to 
 * read data from a data source and format it to a map 
 * for data store insertion.
 *
 * @param <K> Type of the key in the map entry
 * @param <V> Type of the value in the map entry
 */
public interface IDataReader<K,V> {
	
	/**
	 * Reads from a data source and returns a map of an entry intended for a data store. 
	 * 
	 * @return Map of identifier to data entry value.
	 */
	public Map<K,V> getNextDataEntry();
}
