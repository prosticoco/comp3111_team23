package com.example.bot.spring;


import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat; 

public class MessageHandler {
		
	private StorageEngine database;
	private Customer customer;
	private Tour tour;
	private TourBooking booking;
	private boolean cusNulls=true, bookNulls=true;
	private Date date;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
		customer = new Customer(null,null,0,0);
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray){
		String intent = inputArray.get(0).toLowerCase();
		
		//return default string if not found
		String answer = "Excuse me I cannot understand what you are trying to say. Could you try again?";
		
		
		if(intent.length()>=8 && intent.substring(intent.length()-8).toLowerCase().equals("question"))
			answer = getAsnwer(inputArray.get(0).substring(0,intent.length() - 8));
		else if(intent.length()>=7 && intent.substring(intent.length()-7).toLowerCase().equals("booking")){
			try {
				answer = handleBookingIntent(inputArray);
			} catch (Exception e) {
				answer = answer + "Make sure you spell the tour name and the date right (the date has to be in the following form yyyy-mm-dd)";
				date = null;
			}
		}
		else if(intent.length()>=12 && intent.substring(intent.length()-12).toLowerCase().equals("confirmation"))
			answer = completeBooking();
				
		return answer;      
   }
	
	private String handleBookingIntent(ArrayList<String> inputArray) throws Exception {
		String[] currentAttribute;
		
		for(int i = 1; i < inputArray.size(); i++){
			currentAttribute = inputArray.get(i).split(":");
			if(customer.nullValues().size() > 0 && checkBelongToCustomer(currentAttribute)) continue;
			else if(checkBelongToBooking(currentAttribute)) continue;
		}
		
		String answer = "Please provide details about:";
		
		if(booking.nullValues().size()>0){
			for(String s: booking.nullValues()){
				answer = answer + ", " + s;
			}
		}
		else cusNulls = false;
			
		if(customer.nullValues().size()>0){
			for(String s: customer.nullValues()){
				answer = answer + ", " + s;
			}
		}
		else bookNulls = false;
		
		
		if(!cusNulls && !bookNulls) answer = "Are you sure you want to make this booking? Press Y";			
		return answer;
	}


	private String completeBooking() {
		String answer = "Sorry I could not complete the booking"; 
		
		if(!cusNulls && !bookNulls) {
			try {
				database.addBooking(booking);
				database.addCustomer(customer);
				answer = "Your booking is complete";
				resetBooking();
				bookNulls = true;
			} catch (Exception e){
				
			}
		}
		
		return answer;
	}


	private boolean checkBelongToCustomer(String[] attributes) {
		boolean successful = false;
		
		try {
			customer = database.getCustomerDetails(customer.getId());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO: input validate everything 

		switch(attributes[0]){
			case "builtin.encyclopedia.people.person":
				customer.setName(attributes[1]);
				successful = true;
				break;
			case "builtin.age":
				customer.setAge(Integer.parseInt(attributes[1]));
				successful = true;
				break;
		}
		return successful;
	}


	private boolean checkBelongToBooking(String[] attributes) throws Exception {
		boolean successful = false;
		
		//TODO: input validate everything 
		
		switch(attributes[0]){
			case "numberOfAdults":
				booking.setAdultsNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "numberOfChildren":
				booking.setChildrenNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "numberOfToddlers":
				booking.setToddlersNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "tourType":
				GeneralTour temp;
				temp = database.getGeneralTourDetails(attributes[1]);
				tour.setId(temp.getId());
				if(date != null) setTour();
				break;
			case "builtin.datetimeV2.date":
				date = new SimpleDateFormat("yyyy-mm-dd").parse(attributes[1]);
				if(tour.getId()!= null) setTour();
				break;
				
			/*case "specialRequests":
				booking.setSpecialRequests(attributes[1]);
				successful = true;
				break;*/
		}
		return successful;
	}

	private void setTour() throws Exception{
		tour = database.getTourDetails(tour.getId(), date);
	}

	private String getAsnwer(String question) {
		String answer = "Excuse me, I do not have an answer for your question." +
							"We have sent it to our staff. Please wait for them to respond";
		try {
			answer = database.getFAQResponse(question);
		} catch (Exception e) {
			database.logQuestion(question);
		}
		return answer;
	}
	
	public void setCustomerId(String userId){
		customer.setId(userId);
	}

	private void resetBooking(){
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
	}
}
