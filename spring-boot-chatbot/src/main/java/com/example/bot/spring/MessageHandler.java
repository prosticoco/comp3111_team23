package com.example.bot.spring;

import java.util.Arrays;

import KitchenSinkController.ProfileGetter;

@Slf4j
@LineMessageHandler
public class MessageHandler {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	private DatabaseEngine database;
	
	public MessageHandler() {
		database = new SQLDatabaseEngine();
	}
	
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}

	private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
        String text = content.getText();
        log.info("Got text message from {}: {}", replyToken, text);
        //Check if it is a FAQ quesiton
        //Get response for the FAQ
        
        //If not a FAQ question search the database for answer
        //Send response back to the user
        
        //If no response add the message to database
        //Send notification to the employee
        //Send an excusing message to the user
        
        try {
        	database.searchFAQ(text);
		} catch (Exception e) {
			sendToEmployee(text);
		}
    }
	
	private void sendtoEmployee(String question){
		//add the entry to the database for later retrieval
		database.addEntry(question);
		//push notification to the employee that a new question arised
	}
}
