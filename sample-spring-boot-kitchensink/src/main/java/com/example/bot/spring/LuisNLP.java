package src.main.java.com.example.bot.spring;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;

public class LuisNLP implements LanguageProcessor {

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
            String SubscriptionKey = "3e56f1252fc64579aca4198b5397ce83";

            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/" + AppId + "?");

            builder.setParameter("q", sentence);
            builder.setParameter("timezoneOffset", "0");
            builder.setParameter("verbose", "false");
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
		JSONArray arr = obj.getJSONArray("entities");
		for (int i = 0; i < arr.length(); i++) {
			entityType = arr.getJSONObject(i).getString("type");
			entityText = arr.getJSONObject(i).getString("entity");
			concat = entityType.concat(":" + entityText);
			result.add(concat);
		}
		
		return result;
	}
}
