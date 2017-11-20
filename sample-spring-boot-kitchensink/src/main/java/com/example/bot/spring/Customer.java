package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
/**
 * Class to represent a customer of the travel agency
 * 
 * @author Ivan Bardarov
 * 
 *
 */
public class Customer {
	// attributes that define the customer
	private String name;
	// the id is unique 
	private String id;
	private int phone;
	private int age;
	
	public Customer(String name, String id, int phone, int age) {
		this.name = name;
		this.id = id;
		this.phone = phone;
		this.age = age;
	}
	/**
	 * getter for the phone number
	 * @return the phone number of the customer as an integer
	 */
	public int getPhone() {
		return phone;
	}
	/**
	 * Sets a new phone number for the customer
	 * @param phone the new phone number to assign
	 */
	public void setPhone(int phone) {
		this.phone = phone;
	}
	/**
	 * Method to return the age of the customer
	 * @return the age of the customer as an int
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * method to set a new age for the customer
	 * @param age the new age as an int we want to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
    /**
     * method to get the name of the customer
     * @return the name of the customer as a String
     */
	public String getName() {
		return name;
	}
	/**
	 * Method to set a new name for the customer
	 * @param name the new name we want to set as a String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Method to get the id of the customer (as an int)
	 * @return the unique id of the customer
	 */
	public String getId() {
		return id;
	}
	/**
	 * Method to set a new id for the customer
	 * @param id the new id we want to assign.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * This method looks at what attributes are "uninitiated" in the class. i.e are null or have 0 value
	 * it will then return an array containing messages to be sent to the customer so that he can send back the values of the attributes.
	 * For example, if the name is null, then the arrayList will contain a message asking the customer to input his name.
	 * @return array of messages useful for the customer
	 */
	public ArrayList<String> nullValues(){
		ArrayList<String> nullValues = new ArrayList<>();
		if(name == null){
			nullValues.add("name: <your name>");
		}
		if(age == 0){
			nullValues.add("age: <your age> years old");
		}
		return nullValues;
	}
}
