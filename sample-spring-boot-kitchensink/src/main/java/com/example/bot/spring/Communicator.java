package src.main.java.com.example.bot.spring;

import java.util.ArrayList;


/**
 * @author Oliver Thomsen
 */

public interface Communicator {
	
	public void replyText(String replyToken, String message);
	public void pushCustomerNotification(ArrayList<String> recepients, String message);	
	
}
