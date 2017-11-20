package com.example.bot.spring;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test Cases for the TourBooking class
 * 
 * @author Adrien Prost
 *
 */


public class TourBookingTest {
		Customer customer = new Customer("Adrien Prost","666",53338027,23);
		Customer anotherCustomer = new Customer("John Smith","423",68402893,45);
	    Tour testTour = new Tour("123456",new Date(2017,8,26),100,"Pablo Fernandez","pablo23",
				"Namaste Backpackers",30,15);
		Tour anotherTour = new Tour("654321",new Date(2020,1,1),200,"Fernando Rodriguez","fernandro43","Best Western",25,12);
		TourBooking test = new TourBooking(testTour,customer);
	
	
	
	
	
	@Test
	public void adultsNumberTest() {
		test.setAdultsNumber(3);
		assertEquals("adultNumber getter and setter work fine",3,test.getAdultsNumber());
	}
	
	@Test
	public void childrensNumberTest() {
		test.setChildrenNumber(6);
		assertEquals("children number getter and setter work fine",6,test.getChildrenNumber());
	}
	
	@Test
	public void toddlerNumberTest() {
		test.setToddlersNumber(1);
		assertEquals("toddler number getter and setter work fine",1,test.getToddlersNumber());
	}
	
	@Test
	public void getTourFeeTest() {
		test.setAdultsNumber(2);
		test.setChildrenNumber(10);
		assertEquals("calcTourFee() returns the correct total fee",1000,test.getPrice());
	}
	
	@Test
	public void paidTest() {
		test.setPaid(1);
		assertEquals("paid getter and setter work well",1,test.getPaid());
	}
	
	@Test
	public void testTour() {
		assertEquals("getTour returns the correct Tour","Pablo Fernandez",test.getTour().getTourGuide());
		test.setTour(anotherTour);
		assertEquals("setTour works as expected","Fernando Rodriguez",test.getTour().getTourGuide());
	}
	
	@Test
	public void testCustomer() {
		assertEquals("getCustomer returns the correct Customer","666",test.getCustomer().getId());
		test.setCustomer(anotherCustomer);
		assertEquals("setCustomer correctly updates the customer","423",test.getCustomer().getId());
	}
	/*
	@Test
	public void testNullValues() {
		TourBooking nullTour = new TourBooking(null,null);
		assertEquals("NullValues returns a array of length 5 for no fields assigned",5,nullTour.nullValues().size());
		test.setAdultsNumber(1);
		assertEquals("NullValues returns an array of length 2 for only two unassigned fields",2,test.nullValues().size());
	}
	*/
	

}
