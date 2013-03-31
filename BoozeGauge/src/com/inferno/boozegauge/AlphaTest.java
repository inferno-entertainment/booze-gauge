package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class AlphaTest extends SuperActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alpha_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alpha_test, menu);
		return true;
	}
	
	public void endTest(View view) {
		setResult(0);
		finish();
	}

	@Override
	public void calculateScore(long raw) {
		// TODO Auto-generated method stub
		
	}
}
