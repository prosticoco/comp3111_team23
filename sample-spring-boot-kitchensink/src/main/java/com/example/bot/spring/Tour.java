package src.main.java.com.example.bot.spring;

import java.util.Date;

public class Tour {

	private String id;
	private Date date;
	private int price;
	private String tourGuide;
	private String tourGuideLineAcc;
	private String hotel;
	private int capacity;
	private int minCustomers;

	
	public Tour(String id, Date date, int price, String tourGuide,  String tourGuideLineAcc, String hotel, int capacity, int minCustomers){
		this.id = id;
		this.date = date;
		this.price = price;
		this.tourGuide = tourGuide;
		this.tourGuideLineAcc = tourGuideLineAcc;
		this.hotel = hotel;
		this.capacity = capacity;
		this.minCustomers = minCustomers;
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getPrice() {
		return price;
	}

	public String getTourGuide() {
		return tourGuide;
	}

	public String getTourGuideLineAcc() {
		return tourGuideLineAcc;
	}

	public String getHotel() {
		return hotel;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getMinCustomers() {
		return minCustomers;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
