package src.main.java.com.example.bot.spring;

import java.util.Arrays;

@Slf4j
@LineMessageHandler
public class Controller {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	private MessageHandler messageHandler;
	
	public Controller() {
		
	}
	
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		TextMessageContent message = event.getMessage();
		
		messageHandler.handleTextContent(event.getReplyToken(), event, message);
	}

	
}