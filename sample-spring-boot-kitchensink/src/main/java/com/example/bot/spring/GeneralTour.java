package com.example.bot.spring;
/**
 * This class is used to represent a Tour, specificaly basic information about it. 
 * 
 * @author Ivan Bardarov
 *
 */
public class GeneralTour {
	// atributes for the tour, id is key.
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
	/**
	 *  This method is used to get the id of the tour
	 * @return the id of the tour
	 */
	public String getId() {
		return id;
	}
	/**
	 * This method is used to get the name of the tour
	 * @return the name of the tour
	 */
	public String getName() {
		return name;
	}
	/**
	 * This method is used to get the description of the tour
	 * @return the description of the tour
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * This method is used to get the information about when in the week is the tour usually planned
	 * @return a string giving info about the days the tour usually takes place
	 */
	public String getDays() {
		return days;
	}
	
	

}
