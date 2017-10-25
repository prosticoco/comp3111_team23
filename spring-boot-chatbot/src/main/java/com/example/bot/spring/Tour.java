package com.example.bot.spring;

public class Tour {

	private String id;
	private int price;
	private String name;
	private String description;
	private int availability;
	
	public Tour(String id, String name, int price, String description, int availability){
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.availability = availability;
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getAvailability() {
		return availability;
	}
	
	public void setAvailability(int availability) {
		this.availability = availability;
	}
	
}
