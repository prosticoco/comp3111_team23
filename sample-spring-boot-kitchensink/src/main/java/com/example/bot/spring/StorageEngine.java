package com.example.bot.spring;

import java.util.Date;

public interface StorageEngine{
	
	public String getFAQResponse(String quesion) throws Exception;
	
	public Tour getTourDetails(String id, Date date) throws Exception;
	
	public Customer getCustomerDetails(String identifier) throws Exception;
	
	public GeneralTour getGeneralTourDetails(String name) throws Exception;

	public void logQuestion(String question);
	
	public void addBooking(TourBooking tourBooking);
	
	public int getTourAvailability(Tour tour);
}
