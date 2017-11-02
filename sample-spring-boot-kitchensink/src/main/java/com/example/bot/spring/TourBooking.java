package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TourBooking {
	
	private int adultsNumber=0;
	private int childrenNumber=0;
	private int toddlersNumber=0;
	private int tourFee=0;
	private int paid=0;
	//private String specialRequests=null;
	private Tour tour;
	private Customer customer;
	
	public TourBooking(Tour tour, Customer customer){
		this.tour = tour;
		this.customer = customer;
	}

	public int getAdultsNumber() {
		return adultsNumber;
	}

	public void setAdultsNumber(int adultsNumber) {
		this.adultsNumber = adultsNumber;
	}

	public int getChildrenNumber() {
		return childrenNumber;
	}

	public void setChildrenNumber(int childrenNumber) {
		this.childrenNumber = childrenNumber;
	}

	public int getToddlersNumber() {
		return toddlersNumber;
	}

	public void setToddlersNumber(int toddlersNumber) {
		this.toddlersNumber = toddlersNumber;
	}

	public int getTourFee() {
		return tourFee;
	}

	public void setTourFee(int tourFee) {
		this.tourFee = tourFee;
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
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field f : fields){
			f.setAccessible(true);
			try {
				
				if(!f.getType().isPrimitive()){
					if(f.get(this) == null) nullValues.add(f.getName());
				}else{
					if(f.get(this).equals(0)) nullValues.add(f.getName());
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nullValues;
	}
}
