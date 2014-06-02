package com.gala.logicEngine;

import java.util.List;

/**
 * Interface for fetching data from the db and formatting it to the appropriate object
 * @author Heather
 *
 * @param <T>
 */
public interface IDataRetriever {

	<T> List<T> retrieveData(final QueryType queryType_, final Object[] params_);
}
