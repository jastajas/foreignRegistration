package com.example.foreign_registration.model.app;

public enum AppObject{
	
	ASSESSMENT("A"),
	ORDER("O"),
	CALCULATION("C"),
	MARKETING_AUTHORIZATION("MA");
	
	private String objectShortcut;
	
	AppObject(String objectShortcut){
		this.objectShortcut = objectShortcut;
	}
	
	public String getObjectShortcut(){
		return this.objectShortcut;
	}
	
}