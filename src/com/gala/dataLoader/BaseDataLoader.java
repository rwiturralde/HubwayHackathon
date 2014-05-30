package com.gala.dataLoader;

import java.sql.BatchUpdateException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Loader for all data required for this application.
 * @author Heather
 *
 * @param <K>
 * @param <V>
 */
public class BaseDataLoader<K,V> implements IDataLoader{

	static final Logger _logger = Logger.getLogger(BaseDataLoader.class);
	protected IDataWriter<K,V> _writer;
	protected List<SimpleEntry<IDataReader<K,V>, IDataPostProcessor<K,V>>> _readPostPairs;
	protected List<String> _collectionsToLoad;
	protected int _batchWriteSize;
	
	public BaseDataLoader(final List<SimpleEntry<IDataReader<K,V>, IDataPostProcessor<K,V>>> pairs_, 
						final IDataWriter<K,V> writer_,
						final List<String> collToLoad_,
						final int batchSize_){
		_readPostPairs = pairs_;
		_writer = writer_;
		_collectionsToLoad = collToLoad_;
		_batchWriteSize = batchSize_;
	}
	
	/* (non-Javadoc)
	 * @see com.gala.dataLoader.IDataLoader#loadData()
	 */
	public boolean loadData() {
		if (_collectionsToLoad.size() != 0 && !_writer.isInitialized()) {
			_logger.warn("Writer was not initialized. Attempting to initialize again.");
			if (!_writer.init()) {
				_logger.error("Attempt to initialize writer has failed. Data cannot be loaded.");
				return false;
			}
		}
		if (_collectionsToLoad.size() == 0) {
			return true;
		}
		
		Map<K,V> nextReaderEntry;
		Map<String,Map<K,V>> nextCollectionEntry;
		Map<String,List<Map<K,V>>> dataToWrite = new HashMap<String,List<Map<K,V>>>();
		boolean timeToWrite = false;
		List<String> collectionsWritten = new ArrayList<String>();
		
		_logger.info(String.format("BEGINNING DATA LOAD : %s", new java.util.Date()));
		for (SimpleEntry<IDataReader<K,V>, IDataPostProcessor<K,V>> pair : _readPostPairs){
			int counter = 0;
			dataToWrite.clear();
			while ((nextReaderEntry = pair.getKey().getNextDataEntry()) != null) {
				nextCollectionEntry = pair.getValue().postProcessDataEntry(nextReaderEntry);
				
				// Merge data
				for (String coll : nextCollectionEntry.keySet()){
					if (_collectionsToLoad.contains(coll)){
						if (!dataToWrite.containsKey(coll)){
							dataToWrite.put(coll, new ArrayList<Map<K,V>>());
						}
						
						List<Map<K,V>> collList = dataToWrite.get(coll);
						collList.add(nextCollectionEntry.get(coll));
						dataToWrite.put(coll, collList);
						
						if (collList.size() >= _batchWriteSize){
							timeToWrite = true;
						}					
						counter++;
					}
				}
				
				// Write data
				if (timeToWrite){
					boolean success = false;
					for (String coll : dataToWrite.keySet()){					
						success = _writer.batchWrite(dataToWrite.get(coll), coll);
						if (success){
							collectionsWritten.add(coll);
						}
					}
					
					for(int i=0;i<collectionsWritten.size();i++){
						_logger.info(String.format("Loaded %s entries into %s. Clearing internal list.", counter, collectionsWritten.get(i)));
						dataToWrite.remove(collectionsWritten.get(i));
					}
					
					collectionsWritten.clear();					
					timeToWrite = false;
				}
								
//				counter++;
//				if (counter%100 == 0){
//					_logger.info(String.format("Loaded %s entries.", counter));
//				}
			}
			
			if (!dataToWrite.isEmpty()){
				for (String coll : dataToWrite.keySet()){					
					boolean success = _writer.batchWrite(dataToWrite.get(coll), coll);
					if (success){
						_logger.info(String.format("Loaded %s entries into %s.", counter, coll));
					} else {
						_logger.warn(String.format("Failed to load %s entries into %s.", dataToWrite.get(coll).size(), coll));
					}
				}
			}
		}
		_logger.info(String.format("DATA LOAD COMPLETE: %s", new java.util.Date()));
		
		return true;
	}
}
