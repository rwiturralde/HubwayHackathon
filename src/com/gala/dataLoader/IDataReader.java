package com.gala.dataLoader;

import java.util.Map;

public interface IDataReader<K,V> {
	
	public Map<K,V> getNextDataEntry();
}
