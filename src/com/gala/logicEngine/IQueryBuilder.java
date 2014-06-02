package com.gala.logicEngine;

public interface IQueryBuilder {

	MongoDbQueryObject buildQuery(final QueryType query_);
	
}
