package src.main.java.com.example.bot.spring;


@Slf4j
public class MessageHandler {
		
	private StorageEngine database;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
	}
	
	
	public String handleTextContent(String text) throws Exception {
        log.info("Got text message: {}", text);
        
        try{
        	database.getFAQResponse(text);
        }
        catch(Exception e){
        	// the entry does not exist
        }
        
        
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
