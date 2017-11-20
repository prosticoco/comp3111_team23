package com.example.bot.spring;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * This Class serves as a factory for different handler Classes
 * we use a hash map for booking in order to give each booking/query a different handler
 * @author Ivan Bardarov
 *
 */
public class HandlerFactory {
	
	private HashMap<String,EventHandler> bookingMap = new HashMap<>();
	
	/**
	 * This method is used to get a specific handler according to the intent and userId 
	 * of the customer
	 * when the query is a booktour or info about a tour we try to see if the handler is already in the hashmap.
	 * @param intent the intent of the query
	 * @param userId the user Id of the customer making the query
	 * @return an EventHandler that suits the user's request
	 */
	public EventHandler getHandler(String intent, String userId){
		EventHandler currEventHandler = null;
		if(intent.length()>=8 && intent.substring(intent.length()-8).equals("question")){
			//get answer from the FAQ table in the database
			currEventHandler = new QuestionHandler();
		}
		else if(intent.equals("booktour") || intent.equals("additionalinformation") || intent.equals("positiveconfirmation")){
			
			//get the needed eventHandler from the
			currEventHandler = bookingMap.get(userId);
			if(currEventHandler == null){
				currEventHandler = new BookingHandler(userId);
				bookingMap.put(userId,currEventHandler);
			}
			
			if(intent.equals("positiveconfirmation")){
				String answer = ((BookingHandler) currEventHandler).completeBooking();
				
				if(answer == MessageHandler.COMPLETEDBOOKING)
					bookingMap.remove(userId);
				
				currEventHandler = new EventHandler(){
					
					@Override
					public String handleEvent(ArrayList<String> inputArray) {
						return answer;
					}
				};
			}
		}
		else if (intent.equals("none")){
			currEventHandler = new LoggerHandler();
		}
		else if (intent.length()>=7 && intent.substring(intent.length()-7).equals("enquiry")){
			currEventHandler = new EnquiryHandler();
		}
		else if (intent.equals("greeting")) { 
			currEventHandler = new EventHandler(){
			
				@Override
				public String handleEvent(ArrayList<String> inputArray) {
					return MessageHandler.GREETING;
				}
			};
		}
		else{
			currEventHandler = new EventHandler(){
				
				@Override
				public String handleEvent(ArrayList<String> inputArray) {
					return MessageHandler.ERROR;
				}
			};
		}
		
		return currEventHandler;
	 }
	
}
