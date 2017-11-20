package com.example.bot.spring;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.bot.spring.PSQLDatabaseEngine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class WebController {
	
	private final String ERROR = "An error occured";
	private final String SUCCESS = "Operation executed successfully";
	
	private StorageEngine database = new PSQLDatabaseEngine();

	@RequestMapping("/cancelBooking")
	public String cancelBooking(@RequestParam(value="tourId", defaultValue="") String tourId,
			@RequestParam(value="date", defaultValue="") String date) {
		String answer = SUCCESS;
		log.info("Cancelling tour via the web application ------------------------------");
		
		
		
		return answer;
	}
	
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
