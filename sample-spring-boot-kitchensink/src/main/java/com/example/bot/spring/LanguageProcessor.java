package com.example.bot.spring;

import java.util.ArrayList;

import java.util.ArrayList;
/**
 * interface for language processing 
 * @author Ivan Bardarov
 *
 */
public interface LanguageProcessor {
	/**
	 * method that will be called by an concrete language processor to process the message sent by the customer
	 * on the line app to an array of Strings
	 * @param sentence the String representing the message sent by the customer
	 * @return an arrayList of Strings representing the message in a way the handlers can understand it
	 */
	public ArrayList<String> processInput(String sentence);
	

}
