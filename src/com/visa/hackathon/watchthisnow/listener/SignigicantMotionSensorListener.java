package com.visa.hackathon.watchthisnow.listener;

import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;

public class SignigicantMotionSensorListener extends TriggerEventListener {

	@Override
	public void onTrigger(TriggerEvent event) {
		Log.d(this.getClass().getName(), "Received an event" + event);
		
	}

}
