package com.example.bot.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.model.PushMessage;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author Oliver Thomsen
 *
 */
@Slf4j
public class LineCommunicator implements Communicator{
	
	@Autowired
	private LineMessagingClient lineMessagingClient;

	@Override
	public void replyText(@NonNull String replyToken, @NonNull String message) {
		// TODO Auto-generated method stub
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "..";
		}
		this.reply(replyToken, new TextMessage(message));
	}

	@Override
	public void pushCustomerNotification(ArrayList<String> recepients, String message) {
		if (recepients.isEmpty()) {
			throw new IllegalArgumentException("the message should have recepients");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "..";
		}
		for(String userId:recepients){
			push(userId, Collections.singletonList(new TextMessage(message)));
		}
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
	
	private void push(@NonNull String userId, @NonNull List<Message> messages ) {
		try {
			BotApiResponse apiResponse = lineMessagingClient.pushMessage(new PushMessage(userId, messages)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	
}
