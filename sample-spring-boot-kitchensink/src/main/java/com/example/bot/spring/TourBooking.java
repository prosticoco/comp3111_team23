package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * Class to represent a tour booking, used whenever a customer books a tour on the line application
 * @author Ivan Bardarov
 *
 */
public class TourBooking {
	// attributes for the booking
	private int numOfAdults=-1;
	private int numOfChildren=-1;
	private int numOfToddlers=-1;

	//private String specialRequests="";
	private int price = 0;

	private int paid = 0;
	//private String specialRequests=null;

	private Tour tour;
	private Customer customer;
	
	public TourBooking(Tour tour, Customer customer){
		this.tour = tour;
		this.customer = customer;
	}
	/**
	 * method to get the number of adults that are being signed up in this tour booking
	 * @return the number of adults in the booking
	 */
	public int getAdultsNumber() {
		return numOfAdults;
	}
	/**
	 * method to set the number of adults registered in a booking
	 * @param adultsNumber the number of adults we want to set
	 */
	public void setAdultsNumber(int adultsNumber) {
		this.numOfAdults = adultsNumber;
	}
	/**
	 * method to get the number of children that are being signed up in this tour booking
	 * @return the number of children in the booking
	 */
	public int getChildrenNumber() {
		return numOfChildren;
	}
	
	/**
	 * method to set the number of children registered in a booking
	 * @param childrenNumber the number of children we want to set
	 */
	public void setChildrenNumber(int childrenNumber) {
		this.numOfChildren = childrenNumber;
	}

	/**
	 * method to get the number of toddlers that are signed up in the booking
	 * @return the number of toddlers in the booking
	 */
	public int getToddlersNumber() {
		return numOfToddlers;
	}

	/**
	 * method to set the number of toddlers that are signed up in the booking
	 * @param toddlersNumber the number of toddlers we want to set
	 */
	public void setToddlersNumber(int toddlersNumber) {
		this.numOfToddlers = toddlersNumber;
	}

	/**
	 * method that computes the price of the booking according to the number of adults/children
	 * 
	 */
	public void calcTourFee() {
		price = (int) (tour.getPrice() * numOfAdults + ( tour.getPrice()*0.8 ) * numOfChildren);

	}

	/**
	 * method to get the total amount that has been paid for the booking
	 * @return the amount that has been paid for the booking
	 */
	public int getPaid() {
		return paid;
	}
	/**
	 * method to set the total amount that has been paid for the booking
	 * @param paid integer representing the amount that has been paid
	 */
	public void setPaid(int paid) {
		this.paid = paid;
	}

	/*public String getSpecialRequests() {
		return specialRequests;
	}

	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}*/
	/**
	 * Method to get the instance of the specific tour of the booking
	 * @return the instance of the tour the booking represents
	 */
	public Tour getTour() {
		return tour;
	}

	/**
	 * method to set the tour instance that the booking involves
	 * @param tour the tour instance we want to set
	 */
	public void setTour(Tour tour) {
		this.tour = tour;
	}

	/**
	 * method to get the instance of the customer that is booking
	 * @return the customer entitled to this tour booking
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * method to get the price of the tour
	 * @return the price of the total booking
	 */
	public int getPrice() {
		calcTourFee();
		return price;
	}
	
	/**
	 * method to set the price of the tour
	 * @param price the new price we want to set for the tour
	 */
	public void setPrice(int price) {
		this.price = price;
	}


	/**
	 * method to set the customer entitled to this tour booking
	 * @param customer the customer involved in the booking
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * method that checks which attributes of the tour booking aren't set and creates an array with messages
	 * to be sent to the customer. 
	 * @return an ArrayList of strings containing messages to be sent to the customer
	 */
	public ArrayList<String> nullValues(){
		ArrayList<String> nullValues = new ArrayList<>();
		if(numOfAdults < 1) {
			nullValues.add("adults: <number of adults>");
		}
		if(numOfChildren == -1) {
			nullValues.add("children: <number of children>");
		}
		if(numOfToddlers == -1) {
			nullValues.add("toddlers: <number of toddlers>");
		}
		if(tour.getId() == null){
			nullValues.add("tour: <name of tour>");
		}
		if(tour.getDate() == null){
			nullValues.add("date: <date of tour>");
		}
		return nullValues;
	}
}
