package com.example.dota2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Descarga y parse un JSON 
 * @author David García
 * @author Daniel Serrano
 */
public class JSONParser {
	static InputStream iStream = null; 
	static JSONArray jarray = null; 
	static String json = "";
	
	public JSONParser() {
	}

	public JSONArray getJSONFromUrl(String url) { 
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient(); 
		HttpGet httpGet = new HttpGet(url); 
		try { 
			HttpResponse response = client.execute(httpGet); 
			StatusLine statusLine = response.getStatusLine(); 
			int statusCode = statusLine.getStatusCode(); 
			if (statusCode == 200) { 
				HttpEntity entity = response.getEntity(); 
				InputStream content = entity.getContent(); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line; 
				while ((line = reader.readLine()) != null) { 
					builder.append(line); 
				} 
			} 
			else { 
				Log.e("==>", "Failed to download file"); } 
			} catch (ClientProtocolException e) {e.printStackTrace(); } 
				catch (IOException e) { e.printStackTrace(); } 
		// Parse String to JSON object 
		try { 
			jarray = new JSONArray( builder.toString()); 
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString()); } 
		// return JSON Object 
		return jarray;
		}
	
	public JSONObject getJSONObjectFromUrl(String url)
	{
		JSONObject jsonResponse=null;
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient(); 
		HttpGet httpGet = new HttpGet(url); 
		try { 
			HttpResponse response = client.execute(httpGet); 
			StatusLine statusLine = response.getStatusLine(); 
			int statusCode = statusLine.getStatusCode(); 
			if (statusCode == 200) { 
				HttpEntity entity = response.getEntity(); 
				InputStream content = entity.getContent(); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line; 
				while ((line = reader.readLine()) != null) { 
					builder.append(line); 
				} 
			} 
			else { 
				Log.e("==>", "Failed to download file"); } 
			} catch (ClientProtocolException e) {e.printStackTrace(); } 
				catch (IOException e) { e.printStackTrace(); } 
		// Parse String to JSON object 
		try { 
			jsonResponse = new JSONObject( builder.toString()); 
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString()); } 
		
		return jsonResponse;
	}
		

	
	
}
