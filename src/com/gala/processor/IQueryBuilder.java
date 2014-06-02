package com.gala.processor;

import com.mongodb.DBObject;

public interface IQueryBuilder {

	DBObject[] buildQuery();
	
}
