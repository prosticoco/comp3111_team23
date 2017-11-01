package com.example.bot.spring;

import java.util.ArrayList;

public class MessageHandler {
		
	private StorageEngine database;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray) throws Exception {
		String intent = inputArray.get(0);
		String answer = null;
		switch(intent){
			case "none":
				handleNoneIntent();
			case "question":
				handleQuestionIntent(inputArray.get(1));
				break;
			case "booking":
				handleBookingIntent(inputArray);
				break;
			default:
				answer = "Excuse me I cannot understand what you are trying to say. Could you try again?";
				break;
		}
		return answer;      

    }
	
	private void handleBookingIntent(ArrayList<String> inputArray) {
		// TODO Auto-generated method stub
		
	}


	private void handleQuestionIntent(String question) {
		database.getFAQResponse(quesion);
		
	}


	private void handleNoneIntent() {
		
		
	}

}
