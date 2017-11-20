package com.example.bot.spring;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface StorageEngine{
	
	public String getFAQResponse(String quesion) throws Exception;
	
	public Tour getTourDetails(String id, Date date) throws Exception;
	
	public Customer getCustomerDetails(String identifier) throws Exception;
	
	public GeneralTour getGeneralTourDetails(String name) throws Exception;
	
	public ArrayList<Date> getTourDates(String identifier) throws Exception;
	
	public ArrayList<String> getNotPaidCustomers(Date date) throws Exception;

	public void logQuestion(String question);
	
	public void addBooking(TourBooking tourBooking) throws URISyntaxException;
	
	public void addCustomer(Customer customer) throws URISyntaxException;
	
	public int getNumberBookedTours(Tour tour);

}
