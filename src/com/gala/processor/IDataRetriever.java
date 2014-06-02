package com.gala.processor;

import java.util.Collection;

/**
 * Interface for fetching data from the db and formatting it to the appropriate object
 * @author Heather
 *
 * @param <T>
 */
public interface IDataRetriever<T> {

	Collection<T> retrieveData(Object[] query_);
}
