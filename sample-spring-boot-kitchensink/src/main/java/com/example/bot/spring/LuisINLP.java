package com.example.bot.spring;

import java.util.ArrayList;

import org.json.JSONObject;

public class LuisINLP implements LanguageProcessor {

	@Override
	public String processInput(String sentence) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getIntent(JSONObject json){
		return json.getString("intent");
	}
	
	private ArrayList<String> getEntity(JSONObject json){
		
		return null;
	}
	
}
