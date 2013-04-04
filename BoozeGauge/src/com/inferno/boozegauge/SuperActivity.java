package com.inferno.boozegauge;

import android.app.Activity;

/*
 * SuperActivity is a template class.
 * Each of the game classes extends SuperActivity and implements the
 * calculateScore function
 */
public abstract class SuperActivity extends Activity {
	
	public abstract void calculateScore();
	
	//called when a game is complete
	public void endTest() {
		calculateScore();
		setResult(0);
		finish();
	}
}
