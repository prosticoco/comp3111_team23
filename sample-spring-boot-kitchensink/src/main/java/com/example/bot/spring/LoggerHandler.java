package com.example.bot.spring;

import java.util.ArrayList;

public class LoggerHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		database.logQuestion(inputArray.get(1));
		return MessageHandler.DEFAULTANSWER;
	}

}
