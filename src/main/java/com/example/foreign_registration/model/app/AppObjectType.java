package com.example.foreign_registration.model.app;

public enum AppObjectType {
	
	ASSESSMENT("A"),
	ORDER("O"),
	CALCULATION("C"),
	MARKETING_AUTHORIZATION("MA");
	
	private String objectShortcut;
	
	AppObjectType(String objectShortcut){
		this.objectShortcut = objectShortcut;
	}
	
	public String getObjectShortcut(){
		return this.objectShortcut;
	}
	
}
