package com.example.bot.spring;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingHandler implements EventHandler{
	
	private boolean cusNulls=true, bookNulls=true;
	private StorageEngine database = new PSQLDatabaseEngine();
	private Customer customer = new Customer(null,null,0,0);
	private Tour tour = new Tour(null,null,0,null,null,null,0,0);
	private TourBooking booking = new TourBooking(tour, customer);
	private Date date;
	
	public BookingHandler(String userId){
		customer.setId(userId);
	}
	
	public String handleEvent(ArrayList<String> inputArray){
		String[] currentAttribute;
		
		for(int i = 1; i < inputArray.size(); i++){
			//separate the attribute name and attribute value
			currentAttribute = inputArray.get(i).split(":");
			if(customer.nullValues().size()>0 && addToCustomer(currentAttribute))
				continue;
			else{
				try {
					if(addToBooking(currentAttribute)) 
						continue;
				} catch (Exception e) {
					e.printStackTrace();
					date = null;
					return "Make sure you spell the tour name and the date right (the date has to be in the following form yyyy-mm-dd)";	
				}
			}			
		}

		
		return provideAnswer();
	}

	private String provideAnswer() {
		//default string in case of insufficient amount of attributes
		String answer = "Please provide more details about the tour and the people going, in the following format:";
		
		answer += appendBookingNullValues(booking,answer);
		answer += appendCustomerNullValues(customer,answer);
		
		//if no more attributes needed ask for confirmation
		if(!cusNulls && !bookNulls) 
			answer = MessageHandler.CONFIRMATION;
		
		return answer;
	}
	

	private boolean addToCustomer(String[] attributes) {
		boolean successful = false;
		
		switch(attributes[0]){
			case "builtin.encyclopedia.people.person":
				customer.setName(attributes[1]);
				successful = true;
				break;
			case "builtin.age":
				customer.setAge(Integer.parseInt(attributes[1].replaceAll("[\\D]", "")));
				successful = true;
				break;
		}
		return successful;
	}
	
	private boolean addToBooking(String[] attributes) throws Exception{
		boolean successful = false;
		
		//TODO: input validate everything 
		String atrb = attributes[1];
		
		// if it is additional information, the attributes array will have three indexes instead of one, and in the case we need to grab the last index		
		if (attributes.length > 2) {
			atrb = attributes[2].replaceAll("\\s","");
		}
		
		switch(attributes[0]){
			case "numberOfAdults":
				booking.setAdultsNumber(Integer.parseInt(atrb.replaceAll("[\\D]", "")));
				successful = true;
				break;
			case "numberOfChildren":
				booking.setChildrenNumber(Integer.parseInt(atrb.replaceAll("[\\D]", "")));
				successful = true;
				break;
			case "numberOfToddlers":
				booking.setToddlersNumber(Integer.parseInt(atrb.replaceAll("[\\D]", "")));
				successful = true;
				break;
			case "tourType":
				String tourName = atrb.replaceAll("\\s+","").toLowerCase();
				tour.setId(database.getGeneralTourDetails(tourName).getId());
				setTour();
				break;
			case "builtin.datetimeV2.date":
				date = new SimpleDateFormat("yyyy-MM-dd").parse(atrb);
				setTour();
				break;
				
			/*case "specialRequests":
				booking.setSpecialRequests(attributes[1]);
				successful = true;
				break;*/
		}
		return successful;
	}
	
	private String appendCustomerNullValues(Customer c, String str){
		ArrayList<String> nulls = c.nullValues();
		if(nulls.size()<= 0)
			cusNulls = false;
		return appendNullValues(nulls,str);
	}
	
	private String appendBookingNullValues(TourBooking b, String str){
		ArrayList<String> nulls = b.nullValues();
		if(nulls.size()<= 0)
			bookNulls = false;
		return appendNullValues(nulls,str);
	}
	
	private String appendNullValues(ArrayList<String> nulls, String str){
		if(nulls.size()>0){
			for(String s: nulls){
				str = str + "\n" + s;
			}
		}
		return str;
	}
	
	private void setTour() throws Exception{
		if(date != null & tour.getId() != null){
			tour = database.getTourDetails(tour.getId(), date);
		}
	}
	
	public String completeBooking() {
		//default answer in case something goes wrong
		String answer = MessageHandler.ERROR;
		if(!cusNulls && !bookNulls){
			if(database.getNumberBookedTours(tour) < tour.getCapacity()){
				try {
					database.addCustomer(customer);
					database.addBooking(booking);
					answer = MessageHandler.COMPLETEDBOOKING;
				} catch (URISyntaxException e) {
					answer = MessageHandler.SQLERROR;
				}
			}
			else{
				answer = MessageHandler.FULLTOUR;
			}
		}
		resetHandler();
		return answer;
	}
	
	private void resetHandler(){
		customer = new Customer(null,null,0,0);
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
		bookNulls = true;
		cusNulls = true;
	}


}
