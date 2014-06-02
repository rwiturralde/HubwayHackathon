package com.gala.core;

public class Station {

	private String _name;
	private int _id;
	private double _lat;
	private double _lon;
	private String _municipality;
	
	public Station(){
		_name = "";
		_id = 0;
		_lat = 0.0;
		_lon = 0.0;
		_municipality = "";
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

	public String getMunicipality() {
		return _municipality;
	}

	public void setMunicipality(String municipality_) {
		this._municipality = municipality_;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (_id != other._id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Station [_name=");
		builder.append(_name);
		builder.append(", _id=");
		builder.append(_id);
		builder.append(", _lat=");
		builder.append(_lat);
		builder.append(", _lon=");
		builder.append(_lon);
		builder.append(", _municipality=");
		builder.append(_municipality);
		builder.append("]");
		return builder.toString();
	}	
	
}
