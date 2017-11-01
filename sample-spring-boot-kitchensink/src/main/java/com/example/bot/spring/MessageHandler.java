package com.example.bot.spring;

import java.util.ArrayList;

public class MessageHandler {
		
	private StorageEngine database;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray) throws Exception {
		String intent = inputArray.get(0).toLowerCase();
		String answer = null;
		switch(intent){
			case "none":
				handleNoneIntent();
				answer = "Excuse me I cannot understand what you are trying to say. Could you try again?";
				break;
			case "question":
				//handleQuestionIntent(inputArray.get(1));
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
		try {
			database.getFAQResponse(question);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void handleNoneIntent() {
		
		
	}

}
