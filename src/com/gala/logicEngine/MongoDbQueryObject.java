package com.gala.logicEngine;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoDbQueryObject {

	protected boolean _shouldAggregate;
	protected BasicDBObject _queryObject;
	protected BasicDBObject _fieldsRequest;
	protected List<DBObject> _aggregateObject;
	protected String _collectionName;
	
	public MongoDbQueryObject(BasicDBObject queryObject_,
			BasicDBObject fieldsRequest_, String collectionName_) {
		_queryObject = queryObject_;
		_fieldsRequest = fieldsRequest_;
		_collectionName = collectionName_;
		_shouldAggregate = false;
	}
	
	public MongoDbQueryObject(List<DBObject> aggregateObject_,
			String collectionName_) {
		_aggregateObject = aggregateObject_;
		_collectionName = collectionName_;
		_shouldAggregate = true;
	}
	
	public BasicDBObject getQueryObject() {
		return _queryObject;
	}

	public void setQueryObject(BasicDBObject queryObject) {
		_queryObject = queryObject;
	}

	public BasicDBObject getFieldsRequest() {
		return _fieldsRequest;
	}

	public void setFieldsRequest(BasicDBObject fieldsRequest) {
		_fieldsRequest = fieldsRequest;
	}

	public String getCollectionName() {
		return _collectionName;
	}

	public void setCollectionName(String collectionName) {
		_collectionName = collectionName;
	}
	
	public List<DBObject> getAggregateObject(){
		return _aggregateObject;
	}
	
	public void setAggregateObject(List<DBObject> aggregateObject) {
		_aggregateObject = aggregateObject;
	}
	
	public boolean getShouldAggregate() {
		return _shouldAggregate;
	}
	
	public void setShouldAggregate(boolean shouldAggregate) {
		_shouldAggregate = shouldAggregate;
	}
}
