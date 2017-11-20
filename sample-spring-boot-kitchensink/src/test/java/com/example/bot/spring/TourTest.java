package com.example.bot.spring;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * Test case for the Tour class
 * 
 * @author Adrien Prost
 *
 */
public class TourTest {
	Tour testTour = new Tour("123456",new Date(2017,8,26),100,"Pablo Fernandez","pablo23",
			"Namaste Backpackers",30,15);
	Tour anotherTour = new Tour("654321",new Date(2020,1,1),200,"Fernando Rodriguez","fernandro43","Best Western",25,12);

	@Test
	public void idTest() {
		assertEquals("getId returns the correct Id","123456",testTour.getId());
		anotherTour.setId("123456");
		assertEquals("setId correctly updates the tour's id","123456",anotherTour.getId());
	}
	
	@Test
	public void dateTest() {
		assertEquals("getDate returns the correct date",2017,testTour.getDate().getYear());
		testTour.setDate(new Date(2018,8,9));
		assertEquals("setDate correctly updates the tour's date",2018,testTour.getDate().getYear());
	}

	@Test
	public void getPricetest() {
		assertEquals("getPrice returns the correct price",200,anotherTour.getPrice());
	}

	@Test
	public void getHoteltest() {
		assertEquals("getHotel returns the correct hotel name","Best Western",anotherTour.getHotel());
	}

	@Test
	public void getCapacityTest() {
		assertEquals("getCapacity returns the correct capacity",30,testTour.getCapacity());
	}

	@Test
	public void getMinTest() {
		assertEquals("getMinCustomer returns the correct minimum amount of attendance",12,anotherTour.getMinCustomers());
	}
	
	public void guideTest(){
		assertEquals("getTourGuide returns the correct name of the tour guide","Fernando Rodriguez",anotherTour.getTourGuide());
	}


}
