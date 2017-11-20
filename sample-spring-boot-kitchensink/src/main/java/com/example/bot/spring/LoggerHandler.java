package com.example.bot.spring;

import java.util.ArrayList;
<<<<<<< HEAD
/**
 * Class that represents the handler that takes care of questions that aren't recognized as a booking or an enquiry about a tour. 
 * This handler will take care of logging these messages.
 * @author Ivan Bardarov
 *
 */
=======
import java.util.Arrays;

<<<<<<< HEAD
>>>>>>> tests
public class LoggerHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	/**
	 * this method simply takes the question from the customer and stores it in the database
	 * @return a message to be sent to the customer indicating that his/her message has been logged 
	 */
=======
import org.springframework.beans.factory.annotation.Autowired;

public class LoggerHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	@Autowired
	private LineCommunicator linCom;
>>>>>>> tests
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		database.logQuestion(inputArray.get(1));
		linCom.pushCustomerNotification(new ArrayList<String>(Arrays.asList("U7284687917ae6c74fdca2ba21f055e78")), "Someone is asking: ");
		return MessageHandler.DEFAULTANSWER;
	}

}
