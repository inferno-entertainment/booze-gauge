package com.inferno.boozegauge;

import android.os.Bundle;
import android.view.Menu;

public class LogTest extends SuperActivity {
	private long time;
	
	
	public void collectScore(long t) {
		time = t;
		endTest();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_test);
		
		/*PopupWindow pw = new PopupWindow();
		pw.setContentView(R.layout.popup_instructions);*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_test, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
	}
	
	public void calculateScore() {
		Globals.score += time;
		///endTest(null);
	}
}
