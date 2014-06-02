package com.gala.logicEngine;

import com.mongodb.BasicDBObject;

public class MongoDbQueryObject {

	protected BasicDBObject _queryObject;
	protected BasicDBObject _fieldsRequest;
	protected String _collectionName;
	
	public MongoDbQueryObject(BasicDBObject queryObject_,
			BasicDBObject fieldsRequest_, String collectionName_) {
		_queryObject = queryObject_;
		_fieldsRequest = fieldsRequest_;
		_collectionName = collectionName_;
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
}
