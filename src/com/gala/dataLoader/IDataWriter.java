package com.gala.dataLoader;

import java.util.HashMap;

public interface IDataWriter<K,V> {
	
	boolean isInitialized();
	boolean init();
	boolean writeEntry(HashMap<K,V> dataEntry_, String collectionName_);
}
