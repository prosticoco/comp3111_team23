package com.example.bot.spring;
import java.util.ArrayList;
/**
 * the interface diverse event handlers will use
 * @author Ivan Bardarov
 *
 */
public interface EventHandler {
	/**
	 * Method that handles a message processed by LUIS and provides a bot answer to it
	 * depending on the handler that is chosen according to the intent of the message
	 * @param inputArray the customer's message preprocessed by LUIS
	 * @return a string representing the bot's answer to that message
	 */
	public String handleEvent(ArrayList<String> inputArray);
	
}
