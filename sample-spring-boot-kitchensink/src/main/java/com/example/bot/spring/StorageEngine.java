package com.example.bot.spring;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 * This interface sets the methods our classes that communicate with Databases will implement
 * @author Ivan Bardarov
 *
 */
public interface StorageEngine{
	/**
	 * This method will be used to fetch the answers to a FAQ from the database
	 * @param quesion the question we want to ask
	 * @return a String that serves as an answer to that question if it is stored in the database
	 * @throws Exception if the question is not present in the database
	 */
	public String getFAQResponse(String quesion) throws Exception;
	
	/**
	 * This method will serve to fetch the details of a specific tour that is stored in the database
	 * @param id the id of the tour
	 * @param date the date at which the tour will take place
	 * @return a instance of Tour with the information we need
	 * @throws Exception if the id/date of the tour do not correspond to any entries in the database
	 */
	public Tour getTourDetails(String id, Date date) throws Exception;
	
	/**
	 * This method will serve to get details about a specific customer
	 * @param identifier the String that identifies the customer
	 * @return a customer instance if the customer is stored in the database
	 * @throws Exception if the identifier is not present in the database
	 */
	public Customer getCustomerDetails(String identifier) throws Exception;
	
	/**
	 * this method will serve to get details about a tour (not according to the date)
	 * @param name the name of the tour
	 * @return a instance of GeneralTour 
	 * @throws Exception
	 */
	public GeneralTour getGeneralTourDetails(String name) throws Exception;
	
	public ArrayList<Date> getTourDates(String identifier) throws Exception;
	
	public ArrayList<String> getNotPaidCustomers(Date date) throws Exception;

	public void logQuestion(String question);
	
	public void addBooking(TourBooking tourBooking) throws URISyntaxException;
	
	public void addCustomer(Customer customer) throws URISyntaxException;
	
	public int getNumberBookedTours(Tour tour);

}
