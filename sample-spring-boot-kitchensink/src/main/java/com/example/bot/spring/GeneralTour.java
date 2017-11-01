package com.example.bot.spring;

public class GeneralTour {
	
	private String id;
	private String name;
	private String description;
	private String days;
	
	public GeneralTour(String id, String name, String description, String days){
		this.id = id;
		this.name = name;
		this.description = description;
		this.days = days;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDays() {
		return days;
	}
	
	

}
