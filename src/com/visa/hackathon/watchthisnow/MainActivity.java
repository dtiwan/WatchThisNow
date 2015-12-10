package com.visa.hackathon.watchthisnow;

import com.visa.hackathon.watchthisnow.listener.ButtonExerciseListener;
import com.visa.hackathon.watchthisnow.listener.ButtonRestListener;
import com.visa.hackathon.watchthisnow.runnable.SampleAccelerometer;

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
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private SensorManager sensorMan;
	private SampleAccelerometer meter;
	private Thread meterRunner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button exerciseBtn = (Button) findViewById(R.id.btn_exercise);
		exerciseBtn.setOnClickListener(new ButtonExerciseListener());
		
		Button btnRest = (Button) findViewById(R.id.btn_rest);
		btnRest.setOnClickListener(new ButtonRestListener());
		
		sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		Sensor sensor =  sensorMan.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
		meter = new SampleAccelerometer(getApplicationContext());
		
		if (sensor != null) {
			sensorMan.requestTriggerSensor(new TriggerEventListener() {

				@Override
				public void onTrigger(TriggerEvent event) {
					Log.d(this.getClass().getName(), "Received event " + event);
					TextView textView = (TextView) findViewById(R.id.text_hello_world);
					textView.setText(event.toString());
					if (meterRunner == null || !meterRunner.isAlive()) {
						meterRunner = new Thread(meter);
					}
					meterRunner.start();
				}
			}, sensor);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
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
