package com.example.bot.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Customer {
	
	private String name;
	private String id;
	private int phone = 4;
	private int age;
	
	public Customer(String name, String id, int phone, int age) {
		this.name = name;
		this.id = id;
		this.phone = 4;
		this.age = age;
	}
	
	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> nullValues(){
		ArrayList<String> nullValues = new ArrayList<>();
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field f : fields){
			f.setAccessible(true);
			try {
				if(!f.getType().isPrimitive()){
					if(f.get(this) == null) nullValues.add(f.getName());
				}else{
					if(f.get(this).equals(0)) nullValues.add(f.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nullValues;
	}
}
