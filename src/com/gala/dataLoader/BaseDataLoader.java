package com.gala.dataLoader;

import org.apache.log4j.Logger;

public class BaseDataLoader implements IDataLoader{

	static final Logger _logger = Logger.getLogger(BaseDataLoader.class);
	protected IDataWriter _writer;
	
	public BaseDataLoader(final IDataWriter writer_){
		_writer = writer_;
	}
	
	public boolean loadData() {
		
		if (!_writer.isInitialized()) {
			_logger.warn("Writer was not initialized. Attempting to initialize again.");
			if (!_writer.init()) {
				_logger.error("Attempt to initialize writer has failed. Data cannot be loaded.");
				return false;
			}
		}
		
		return true;
	}
}
