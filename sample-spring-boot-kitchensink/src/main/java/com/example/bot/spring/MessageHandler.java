package com.example.bot.spring;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat; 

@Slf4j
public class MessageHandler{
	
	public static final String COMPLETEDBOOKING = "Your booking is complete";
	public static final String DEFAULTANSWER = "Excuse me I cannot understand what you are trying to say. We have logged your query. Could you try again?";
	public static final String CONFIRMATION = "Are you sure you want to make this booking? Press Y";
	private EventHandler currEventHandler;
	private HashMap<String,EventHandler> bookingMap = new HashMap<>();
	
	
	public String handleTextContent(ArrayList<String> inputArray, String userId){
		//get the customers intent from  the first index of the array
		String intent = inputArray.get(0).toLowerCase();
		
		
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
			
			if(intent.equals("confirmation")){
				String answer = ((BookingHandler) currEventHandler).completeBooking(inputArray.get(1).toLowerCase());
				
				if(answer == COMPLETEDBOOKING)
					bookingMap.remove(userId);
				
				return answer;
			}
		}
		else if (intent.equals("none")){
			currEventHandler = new LoggerHandler();
		}
		else if (intent.equals("greeting")) { 
			return "Hello, how can I help you today?";
		}

		//return the answer that the eventhandler send
		return currEventHandler.handleEvent(inputArray);
	}



	private void removeEventHandler() {
		Iterator<Map.Entry<String,EventHandler>> iter = bookingMap.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<String,EventHandler> entry = iter.next();
		    if(entry == null){
		        iter.remove();
		    }
		}
	}
}