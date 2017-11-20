package com.example.bot.spring;


import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static final String COMPLETEDBOOKING = "Your booking is complete. You have to pay ";
	public static final String DEFAULTANSWER = "Excuse me I cannot understand what you are trying to say. We have logged your query. Could you try again?";
	public static final String CONFIRMATION = "Are you sure you want to make this booking?(Press Y). The  total price will be ";
	public static final String ERROR = "Excuse me something went wrong. Could you please try again or rephrase.";
	public static final String SQLERROR = "I am sorry, we  have technical issues, please try again later. Sorry for the inconvenience.";
	public static final String GREETING = "Hello, how can I help you today?";
	public static final String FULLTOUR = "The tour is full. We are sorry for the inconvenience. Please pick a different date or another tour.";
	private EventHandler currEventHandler;
	private HandlerFactory factory;
	
	
	
	
	public MessageHandler(HandlerFactory factory){
		this.factory = factory;
	}
	
	public String handleTextContent(ArrayList<String> inputArray, String userId){
		//get the customers intent from  the first index of the array
		String intent = inputArray.get(0).toLowerCase();
		
		//Controller.instance.pushCustomerNotification(new ArrayList<String>(Arrays.asList("U6c377e75e1d6c2b1f0805c82ebb880f9")), "rabotq we");
		
		currEventHandler = factory.getHandler(intent, userId);
		
		
		//return the answer that the eventhandler send
		return currEventHandler.handleEvent(inputArray);
	}


}