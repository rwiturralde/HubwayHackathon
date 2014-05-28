package com.gala.dataLoader;

import java.util.Map;

public interface IDataPostProcessor<K,V> {
	
	public Map<K,V> postProcessDataEntry(Map<K,V> map);
}
