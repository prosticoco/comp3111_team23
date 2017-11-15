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
	
	public MessageHandler() {
	}
	
	
	
	public String handleTextContent(ArrayList<String> inputArray, String userId){
		String intent = inputArray.get(0).toLowerCase();
		
		//return default string if not found
		String answer = DEFAULTANSWER;
		
		
		if(intent.length()>=8 && intent.substring(intent.length()-8).equals("question")){
			//get answer from the FAQ table in the database
			currEventHandler = new QuestionHandler();
		}
		else if(intent.equals("booktour") || intent.equals("additionalinformation") || intent.equals("confirmation")){
			currEventHandler = bookingMap.get(userId);
			if(currEventHandler == null){
				currEventHandler = new BookingHandler(userId);
				bookingMap.put(userId,currEventHandler);
			}
			
			if(intent.equals("confirmation")){
				answer = ((BookingHandler) currEventHandler).completeBooking(inputArray.get(1).toLowerCase());
				if(answer == COMPLETEDBOOKING)
					bookingMap.remove(userId);
				return answer;
			}
		}
		else if (intent.equals("greeting")) { 
			return answer = "Hello, how can I help you today?";
		}
		else if (intent.equals("none")){
			//database.logQuestion(inputArray.get(1));
		}
		answer = currEventHandler.handleEvent(inputArray);
		return answer;
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