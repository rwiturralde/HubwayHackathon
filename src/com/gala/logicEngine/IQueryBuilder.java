package com.gala.logicEngine;

public interface IQueryBuilder {

	MongoDbQueryObject buildQuery(final MongoDbQueryParameters params_);
	
}
