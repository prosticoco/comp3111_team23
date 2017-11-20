package com.example.bot.spring;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * The class representing the event handler that is in charge of handling bookings
 * @author Ivan Bardarov
 *
 */
public class BookingHandler implements EventHandler{
	
	private boolean cusNulls=true, bookNulls=true;
	private StorageEngine database = new PSQLDatabaseEngine();
	private Customer customer = new Customer(null,null,0,0);
	private Tour tour = new Tour(null,null,0,null,null,null,0,0);
	private TourBooking booking = new TourBooking(tour, customer);

	
	public BookingHandler(String userId){
		customer.setId(userId);
	}
	/**
	 * the handleEvent method this class overrides. it verifies that the input is correct and if so calls the provideAnswer method 
	 * to process the input and return the answer string. if the input isn't valid it returns an error message to be sent to the customer
	 */
	@Override 
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
					tour.setDate(null);
					tour.setId(null);
					return "Make sure you spell the tour name and the date right (the date has to be in the following form yyyy-mm-dd)";	
				}
			}			
		}

		
		return provideAnswer();
	}
	/**
	 * Method called my the Handler to provide a bot answer to the customer who is booking a tour.
	 * @return a message informing the customer that more information is needed to complete the booking, or a confirmation of 
	 * the booking if all the information needed has been provided by the customer
	 */
	private String provideAnswer() {
		//default string in case of insufficient amount of attributes
		String answer = "Please provide more details about the tour and the people going, in the following format:";
		
		answer += appendBookingNullValues(booking);
		answer += appendCustomerNullValues(customer);
		
		//if no more attributes needed ask for confirmation
		if(!cusNulls && !bookNulls) 
			answer = MessageHandler.CONFIRMATION + booking.getPrice();
		
		return answer;
	}
	
	/**
	 * method that adds to the customer member of the class an attribute (ex age or name)
	 * @param attributes an array containing information on the type of attribute and the value attribute itself
	 * @return a boolean indicating if the operation was successful or not
	 */
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
				tour.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(atrb));
				setTour();
				break;
				
			/*case "specialRequests":
				booking.setSpecialRequests(attributes[1]);
				successful = true;
				break;*/
		}
		return successful;
	}
	
	private String appendCustomerNullValues(Customer c){
		ArrayList<String> nulls = c.nullValues();
		if(nulls.size()<= 0)
			cusNulls = false;
		return appendNullValues(nulls);
	}
	
	private String appendBookingNullValues(TourBooking b){
		ArrayList<String> nulls = b.nullValues();
		if(nulls.size()<= 0)
			bookNulls = false;
		return appendNullValues(nulls);
	}
	
	private String appendNullValues(ArrayList<String> nulls){
		String str = "";
		if(nulls.size()>0){
			for(String s: nulls){
				str = str + "\n" + s;
			}
		}
		return str;
	}
	
	private void setTour() throws Exception{
		if(tour.getDate() != null && tour.getId() != null){
			System.out.println("I JUST ENTERED HERE----------------------------------");
			booking.setTour(database.getTourDetails(tour.getId(), tour.getDate()));
		}
	}
	
	public String completeBooking() {
		//default answer in case something goes wrong
		String answer = MessageHandler.ERROR;
		if(!cusNulls && !bookNulls){
			if(database.getNumberBookedTours(tour) < booking.getTour().getCapacity()){
				try {
					database.addCustomer(customer);
					database.addBooking(booking);
					answer = MessageHandler.COMPLETEDBOOKING + booking.getPrice();
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
	/**
	 * method used to reset the Handler, ie reset its member values to default values.
	 */
	private void resetHandler(){
		customer = new Customer(null,null,0,0);
		tour = new Tour(null,null,0,null,null,null,0,0);
		booking = new TourBooking(tour, customer);
		bookNulls = true;
		cusNulls = true;
	}


}
