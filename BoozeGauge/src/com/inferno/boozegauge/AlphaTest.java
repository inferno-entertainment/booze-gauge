package com.inferno.boozegauge;

import java.util.Random;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AlphaTest extends SuperActivity implements OnClickListener {
	private static Button buttonUL;
	private static Button buttonUR;
	private static Button buttonCL;
	private static Button buttonCR;
	private static Button buttonLL;
	private static Button buttonLR;
	
	Random generator = new Random();
	private int currentScore;
	private int correctButton;
	private int currentPosition;
	
	
	private Button[] appButtons;
	private char[] revAlphabet = {'Z','Y','X','W','V','U','T','S','R','Q','P','O','N','M','L','K','J','I','H','G','F','E','D','C','B','A'};
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alpha_test);
		currentScore = 0;
		currentPosition = 0;
		buttonUL = (Button)findViewById(R.id.alphaButtonUL);
		buttonUR = (Button)findViewById(R.id.alphaButtonUR);
		buttonCL = (Button)findViewById(R.id.alphaButtonCL);
		buttonCR = (Button)findViewById(R.id.alphaButtonCR);
		buttonLL = (Button)findViewById(R.id.alphaButtonLL);
		buttonLR = (Button)findViewById(R.id.alphaButtonLR);
		appButtons = new Button[] {buttonUL, buttonUR, buttonCL, buttonCR, buttonLL, buttonLR};
		for(Button b: appButtons) {
			b.setOnClickListener(this);
		}
		shuffleButtons();
		
	}
	
	protected void onResume() {
		super.onResume();
		
		
	}
	
	private void shuffleButtons() {
		int randpos = currentPosition;
		correctButton = generator.nextInt(appButtons.length);
		appButtons[correctButton].setText(Character.toString(revAlphabet[currentPosition]));
		
		for (Button b : appButtons) {
			randpos = currentPosition;			
			if(b != appButtons[correctButton]) {
				while(randpos == currentPosition)
					randpos = generator.nextInt(revAlphabet.length);
				b.setText(Character.toString(revAlphabet[randpos]));
			}
				
		}
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alpha_test, menu);
		return true;
	}*/
	
	
	@Override
	public void calculateScore() {
		Globals.score += currentScore;		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}		
		return true;
	}

	@Override
	public void onClick(final View v) {
		new Thread (new Runnable() {
			public void run() {
				if((Button)v == appButtons[correctButton]) {
					v.post(new Runnable() {
						public void run() {
							((Button) v).setText("Correct!");
						}
					});
					android.os.SystemClock.sleep(500);
					currentScore++;
					if(currentPosition != 25) {
						currentPosition++;					
						runOnUiThread(new Runnable() {
							public void run() {
								shuffleButtons();
							}
						});
					}
					else
						endTest(null);
				}
				else {
					final CharSequence cs = ((Button) v).getText();
					v.post(new Runnable() {
						public void run() {
							((Button) v).setText("Incorrect!");
						}
					});
					android.os.SystemClock.sleep(500);
					currentScore--;
					v.post(new Runnable() {
						public void run() {
							((Button) v).setText(cs);
						}
					});
				}
			}
		}).start();
	}
}
