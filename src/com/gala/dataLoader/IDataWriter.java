package com.gala.dataLoader;

import java.util.List;
import java.util.Map;

/**
 * @author Heather
 *
 * Interface for taking data passed in from the 
 * caller and writing it to a specified location.
 *
 * @param <K> Type of the key in the map entry
 * @param <V> Type of the value in the map entry
 */
public interface IDataWriter<K,V> {
	
	/**
	 * 
	 * @return true if the writer has been initialized
	 */
	boolean isInitialized();
	
	/**
	 * Initializes the writer
	 * @return true if initialization was successful
	 */
	boolean init();
	
	/**
	 * Writes the given entry to the collection provided.
	 * @param dataEntry_
	 * @param collectionName_
	 * @return true if write was successful
	 */
	boolean writeEntry(Map<K,V> dataEntry_, String collectionName_);
	
	
	/**
	 * Batch write to the collection provided.
	 * @param dataEntries_
	 * @param collName_
	 * @return true if write was successful
	 */
	boolean batchWrite(List<Map<K,V>> dataEntries_, String collName_);
}
