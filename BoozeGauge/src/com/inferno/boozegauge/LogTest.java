package com.inferno.boozegauge;

import android.content.Context;
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
		LayoutInflater inflater = (LayoutInflater)
				this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		PopupWindow pw = new PopupWindow(
				inflater.inflate(R.layout.popup_instructions, null, false), 
				100, 
				100, 
				true);
		pw.showAtLocation(this.findViewById(R.id.logTestView), Gravity.CENTER, 0, 0);
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
