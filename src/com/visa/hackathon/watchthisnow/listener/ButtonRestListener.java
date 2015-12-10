package com.visa.hackathon.watchthisnow.listener;

import com.visa.hackathon.watchthisnow.R;
import com.visa.hackathon.watchthisnow.rest.client.RestClient;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import model.Activity;

public class ButtonRestListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		StringBuilder result = new StringBuilder();
		try {
			RestCall restCall = (RestCall) new RestCall().execute("");
			result.append(restCall.get());
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Exception", e);
			result = result.append(e.getStackTrace());
		}
        
		TextView txtHelloWorld = (TextView) ((View)v.getParent()).findViewById(R.id.text_log);
		txtHelloWorld.setText(result);
	}
	
	private class RestCall extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			Activity activity = new Activity();
			activity.setStartTimeStamp(System.currentTimeMillis() + "");
			activity.setEndTimeStamp(System.currentTimeMillis()+ 1000+"");
			return new RestClient().sendActivity(activity);
		}
		
	}

}
