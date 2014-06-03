package com.gala.core;

public class Station {

	private String _name;
	private int _id;
	private Double _lat;
	private Double _lon;
	private String _municipality;
	private int _capacity;
	
	public Station(){
		_name = "";
		_id = 0;
		_lat = new Double(0.0);
		_lon = new Double(0.0);
		_municipality = "";
		_capacity = 0;
	}
	
	public Station(final String name_, final int id_, final Double lon_, final Double lat_, final int capacity_){
		_name = name_;
		_id = id_;
		_lon = lon_;
		_lat = lat_;
		_capacity = capacity_;
	}
	
	public String getName(){
		return _name;
	}
	
	public int getId(){
		return _id;
	}
	
	public Double getLatitude(){
		return _lat;
	}
	
	public Double getLongitude(){
		return _lon;
	}
	
	public int getCapacity(){
		return _capacity;
	}
	
	public void setName(final String name_){
		_name = name_;
	}
	
	public void setId(final int id_){
		_id = id_;
	}
	
	public void setLatitude(final Double lat_){
		_lat = lat_;
	}
	
	public void setLongitude(final Double lon_){
		_lon = lon_;
	}

	public String getMunicipality() {
		return _municipality;
	}

	public void setMunicipality(String municipality_) {
		this._municipality = municipality_;
	}
	
	public void setCapacity(final int capacity_){
		_capacity = capacity_;
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
