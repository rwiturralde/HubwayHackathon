package com.gala.logicEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.gala.core.Station;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDbDataRetrieverStation extends MongoDbDataRetrieverBase<Station> {

	static final Logger _logger 			= Logger.getLogger(MongoDbDataRetrieverSingleObj.class);
	
	protected String _idKey;
	protected String _nameKey;
	protected String _latKey;
	protected String _lonKey;
	protected String _municipalityKey;
	protected String _capacityKey;
	
	public MongoDbDataRetrieverStation(final MongoClient client_,final String dbName_,
			final List<String> collNames_, final MongoDbQueryBuilder mongoDbQueryBuilder_,
			final String idKey_, final String nameKey_, final String municipalityKey_, 
			final String capacityKey_, final String latKey_, final String lonKey_) {
		super(client_, dbName_, collNames_, mongoDbQueryBuilder_);
		_idKey = idKey_;
		_nameKey = nameKey_;
		_latKey = latKey_;
		_lonKey = lonKey_;
		_municipalityKey = municipalityKey_;
		_capacityKey = capacityKey_;
	}

	public List<Station> retrieveData(MongoDbQueryParameters params_) {
		Iterator<DBObject> iterator =  retrieveCursor(params_);
		
		if (iterator == null){
			// errors logged at retrieve cursor
			return null;
		}
		
		List<Station> returnList = new ArrayList<Station>();
		while(iterator.hasNext()){
			DBObject responseObj = iterator.next();
			
	
			int id = 0;
			String name = "";			
			Double lat = new Double(0.0);
			Double lon = new Double(0.0);
			String municipality = "";
			int capacity = 0;
			

			Object idObj = responseObj.get(_idKey);
			if (idObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _idKey));
			} else {
				try {
					id = Integer.parseInt(idObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", idObj.toString(), _idKey));
				}				
			}
			
			Object nameObj = responseObj.get(_nameKey);
			if (nameObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _nameKey));
			} else {
				name = nameObj.toString();			
			}
			
			Object latObj = responseObj.get(_latKey);
			if (latObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _latKey));
			} else {
				try {
					lat = Double.parseDouble(latObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a date. Station Status will not be fully populated.", latObj.toString(), _latKey));
				}				
			}
			
			Object lonObj = responseObj.get(_lonKey);
			if (lonObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _lonKey));
			} else {
				try {
					lon = Double.parseDouble(lonObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to a Day. Station Status will not be fully populated.", lonObj.toString(), _lonKey));
				}				
			}
			
			Object municipalityObj = responseObj.get(_municipalityKey);
			if (nameObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _municipalityKey));
			} else {
				municipality = municipalityObj.toString();			
			}
						
			Object capacityObj = responseObj.get(_capacityKey);
			if (capacityObj == null){
				_logger.warn(String.format("Unable to retrieve field %s from DB response", _capacityKey));
			} else {
				try {
					capacity = Integer.parseInt(capacityObj.toString());
				} catch (Exception e) {
					_logger.warn(String.format("Unable to parse %s from field %s to an int. Station Status will not be fully populated.", capacityObj.toString(), _capacityKey));
				}				
			}
			
			returnList.add(new Station(id, name, municipality, capacity, lat, lon));
		}
		
		return returnList;
	}

}
