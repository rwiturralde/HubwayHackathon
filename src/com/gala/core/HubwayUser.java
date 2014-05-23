package com.gala.core;

public class HubwayUser {
	private String _userId;
	private int _age;
	
	public HubwayUser(){
		_userId = "";
		_age = 0;
	}
	
	public HubwayUser(final String userId_, final int age_){
		_userId = userId_;
		_age = age_;
	}
	
	public String getUserId(){
		return _userId;
	}
	
	public int getAge(){
		return _age;
	}
	
	public void setUserId(String userId_){
		_userId = userId_;
	}
	
	public void setAge(int age_){
		_age = age_;
	}
}
