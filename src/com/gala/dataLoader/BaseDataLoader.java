package com.gala.dataLoader;

public class BaseDataLoader implements IDataLoader{

	protected IDataWriter _writer;
	
	public BaseDataLoader(final IDataWriter writer_){
		_writer = writer_;
	}
	
	public boolean loadData() {
		return true;
	}
}
