package com.inferno.boozegauge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.PopupWindow;

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
	public void onResume() {
		super.onResume();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("ZOMG TEST BOX").setTitle("TEST BOX TITLE");
		builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}
		);
		AlertDialog dialog = builder.create();
		dialog.show();
		
		
		
		
		
	}
	
	public void calculateScore() {
		Globals.score += time;
		///endTest(null);
	}
}
