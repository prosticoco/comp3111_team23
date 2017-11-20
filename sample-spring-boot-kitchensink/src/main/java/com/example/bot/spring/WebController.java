package com.example.bot.spring;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.bot.spring.PSQLDatabaseEngine;

import lombok.extern.slf4j.Slf4j;
/**
 * This class serves for receiving external calls from the web application
 * 
 * @author Christopher Lynch
 *
 */
@Slf4j
@RestController
public class WebController {
	
	private final String ERROR = "An error occured";
	private final String SUCCESS = "Operation executed successfully";
	
	private StorageEngine database = new PSQLDatabaseEngine();
<<<<<<< HEAD
	/**
	 * this method is called when an employee wants to cancel a booking via the web interface. 
	 * it will update the database accordingly
	 * @param tourId id of the tour
	 * @param date date of the tour
	 * @return
	 */
=======
	@Autowired
	private LineCommunicator lineCom;

>>>>>>> tests
	@RequestMapping("/cancelBooking")
	public String cancelBooking(@RequestParam(value="tourId", defaultValue="") String tourId,
			@RequestParam(value="date", defaultValue="") String date) {
		String answer = SUCCESS;
		log.info("Cancelling tour via the web application ------------------------------");
		try{
			ArrayList<String> customers = database.getBookedCustomers(tourId, new SimpleDateFormat("yyyy-MM-dd").parse(date));
			String message = "The tour on the " + date+" you have booked, has been cancelled.";
			database.removeTour(tourId, new SimpleDateFormat("yyyy-MM-dd").parse(date));
			//log.info(customers.get(0).toString());
			lineCom.pushCustomerNotification(new ArrayList<String>(Arrays.asList("U6c377e75e1d6c2b1f0805c82ebb880f9")), message);	
		}
		catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return answer;
	}
	/**
	 * This method is called when a employee wants to add a booking via the webserver.
	 * it's use is to fetch the parameters from the URL and file the booking into the database
	 * @param tourId the id of the tour
	 * @param customerId the customer's id
	 * @param numberOfAdults the number of adults in the booking
	 * @param numberOfChildren number of children in the booking
	 * @param numberOfTodders number of toddlers in the booking
	 * @param date the date of the tour
	 * @param name the name of the customer
	 * @param age the age of the customer (booker)
	 * @return returns an answer indicating if the operation was successful
	 */
	@RequestMapping("/addBooking")
	public String addBooking(@RequestParam(value="tourId", defaultValue="") String tourId,
			@RequestParam(value="customerId", defaultValue="") String customerId,
			@RequestParam(value="numberOfAdults", defaultValue="") String numberOfAdults,
			@RequestParam(value="numberOfChildren", defaultValue="") String numberOfChildren,
			@RequestParam(value="numberOfToddlers", defaultValue="") String numberOfTodders,
			@RequestParam(value="date", defaultValue="") String date,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="age", defaultValue="") String age) {
		log.info("Logging external booking from web application ------------------------------");
		
		String answer = SUCCESS;
		//This object will form the booking
		BookingHandler bookingHandler = new BookingHandler(customerId);
		
		//Entering customer values
		log.info("----Creating customer object----");
		Customer customer = new Customer(name, customerId, -1, Integer.parseInt(age));
		
		//Entering basic necessary tour booking values
		log.info("----Creating Tour object----");
		Tour tour = null;
		TourBooking tBooking = null;
		try {
			tour = new Tour(tourId, new SimpleDateFormat("yyyy-MM-dd").parse(date), 0, "", "", "", 0, 0);
			log.info("----Creating TourBooking object----");
			tBooking = new TourBooking(tour, customer);
			tBooking.setAdultsNumber(Integer.parseInt(numberOfAdults));
			tBooking.setChildrenNumber(Integer.parseInt(numberOfChildren));
			tBooking.setToddlersNumber(Integer.parseInt(numberOfTodders));
		}
		catch(Exception e) {
			answer = ERROR;
		}
		
		log.info("----Objects created... Adding to database----");
		
		//Adding booking to the database
		try {
			database.addBooking(tBooking);
		}
		catch(URISyntaxException e) {
			answer = ERROR;
		}
		
		return answer;
	}

}
