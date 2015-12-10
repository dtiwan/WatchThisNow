package com.visa.hackathon.watchthisnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SensorActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_tracker);
		
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor stepDetector =  sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

		sensorManager.requestTriggerSensor(new TriggerEventListener() {
			private int stepCounts = 0;
			@Override
			public void onTrigger(TriggerEvent event) {
				Log.d(this.getClass().getName(), "Received event " + event);
				
				TextView sensorDisplay  = (TextView) findViewById(R.id.sensorDisplay);
				CharSequence currentText = sensorDisplay.getText();
				if(currentText == null){
					currentText = "" + stepCounts++;
				} else {
					currentText = currentText + "\n" + stepCounts++;
				}
				sensorDisplay.setText(currentText);
			}
		}, stepDetector);
		/*Sensor sensor =  sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
		
		sensorManager.requestTriggerSensor(new TriggerEventListener() {
			
			@Override
			public void onTrigger(TriggerEvent event) {
				Log.d(this.getClass().getName(), "Received event " + event);
				TextView sensorDisplay  = (TextView) findViewById(R.id.sensorDisplay);
				sensorDisplay.setText(event.toString());
			}
		}, sensor);*/
		
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_home:
			break;
		case R.id.menu_sensor:
			final Context context = this;
			Intent intent = new Intent(context, SensorActivity.class);
            startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
