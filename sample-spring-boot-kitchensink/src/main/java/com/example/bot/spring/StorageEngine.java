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
	 * @throws Exception in case name of the tour is not in the database
	 */
	public GeneralTour getGeneralTourDetails(String name) throws Exception;
	
	/**
	 * this method will serve to get the list of dates for a tour specified by its id
	 * @param identifier the id of the tour
	 * @return an array of Dates
	 * @throws Exception if the tour is not in the DB
	 */
	public ArrayList<Date> getTourDates(String identifier) throws Exception;
	
	/**
	 * this method will serve to get a list of the customers who have not paid for tours on 
	 * a specific date
	 * @param date the date
	 * @return an array of ids of customer who still have to pay
	 * @throws Exception if there is no tour for the specified date
	 */
	public ArrayList<String> getNotPaidCustomers(Date date) throws Exception;
	
	/**
	 * This method will serve to log the questions of the users in the DB
	 * @param question the question the user asked
	 */
	public void logQuestion(String question);
	
	/**
	 * this method will add a booking made by a customer in the DB
	 * @param tourBooking the booking
	 * @throws URISyntaxException if the fields are in an incorrect format
	 */
	public void addBooking(TourBooking tourBooking) throws URISyntaxException;
	
	/**
	 * This method will add a new customer to the DB
	 * @param customer the customer we want to add
	 * @throws URISyntaxException if the fields are in an incorrect format
	 */
	public void addCustomer(Customer customer) throws URISyntaxException;
	
	/**
	 * this method will return the number of bookings made for a tour
	 * @param tour the tour we want to know the number of bookings for
	 * @return the number of bookings made
	 */
	public int getNumberBookedTours(Tour tour);

	ArrayList<String> getBookedCustomers(String id, Date date) throws Exception;

}
