package com.inferno.boozegauge;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ProgressBar;

public class BalTest extends Activity {

	private ProgressBar progressBar;
	private long startTime = System.currentTimeMillis();
	private Runnable updateProgressBar;
	private Timer timer;

	private final static int BALANCE_TIME = 10000; //millis
	private final static int MAX_PROGRESS = 512;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bal_test);
		progressBar = (ProgressBar) findViewById(R.id.balance_progress_bar);
	}

	@Override
	protected void onResume() {
		super.onResume();
		startTime = System.currentTimeMillis();
		timer = new Timer();
		progressBar.setMax(MAX_PROGRESS);
		progressBar.setProgress(0);
		updateProgressBar = new Runnable() {
			public void run() {
				int progress = (int) ((System.currentTimeMillis() - startTime) * 512 / BALANCE_TIME);
				progressBar.setProgress(progress);
				if (progress >= MAX_PROGRESS) {
					timer.cancel();
					endTest();
				}
			}
		};
		timer.schedule(new TimerTask() {
			public void run() {
				runOnUiThread(updateProgressBar);
			}
		}, 0L, 20L);
	}

	public void endTest() {
		setResult(0);
		finish();
	}
}
