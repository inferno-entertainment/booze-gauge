package com.inferno.boozegauge;

import android.app.Activity;
import android.view.View;

public abstract class SuperActivity extends Activity {
	
	public abstract void calculateScore(long raw);
	
	public void endTest(View view) {
		setResult(0);
		finish();
	}
}
