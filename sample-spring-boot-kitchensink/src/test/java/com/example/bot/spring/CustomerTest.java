package com.example.bot.spring;

import junit.framework.TestCase;
import org.junit.*;

/**
 * The Test Case for the Customer class
 * 
 * @author Adrien Prost
 * @version 1.0
 * @since 17.11.2017
 */
public class CustomerTest extends TestCase {
	// two test subjects
	Customer test = new Customer("Ivan Lynch","6756",53338028,21);
	Customer testNull = new Customer((String)null,(String)null,0,0);
	// we simply test that the class methods return the correct values
	@Test
	public void getPhoneTest(){
		assertEquals("Returns the correct phone number",53338028,test.getPhone());
	}
	
	@Test 
	public void setPhoneTest(){
		test.setPhone(53338029);
		assertEquals("changing phone number works",53338029,test.getPhone());
	}
	
	@Test
	public void getAgeTest(){
		assertEquals("getAge returns the correct age",21,test.getAge());
	}
	
	@Test
	public void setAgeTest(){
		test.setAge(45);
		assertEquals("setAge returns the correct age",45,test.getAge());
	}
	
	@Test
	public void getNameTest(){
		assertEquals("getName returns the correct name","Ivan Lynch",test.getName());
	}
	@Test
	public void setNameTest(){
		test.setName("Kevin Wang");
		assertEquals("setName correctly changes the name","Kevin Wang",test.getName());
	}
	@Test
	public void getIdTest(){
		assertEquals("getId returns the correct Id","6756",test.getId());
	}
	@Test
	public void setIdTest(){
		test.setId("420");
		assertEquals("setId sets corectly a new Id","420",test.getId());
	}
	@Test
	public void nullValuesTest(){
		assertEquals("the nullValues array has a correct length for a null Customer",4,testNull.nullValues().size());
		assertEquals("The nullvalues array has a correct length for a 'full' customer",0,test.nullValues().size());
	}

}
