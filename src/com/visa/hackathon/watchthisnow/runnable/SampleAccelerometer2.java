package com.visa.hackathon.watchthisnow.runnable;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;
import model.AccelerometerData;

public class SampleAccelerometer2 implements SensorEventListener{

	
	private List<AccelerometerData> activities = new ArrayList<AccelerometerData>();
	
	public List<AccelerometerData> getActivities(){
		return activities;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float[] mGravity;
		float mAccel;
		float mAccelCurrent;
		float mAccelLast;
		
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
		
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
	        mGravity = event.values.clone();
	        // Shake detection
	        float x = mGravity[0];
	        float y = mGravity[1];
	        float z = mGravity[2];
	        mAccelLast = mAccelCurrent;
	        mAccelCurrent = FloatMath.sqrt(x*x + y*y + z*z);
	        float delta = mAccelCurrent - mAccelLast;
	        mAccel = mAccel * 0.9f + delta;
	            // Make this higher or lower according to how much
	            // motion you want to detect
	        if(mAccel > 3){ 
	        	//Do something here.
	        	AccelerometerData datum = new AccelerometerData();
	        	datum.setTimeStamp(""+System.currentTimeMillis());
	        	datum.setData(mAccel);
	        	activities.add(datum);
	        }
	    }
	}
	
}
