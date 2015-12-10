package com.visa.hackathon.watchthisnow.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import model.AccelerometerData;
import model.Activity;

public class RestClient {
	
	private String destination = "https://game-chaat.appspot.com/activity/postActivity.json";
	private DefaultHttpClient client = new DefaultHttpClient();
	private HttpPost post = new HttpPost(destination);
	
	public String sendActivity(Activity activity) {
		StringBuilder output= new StringBuilder();
		JSONObject json = new JSONObject();
		try {
			json.put("startTimeStamp", activity.getStartTimeStamp());
		} catch (JSONException e) {
			Log.e(this.getClass().getName(), "Found error in starTimeStamp " + e);
		}
		try {
			json.put("endTimeStamp", activity.getEndTimeStamp());
		} catch (JSONException e) {
			Log.e(this.getClass().getName(), "Found error in endTimeStamp " + e);
		}

		try {
			json.put("activeStatus", activity.getActiveStatus());
		} catch (JSONException e) {
			Log.e(this.getClass().getName(), "Found error in activeStatus " + e);
		}
		try {
			if (activity.getData() != null) {
				JSONArray array = new JSONArray();
				for (AccelerometerData accelerometerData : activity.getData()) {
					JSONObject obj = new JSONObject();
					obj.put("timeStamp", accelerometerData.getTimeStamp());
					obj.put("data", accelerometerData.getData());
					array.put(obj);
				}
				json.put("data", array);
			}
		} catch (JSONException e) {
			Log.e(this.getClass().getName(), "Found error in data " + e);
		}
		StringEntity entity;
		try {
			entity = new StringEntity(json.toString());

			entity.setContentType("application/json");
			post.setEntity(entity);

			HttpResponse response = client.execute(post);

			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

			Log.d(this.getClass().getName(), "Output from Server .... \n");
			String result;
			while ((result = br.readLine()) != null) {
				Log.d(this.getClass().getName(), output.toString());
				output.append(result);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(this.getClass().getName(), "" + e);
			output.append("" + e.getMessage());
		} catch (ClientProtocolException e) {
			Log.e(this.getClass().getName(), "" + e);
			output.append("" + e.getMessage());
		} catch (IOException e) {
			Log.e(this.getClass().getName(), "" + e);
			output.append("" + e.getMessage());
		} 
		return output.toString();
	}

}
