package com.example.bot.spring;



public class MessageHandler {
		
	private StorageEngine database;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
	}
	
	
	public String handleTextContent(String text) throws Exception {

		return database.getCustomerDetails("A124").getName();      

    }
	
	private void sendtoEmployee(String question){

		//push notification to the employee that a new question arised
	}
}
