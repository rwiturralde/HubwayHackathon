package com.gala.core;

public class Station {

	private String _name;
	private int _id;
	private double _lat;
	private double _lon;
	
	public Station(){
		_name = "";
		_id = 0;
		_lat = 0.0;
		_lon = 0.0;
	}
	
	public Station(final String name_, final int id_, final double lon_, final double lat_){
		_name = name_;
		_id = id_;
		_lon = lon_;
		_lat = lat_;
	}
	
	public String getName(){
		return _name;
	}
	
	public int getId(){
		return _id;
	}
	
	public double getLatitude(){
		return _lat;
	}
	
	public double getLongitude(){
		return _lon;
	}
	
	public void setName(final String name_){
		_name = name_;
	}
	
	public void setId(final int id_){
		_id = id_;
	}
	
	public void setLatitude(final double lat_){
		_lat = lat_;
	}
	
	public void setLongitude(final double lon_){
		_lon = lon_;
	}
	
}
