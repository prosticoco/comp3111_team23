package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TourBooking {
	
	private int numOfAdults=-1;
	private int numOfChildren=-1;
	private int numOfToddlers=-1;
	private int paid;
	//private String specialRequests="";
	private int price = 0;
	private Tour tour;
	private Customer customer;
	
	public TourBooking(Tour tour, Customer customer){
		this.tour = tour;
		this.customer = customer;
	}

	public int getAdultsNumber() {
		return numOfAdults;
	}

	public void setAdultsNumber(int adultsNumber) {
		this.numOfAdults = adultsNumber;
	}

	public int getChildrenNumber() {
		return numOfChildren;
	}

	public void setChildrenNumber(int childrenNumber) {
		this.numOfChildren = childrenNumber;
	}

	public int getToddlersNumber() {
		return numOfToddlers;
	}

	public void setToddlersNumber(int toddlersNumber) {
		this.numOfToddlers = toddlersNumber;
	}

	public void calcTourFee() {
		price = (int) (tour.getPrice() * numOfAdults + ( tour.getPrice()*0.8 ) * numOfChildren);
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	/*public String getSpecialRequests() {
		return specialRequests;
	}

	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}*/

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getPrice() {
		calcTourFee();
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

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
