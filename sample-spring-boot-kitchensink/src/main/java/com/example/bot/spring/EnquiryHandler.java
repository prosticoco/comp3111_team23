package com.example.bot.spring;

import java.util.ArrayList;

public class EnquiryHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		switch(inputArray.get(1).toLowerCase()){
			case "tourid":
				return enquireTourId();
			case "date":
				return enquireDate();
			case "capacity":
				return enquireCapacity();
		}
		return MessageHandler.ERROR;
	}

	private String enquireCapacity() {
		// TODO Auto-generated method stub
		return null;
	}

	private String enquireDate() {
		// TODO Auto-generated method stub
		return null;
	}

	private String enquireTourId() {
		// TODO Auto-generated method stub
		return null;
	}

}
