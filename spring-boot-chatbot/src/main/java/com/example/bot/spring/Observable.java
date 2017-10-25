package com.example.bot.spring;

public interface Observable {
	
	public void subscribe();
	
	public void unsubscribe();
	
	public void notifyObservers();

}
