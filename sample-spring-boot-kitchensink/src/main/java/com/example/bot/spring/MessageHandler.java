package com.example.bot.spring;

import java.util.ArrayList;

public class MessageHandler {
		
	private StorageEngine database;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray) throws Exception {
		String intent = inputArray.get(0).toLowerCase();
		String answer = "Excuse me I cannot understand what you are trying to say. Could you try again?";
		switch(intent){
			case "none":
				handleNoneIntent();
				break;
			case "question":
				answer = getAsnwer(inputArray.get(1));
				break;
			case "booking":
				handleBookingIntent(inputArray);
				break;
		}
		return answer;      
    }
	
	private void handleBookingIntent(ArrayList<String> inputArray) {
		// TODO Auto-generated method stub
		
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


	private void handleNoneIntent() {
		
		
	}

}
