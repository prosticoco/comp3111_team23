package com.example.bot.spring;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat; 

@Slf4j
public class MessageHandler implements Callable<String>{
		
	private StorageEngine database;
	private Customer customer;
	private Tour tour;
	private TourBooking booking;
	private boolean cusNulls=true, bookNulls=true;
	private Date date;
	private ArrayList<String> input;
	
	public MessageHandler(ArrayList<String> inputArray, String userId) {
		input = inputArray;
		setCustomer(userId);
		database = new PSQLDatabaseEngine();
		customer = new Customer(null,null,0,0);
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
	}
	
	@Override
	public String call(){
		return handleTextContent(input);
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray){
		String intent = inputArray.get(0).toLowerCase();
		
		//return default string if not found
		String answer = "Excuse me I cannot understand what you are trying to say. We have logged your query. Could you try again?";
		
		if(intent.length()>=8 && intent.substring(intent.length()-8).equals("question")){
			//get answer from the FAQ table in the database
			answer = getAsnwer(inputArray.get(0).substring(0,intent.length() - 8));
		}
		else if(intent.equals("booktour")){
			try {
				answer = handleBookingIntent(inputArray);
			} catch (Exception e) {
				answer = "Make sure you spell the tour name and the date right (the date has to be in the following form yyyy-mm-dd)";
				date = null;
			}
			
			
		}
		else if(intent.equals("additionalinformation")){	
			try {
				answer = handleBookingIntent(inputArray);
			} catch (Exception e) {
				answer = "Make sure you spell the tour name and the date right (the date has to be in the following form yyyy-mm-dd)";
			}
		}
		else if(intent.equals("confirmation")){
			if(inputArray.get(1).toLowerCase().equals("y")) {
				answer = completeBooking();
			}
			else {
				answer = "Booking cancelled. Is there anything else I can do for you today?";
				resetHandler();
			}
		}
		else if (intent.equals("greeting")) { 
			answer = "Hello, how can I help you today?";
		}
				
		return answer;
	}
	
	public void setCustomer(String userId){
		if(customer.getId() == null){
			try {
				customer = database.getCustomerDetails(userId);
			} catch (Exception e) {
				customer.setId(userId);
				log.info("Customer could not be find creating a new one instead");
			}
		}
	}
	
	private String handleBookingIntent(ArrayList<String> inputArray) throws Exception{
		String[] currentAttribute;
		for(int i = 1; i < inputArray.size(); i++){
			//separate the attribute name and attribute value
			currentAttribute = inputArray.get(i).split(":");
			
			if(customer.nullValues().size()>0 && checkBelongToCustomer(currentAttribute))
				continue;
			else if(checkBelongToBooking(currentAttribute)) 
				continue;
		}
		
		//default string in case of insufficient amount of attributes
		String answer = "Please provide more details about the tour and the people going, in the following format:";
		
		answer += appendBookingNullValues(booking,answer);
		answer += appendCustomerNullValues(customer,answer);
		
		//if no more attributes needed ask for confirmation
		if(!cusNulls && !bookNulls) 
			answer = "Are you sure you want to make this booking? Press Y";	
		
		return answer;
	}

	private String completeBooking() {
		//default answer in case something goes wrong
		String answer = "Something went wrong.Sorry for the inconvenience, could you please provide us with all the details again."; 
		if(!cusNulls && !bookNulls){
			if(database.getNumberBookedTours(tour) < tour.getCapacity()){
				try {
					database.addCustomer(customer);
					database.addBooking(booking);
					answer = "Your booking is complete";
				} catch (URISyntaxException e) {
					answer = "Sorry I could not complete the booking. The server is not working, please try again later";
				}
			}
			else{
				answer = "The tour is full. We are sorry for the inconvenience. Please a different date or another tour.";
			}
		}
		resetHandler();
		return answer;
	}

	private boolean checkBelongToCustomer(String[] attributes) {
		boolean successful = false;
		
		//TODO: input validate everything 
		
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

	private boolean checkBelongToBooking(String[] attributes) throws Exception{
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

	private void setTour() throws Exception{
		if(date != null & tour.getId() != null){
			tour = database.getTourDetails(tour.getId(), date);
		}
	}

	private String getAsnwer(String question) {
		String answer = null;
		try {
			answer = database.getFAQResponse(question);
		} catch (Exception e) {
			answer = "Excuse me, I do not have an answer for your question." +
						"We have sent it to our staff. Please wait for them to respond";
		}
		return answer;
	}
	
	private void resetHandler(){
		customer = new Customer(null,null,0,0);
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
		bookNulls = true;
		cusNulls = true;
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
}
