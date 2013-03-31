package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class LogTest extends SuperActivity {
	private long time;
	
	public void collectScore(long t) {
		time = t;
		endTest(null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_test, menu);
		return true;
	}
	
	public void calculateScore() {
		Globals.score += time;
		///endTest(null);
	}
}
