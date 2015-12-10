package com.visa.hackathon.watchthisnow.runnable;

import java.util.ArrayList;
import java.util.List;

import com.visa.hackathon.watchthisnow.rest.client.RestClient;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import model.AccelerometerData;
import model.Activity;

public class SampleAccelerometer implements Runnable{

	private SensorManager sensorMan;
	private Sensor accelerometer;

	
	private Context mContext;
	
	private RestClient restClient = new RestClient();
	
	public SampleAccelerometer(Context context) {
		this.mContext = context;
	}
	
	@Override
	public void run() {
		sensorMan = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		SampleAccelerometer2 meter = new SampleAccelerometer2();
		sensorMan.registerListener(meter, accelerometer, SensorManager.SENSOR_DELAY_UI);
		
		while(true){
			try {
				//TODO if accelerometer gets triggered what happens to sleep?
				Thread.sleep(10000);//Sleep for 10 seconds
			} catch (InterruptedException e) {
				Log.d(this.getClass().getName(), "Got interrupted["+e+"]");
			}
			List<AccelerometerData> activities = meter.getActivities();
			meter.setActivities(new ArrayList<AccelerometerData>());
			
			Log.d(this.getClass().getName(), "Got activiteis ["+activities+"]");
			if((activities.isEmpty())){
				Log.d(this.getClass().getName(), "Got size ["+activities.size()+"] ");
				//No activities happened in last 10 seconds.
				Activity activity = new Activity();
				activity.setStartTimeStamp(""+System.currentTimeMillis());
				activity.setEndTimeStamp(""+System.currentTimeMillis());
				activity.setActiveStatus("inactive");
				String restResponse = restClient.sendActivity(activity);
				Log.d(this.getClass().getName(), "Got response ["+restResponse+"]");
				break;
			}
			Activity activity = new Activity();
			activity.setStartTimeStamp(activities.get(0).getTimeStamp());
			activity.setEndTimeStamp(activities.get(activities.size() - 1).getTimeStamp());
			activity.setActiveStatus("active");
			activity.setData(activities);
			String restResponse = restClient.sendActivity(activity);
			Log.d(this.getClass().getName(), "Got response [" + restResponse + "]");
		}
	}
	
}
