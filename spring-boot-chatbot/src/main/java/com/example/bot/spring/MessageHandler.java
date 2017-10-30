package src.main.java.com.example.bot.spring;


@Slf4j
public class MessageHandler {
		
	public MessageHandler() {

	}
	
	
	private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
        String text = content.getText();
        log.info("Got text message from {}: {}", replyToken, text);
        
        //Check if it is a FAQ quesiton
        //Get response for the FAQ
        
        //If not a FAQ question search the database for answer
        //Send response back to the user
        
		//notify the database for a new entry to be added
        //Send an excusing message to the user
        

    }
	
	private void sendtoEmployee(String question){

		//push notification to the employee that a new question arised
	}
}
