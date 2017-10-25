package com.example.bot.spring;

public interface StorageEngine extends DataObserver{
	
	public String getFAQResponse(String quesion);
	
	public String getTourDetails(String identifier);

}
