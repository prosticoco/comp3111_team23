package com.example.bot.spring;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * @author Adrien Prost
 *
 */
public class GeneralTourTest {
	
	GeneralTour test = new GeneralTour("143","Test Tour","This is the description of the test tour, this tour" +
			" is almost completely useless","every day of the week");

	@Test
	public void getIdTest() {
		assertEquals("getId returns the correct Id","143",test.getId());
	}
	
	@Test
	public void getNameTest() {
		assertEquals("getName returns the correct name","Test Tour", test.getName());
	}
	
	@Test
	public void getDescriptionTest() {
		assertEquals("getDescription returns the correct description","This is the description of the test tour, this tour" +
			" is almost completely useless",test.getDescription());
	}
	
	@Test
	public void getDaysTest() {
		assertEquals("getDays returns the correct String","every day of the week",test.getDays());
	}
}
