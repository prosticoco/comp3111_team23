package com.example.bot.spring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnquiryHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		String[] arr = inputArray.get(1).toLowerCase().split(":");
		switch(arr[0]){
			case "tourid":
				return enquireTourId(arr[1]);
			case "dates":
				return enquireDates(arr[1]);
			case "capacity":
				return enquireCapacity(arr[1], arr[2]);
		}
		return MessageHandler.ERROR;
	}

	private String enquireCapacity(String tourName, String date) {
		Tour gt = null;
		try {
			gt  = database.getTourDetails(tourName, new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (Exception e) {
			return MessageHandler.ERROR;
		}
		return "The capacity of the requested tour is: "+ gt.getCapacity();
	}

	private String enquireDates(String tourName) {
		ArrayList<Date> dates;
		try {
			String tourId = database.getGeneralTourDetails(tourName).getId();
			dates = database.getTourDates(tourId);
		} catch (Exception e) {
			return "I am sorry, there is no avaiable dates for this tour yet";
		}
		
		String answer = "The available dates for the required tour is/are: ";
		for(Date d: dates){
			answer = answer + d.toString() +" ";
		}
		return answer;
		
	}

	private String enquireTourId(String tourName) {
		GeneralTour gt = null;
		try {
			gt  = database.getGeneralTourDetails(tourName);
		} catch (Exception e) {
			return MessageHandler.ERROR;
		}
		return "The id of the tour you are looking for is "+gt.getId();
	}
	
}
