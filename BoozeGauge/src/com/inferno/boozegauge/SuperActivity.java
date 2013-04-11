package com.inferno.boozegauge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/*
 * SuperActivity is a template class.
 * Each of the game classes extends SuperActivity and implements the
 * calculateScore function
 */
public abstract class SuperActivity extends Activity {
	
	public abstract void calculateScore();
	public AlertDialog.Builder builder;
	
	//called when a game is complete
	public void endTest() {
		calculateScore();
		setResult(0);
		finish();
	}
	
	protected void onResume() {
		super.onResume();
		builder = new AlertDialog.Builder(this);
		builder.setTitle("Instructions");
		builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton("Back",  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO
			}
		});	
	}
}
