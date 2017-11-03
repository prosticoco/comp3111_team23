package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TourBooking {
	
	private int numOfAdults=-1;
	private int numOfChildren=-1;
	private int numOfToddlers=-1;
	private int paid;
	//private String specialRequests=null;
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

	public int calcTourFee() {
		int price = tour.getPrice();
		double fee = price * numOfAdults + (price*0.8) * numOfChildren;
		return (int)fee;
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
			nullValues.add("toddler: <number of toddlers>");
		}
		if(tour == null){
			nullValues.add("tour: <name of tour>");
			nullValues.add("date: <date of tour>");
		}
//		Field[] fields = this.getClass().getDeclaredFields();
//		for(Field f : fields){
//			f.setAccessible(true);
//			try {
//				
//				if(!f.getType().isPrimitive()){
//					if(f.get(this) == null) nullValues.add(f.getName());
//				}else{
//					if(f.get(this).equals(0)) nullValues.add(f.getName());
//				}
//				
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return nullValues;
	}
}
