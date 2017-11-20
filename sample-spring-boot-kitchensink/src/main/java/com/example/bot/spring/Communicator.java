package com.example.bot.spring;

import java.util.ArrayList;


/**
 * Interface for a communicator
 * @author Oliver Thomsen
 */

public interface Communicator {
	/**
	 * This method is used to send a reply to the user who has sent a message to the bot
	 * @param replyToken the token needed to reply
	 * @param message the message we want to send as a reply
	 */
	public void replyText(String replyToken, String message);
	
	/**
	 * This method is used to send a notification to a list of users of the line app identified by their id
	 * @param recepients the list of user id of the line app
	 * @param message the message we want to send as a notification
	 */
	public void pushCustomerNotification(ArrayList<String> recepients, String message);	
	
}
