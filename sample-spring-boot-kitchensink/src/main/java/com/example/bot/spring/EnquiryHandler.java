package com.example.bot.spring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnquiryHandler implements EventHandler {

	private StorageEngine database = new PSQLDatabaseEngine();
	
	@Override
	public String handleEvent(ArrayList<String> inputArray) {
		System.out.println("I entered here");
		String intent = inputArray.get(0).toLowerCase();
		intent = intent.substring(0,intent.length() - 7);
		if(inputArray.size()>1){
			String[] atr1 = inputArray.get(1).toLowerCase().split(":");
			switch(intent){
				case "tourid":
					if(!atr1[0].equals("tourtype")) break;
					return enquireTourId(atr1[1]);
				case "dates":
					if(!atr1[0].equals("tourtype")) break;
					return enquireDates(atr1[1]);
				case "capacity":
					if(inputArray.size()>2){
						String[] atr2 = inputArray.get(2).toLowerCase().split(":");
						if(!atr1[0].equals("tourtype")  && !atr2[0].equals("builtin.datetimeV2.date")){
							if(!atr2[0].equals("tourtype")  && !atr1[0].equals("builtin.datetimeV2.date"))
								break;
							else
								return enquireCapacity(atr2[1], atr1[1]);
						}
						return enquireCapacity(atr1[1], atr2[1]);
					}
			}
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
