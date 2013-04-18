package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_screen);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		int tmpScore = Globals.score;
		if(Globals.playAll)
			tmpScore /= 4;
		((TextView)findViewById(R.id.score_text)).setText(Integer.toString(tmpScore));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_screen, menu);
		return true;
	}
	
	public void endTest(View view) {
		setResult(0);
		finish();
	}

}
