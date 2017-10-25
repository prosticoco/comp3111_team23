package com.example.bot.spring;

public class Customer {
	
	private String name;
	private String id;
	private CustomerType type;
	
	public Customer(String name, String id, CustomerType type){
		this.name = name;
		this.id = id;
		this.type = type;
	}

}
