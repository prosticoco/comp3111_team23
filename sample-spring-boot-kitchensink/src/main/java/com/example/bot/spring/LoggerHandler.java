package com.example.bot.spring;

import java.util.ArrayList;
/**
 * Class that represents the handler that takes care of questions that aren't recognized as a booking or an enquiry about a tour. 
 * This handler will take care of logging these messages.
 * @author Ivan Bardarov
 *
 */
public class LoggerHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	/**
	 * this method simply takes the question from the customer and stores it in the database
	 * @return a message to be sent to the customer indicating that his/her message has been logged 
	 */
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		database.logQuestion(inputArray.get(1));
		return MessageHandler.DEFAULTANSWER;
	}

}
