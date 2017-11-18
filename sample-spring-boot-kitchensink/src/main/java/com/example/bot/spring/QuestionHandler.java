package com.example.bot.spring;

import java.util.ArrayList;

public class QuestionHandler implements EventHandler{

	public String handleEvent(ArrayList<String> inputArray) {
		String question = inputArray.get(0).toLowerCase();
		String answer = null;
		try {
			answer = new PSQLDatabaseEngine().getFAQResponse(question.substring(0,question.length() - 8));
		} catch (Exception e) {
			answer = "Excuse me, I do not have an answer for your question." +
						"We have sent it to our staff. Please wait for them to respond";
		}
		return answer;
	}

}
