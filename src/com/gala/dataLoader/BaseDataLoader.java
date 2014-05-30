package com.gala.dataLoader;

import java.util.AbstractMap.SimpleEntry;
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
	
	public BaseDataLoader(final List<SimpleEntry<IDataReader<K,V>, IDataPostProcessor<K,V>>> pairs_, 
						final IDataWriter<K,V> writer_,
						final List<String> collToLoad_){
		_readPostPairs = pairs_;
		_writer = writer_;
		_collectionsToLoad = collToLoad_;
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

		for (SimpleEntry<IDataReader<K,V>, IDataPostProcessor<K,V>> pair : _readPostPairs){
			while ((nextReaderEntry = pair.getKey().getNextDataEntry()) != null) {
				nextCollectionEntry = pair.getValue().postProcessDataEntry(nextReaderEntry);
				
				for (String coll : nextCollectionEntry.keySet()){
					if (_collectionsToLoad.contains(coll)){
						_writer.writeEntry(nextCollectionEntry.get(coll), coll);
					}
				}
			}
		}
		
		return true;
	}
}
