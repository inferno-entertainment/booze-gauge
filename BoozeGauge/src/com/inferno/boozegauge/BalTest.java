package com.inferno.boozegauge;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ProgressBar;

public class BalTest extends SuperActivity implements SensorEventListener {

	private ProgressBar progressBar;
	private BalanceWidget balanceWidget;
	private long startTime = System.currentTimeMillis();
	private Runnable updateUi;
	private Timer timer;
	private float score;
	private float instantaneous_score;
	private Sensor accelerometer;
	private SensorManager sm;
	private float acceleration;

	// length of time for test
	private final static int BALANCE_TIME = 10000; //millis
	// full progress bar level
	private final static int MAX_PROGRESS = 512;
	/* TODO: configure weight for sensible scores */
	private final static float ACCELERATION_WEIGHT = 1000.0f;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bal_test);
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		progressBar = (ProgressBar) findViewById(R.id.balance_progress_bar);
		balanceWidget = (BalanceWidget) findViewById(R.id.balance_widget);
	}

	/** called when dialogue finished */
	public void startTest() {
		startTime = System.currentTimeMillis();
		timer = new Timer();
		progressBar.setMax(MAX_PROGRESS);
		progressBar.setProgress(0);
		score = 0;
		// this is called by the timer, must be run on UI thread
		updateUi = new Runnable() {
			public void run() {
				int progress = (int) ((System.currentTimeMillis() - startTime) * 512 / BALANCE_TIME);
				progressBar.setProgress(progress);
				if (progress >= MAX_PROGRESS) {
					timer.cancel();
					endTest();
				}
				balanceWidget.step(instantaneous_score);
			}
		};
		// Timer updates score and progress bar every 20ms
		timer.schedule(new TimerTask() {
			public void run() {
				instantaneous_score = acceleration;
				score += instantaneous_score;
				// UI changes have no effect on this thread
				runOnUiThread(updateUi);
			}
		}, 0L, 20L);
		sm.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		super.builder.setMessage("Hold the phone as flat and still as possible.");
		super.builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startTest();
			}
		});
		AlertDialog dialog = super.builder.create();
		dialog.show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// listening to the sensor can kill battery while paused
		sm.unregisterListener(this);
	}
	
	public void calculateScore() {
		// This function bounds our golf score (0,inf) to a non-golf score in (0,100)
		Globals.score += (1.0 - Math.exp(-ACCELERATION_WEIGHT/score)) * 100.0;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Ignore changes
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == accelerometer) {
			// Score is based on difference of sensor vs
			// expected direction of gravity. This
			// penalizes both holding the device unlevel
			// and any motion.
			double x = event.values[0],
			       y = event.values[1],
			       z = SensorManager.GRAVITY_EARTH - event.values[2];
			acceleration = (float) Math.sqrt(x * x + y * y + z * z);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		timer.cancel();
	}
}
