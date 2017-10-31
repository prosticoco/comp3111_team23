package src.main.java.com.example.bot.spring;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import test.NonNull;
import test.TextMessage;


@Slf4j
@LineMessageHandler
public class Controller {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
	private MessageHandler messageHandler;
	private LanguageProcessor languageProcessor;
	
	public Controller() {
		messageHandler = new MessageHandler();
		languageProcessor = new LuisINLP();
	}
	
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		String receivedMessage = event.getMessage().getText();
		
		//process the message
		String processedMessage = languageProcessor.processInput(receivedMessage);
		
		//get a response from the handler
		String response = messageHandler.handleTextContent(processedMessage);
		
		//send the message back to the user
		replyText(event.getReplyToken(), response);
	}

	private void replyText(@NonNull String replyToken, @NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "..";
		}
		this.reply(replyToken, new TextMessage(message));
	}
	
	private void reply(@NonNull String replyToken, @NonNull Message message) {
		reply(replyToken, Collections.singletonList(message));
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		try {
			BotApiResponse apiResponse = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}