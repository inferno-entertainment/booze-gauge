package com.inferno.boozegauge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;

public class SpaceTest extends SuperActivity {
	private long time;
	
	private final static float TIME_WEIGHT = 10000.0f;  //weights the user's time in order to create a realistic pass/fail bound

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_space_test);		
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
		super.builder.setMessage(R.string.space_instructions);
		builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				time = System.currentTimeMillis();	//when the user presses start, the activity starts the completion timer			
			}
		});
		
		AlertDialog dialog = super.builder.create();
		dialog.show();
	}
	
	@Override
	protected void onPause() {
		long totalTime = System.currentTimeMillis() - time;
		Globals.score += (1.0 - Math.exp(-TIME_WEIGHT/totalTime)) * 100.0;  //converts the time to completion from a minimum score to a 0 - 100 score
		
		super.onPause();
	}
	
	public void calculateScore() {
		/*
		 * Due to a race condition that causes calculateScore to be called multiple times
		 * score calculation for this test has been moved to onPause, which should only be called once
		 */
	}
}
