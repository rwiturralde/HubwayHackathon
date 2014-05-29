package com.gala.dataLoader;

import java.util.Map;

public interface IDataWriter<K,V> {
	
	boolean isInitialized();
	boolean init();
	boolean writeEntry(Map<K,V> dataEntry_, String collectionName_);
}
