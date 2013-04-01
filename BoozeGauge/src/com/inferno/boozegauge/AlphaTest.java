package com.inferno.boozegauge;

import java.util.Random;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AlphaTest extends SuperActivity {
	private static Button buttonUL;
	private static Button buttonUR;
	private static Button buttonCL;
	private static Button buttonCR;
	private static Button buttonLL;
	private static Button buttonLR;
	
	Random generator = new Random();
	private int correctButton;
	private int currentPosition = 0;
	
	
	private Button[] appButtons;
	private char[] revAlphabet = {'Z','Y','X','W','V','U','T','S','R','Q','P','O','N','M','L','K','J','I','H','G','F','E','D','C','B','A'};
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alpha_test);
		buttonUL = (Button)findViewById(R.id.alphaButtonUL);
		buttonUR = (Button)findViewById(R.id.alphaButtonUR);
		buttonCL = (Button)findViewById(R.id.alphaButtonCL);
		buttonCR = (Button)findViewById(R.id.alphaButtonCR);
		buttonLL = (Button)findViewById(R.id.alphaButtonLL);
		buttonLR = (Button)findViewById(R.id.alphaButtonLR);
		appButtons = new Button[] {buttonUL, buttonUR, buttonCL, buttonCR, buttonLL, buttonLR};
		
	}
	
	protected void onResume() {
		super.onResume();
		System.out.println("Before shuffleButtons()");
		shuffleButtons();
		System.out.println("After shuffleButtons()");
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
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
	
	public void endTest(View view) {
		setResult(0);
		finish();
	}
	
	@Override
	public void calculateScore() {
		// TODO Auto-generated method stub
		
	}
}
