package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LogTest extends Activity {

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

}
