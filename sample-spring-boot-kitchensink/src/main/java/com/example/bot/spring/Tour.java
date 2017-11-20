package com.example.bot.spring;

import java.util.Date;
/**
 * Class to represent a specific tour (has a reference to the tour id but describes a particular instance of a tour)
 * @author Ivan Bardarov
 *
 */
public class Tour {
	// attributes of the tour
	private String id;
	// the specific date the tour will start
	private Date date;
	private int price;
	private String tourGuide;
	// line account of the tour guide
	private String tourGuideLineAcc;
	private String hotel;
	private int capacity;
	// minimum amount of customers required for the tour to take place
	private int minCustomers;

	
	public Tour(String id, Date date, int price, String tourGuide,  String tourGuideLineAcc, String hotel, int capacity, int minCustomers){
		this.id = id;
		this.date = date;
		this.price = price;
		this.tourGuide = tourGuide;
		this.tourGuideLineAcc = tourGuideLineAcc;
		this.hotel = hotel;
		this.capacity = capacity;
		this.minCustomers = minCustomers;
	}
	/**
	 * method to get the unique id of the tour
	 * @return the id of the tour
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * method to get the date the tour will take place
	 * @return the date of the tour
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * method to get the price of the tour	
	 * @return the price of the tour
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * method to get the name of the tour guide
	 * @return the name of the tour guide
	 */
	public String getTourGuide() {
		return tourGuide;
	}
	
	/**
	 * method to get the line username of the tour guide
	 * @return the username of the tour guide
	 */
	public String getTourGuideLineAcc() {
		return tourGuideLineAcc;
	}
	
	/**
	 * method to get the name of the hotel if any for the tour
	 * @return the name of the hotel if any
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * method to get the maximum number of customers that can join a tour
	 * @return the maximum/capacity of the tour
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * method to get the minimum amount of customers a tour needs in order to take place
	 * @return the minimum amount of customers 
	 */
	public int getMinCustomers() {
		return minCustomers;
	}
	
	/**
	 * method to set the unique id of the tour
	 * @param id the new id we want to assign 
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * method to set a new date for the tour
	 * @param date the new date we want to assign
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
