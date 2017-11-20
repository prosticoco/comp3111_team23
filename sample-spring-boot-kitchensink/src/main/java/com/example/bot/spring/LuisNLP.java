package com.example.bot.spring;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;

import lombok.extern.slf4j.Slf4j;


public class LuisNLP implements LanguageProcessor {
	
	String[] validEntities = {"numberOfAdults", "numberOfChildren", "numberOfToddlers", "tourType", "builtin.encyclopedia.people.person", "builtin.datetimeV2.date", "builtin.age"};

	@Override
	public ArrayList<String> processInput(String sentence) {
		HttpClient httpclient = HttpClients.createDefault();
		String response = null;
		ArrayList<String> responses = new ArrayList<String>();	//This arraylist has a fixed structure. The first index is the intent and everything following that are it's entities
		
		try
        {
            // The ID of our LUIS app which can determine what the user is trying to say
            String AppId = "83da097a-db33-45cd-8e46-655cdfb2f8ea";
            // For microsoft azure services 
            String SubscriptionKey = "84a367e5543e40a187f34c47367786b8";

            URIBuilder builder = new URIBuilder("https://southeastasia.api.cognitive.microsoft.com/luis/v2.0/apps/" + AppId + "?");

            builder.setParameter("q", sentence);
            builder.setParameter("timezoneOffset", "8.0");
            builder.setParameter("verbose", "true");
            builder.setParameter("spellCheck", "false");
            builder.setParameter("staging", "false");

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", SubscriptionKey);

            HttpResponse httpResponse = httpclient.execute(request);
            HttpEntity entity = httpResponse.getEntity();


            if (entity != null) 
            {
            		response = EntityUtils.toString(entity);
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
		
		return parseJSON(response);
	}
	
	private ArrayList<String> parseJSON(String json){
		ArrayList<String> result = new ArrayList<String>();
		String entityType = null;
		String entityText = null;
		String concat = null;
		
		JSONObject obj = new JSONObject(json);
		result.add(obj.getJSONObject("topScoringIntent").getString("intent"));
		if(result.get(0).equals("None") || result.get(0).equals("positiveConfirmation") || result.get(0).equals("negativeConfirmation")) {
			result.add(obj.getString("query"));
		}
		else {
			JSONArray arr = obj.getJSONArray("entities");
			for (int i = 0; i < arr.length(); i++) {
				entityType = arr.getJSONObject(i).getString("type");
				entityText = arr.getJSONObject(i).getString("entity");
				if(validEntity(entityType)) {
					concat = entityType.concat(":" + entityText);
					result.add(concat);
				}
			}
		}
		return result;
	}
	
	private boolean validEntity(String entity) {
		if(Arrays.asList(validEntities).contains(entity)) {
			return true;
		}
		return false;
	}
}
