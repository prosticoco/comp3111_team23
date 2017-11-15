package com.example.bot.spring;

import java.util.ArrayList;
import java.util.HashMap;

public class HandlerFactory {
	
	private HashMap<String,EventHandler> bookingMap = new HashMap<>();
	
	
	public EventHandler getHandler(String intent, String userId){
		EventHandler currEventHandler = null;
		if(intent.length()>=8 && intent.substring(intent.length()-8).equals("question")){
			//get answer from the FAQ table in the database
			currEventHandler = new QuestionHandler();
		}
		else if(intent.equals("booktour") || intent.equals("additionalinformation") || intent.equals("confirmation")){
			
			//get the needed eventHandler from the
			currEventHandler = bookingMap.get(userId);
			if(currEventHandler == null){
				currEventHandler = new BookingHandler(userId);
				bookingMap.put(userId,currEventHandler);
			}
			
			if(intent.equals("positiveconfirmation")){
				String answer = ((BookingHandler) currEventHandler).completeBooking("y");
				
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
		else if (intent.equals("enquiry")){
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
