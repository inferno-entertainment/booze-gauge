package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import android.view.Menu;
public class MemTest extends Activity {
	//private Button first = (Button) findViewById(R.id.button1);
	Button first;
	Button second;
	Button third;
	Button forth;
	Timer start = new Timer();
	Timer stop = new Timer();
	private double score = 0;
	private int next = 0;
	private int length = 3;
	private List<Integer> sequence = new ArrayList<Integer>();
	private List<Integer> attempt = new ArrayList<Integer>();
	private Random sequenceGen = new Random();
	private int place = 0;

	
	public void nextSequence()
	{
		first = (Button) findViewById(R.id.button1);
		second = (Button) findViewById(R.id.button2);
		third = (Button) findViewById(R.id.button3);
		forth = (Button) findViewById(R.id.button4);
		if (length > 3)
		{
			for(int i = 0; i < length; i++)
			{
				if (sequence.get(i) == attempt.get(i))
				{
					score++;
				}
			}
			sequence.clear();
			attempt.clear();
		}
		length++;
		if (length > 5) 
		{
			//score = 60/score;
			endTest();
		}
		
		for(place = 0; place < length; place++)
		{
			
			//start = new Timer();
			//stop = new Timer();
			next = sequenceGen.nextInt(4) + 1;
			sequence.add(next);
			//sequence.add(sequenceGen.nextInt(4) + 1);
			/*start.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
				/*	if (next == 1 )
					{
						first.setText("Yep");
					}
					else if (next == 2 )
					{
						second.setText("Yep");
					}
					else if (next == 3 )
					{
						third.setText("Yep");
					}
					else if (next == 4 )
					{
						forth.setText("Yep");
					}*/
					//first.setText("Yep");
					/*stop.schedule(new TimerTask()
					{
						@Override
						public void run()
						{
						first.setText("1");
						second.setText("2");
						third.setText("3");
						forth.setText("4");
						}
					}, 500);
				}
			}, 1000);*/
			//first.setText("Nope");
			//button1.setText("vndjflnj");
		}
	}
	
	//Button1 pressed
	public void press1(View view)
	{
		//Extra press
		if (attempt.size() >= length)
		{
			nextSequence();
		}
		//Add the data
		attempt.add(1);
		//Check the data
		if (attempt.size() == length)
		{
			nextSequence();
		}
	}
	
	//Button2 pressed
	public void press2(View view)
	{
		if (attempt.size() >= length)
		{
			nextSequence();
		}
		attempt.add(2);
		if (attempt.size() == length)
		{
			nextSequence();
		}
	}
	
	//Button3 pressed
	public void press3(View view)
	{
		if (attempt.size() >= length)
		{
			nextSequence();
		}
		attempt.add(3);
		if (attempt.size() == length)
		{
			nextSequence();
		}
	}
	
	//Button4 pressed
	public void press4(View view)
	{
		if (attempt.size() >= length)
		{
			nextSequence();
		}
		attempt.add(4);
		if (attempt.size() == length)
		{
			nextSequence();
		}
	}
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mem_test);
		
		/*button = (Button) findViewById(R.id.button2);
		button2 = (TextView) button.findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.button3);
		button3 = (TextView) button.findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.button4);
		button4 = (TextView) button.findViewById(R.id.textView1);*/
		
		
		nextSequence();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/*Timer timer = new Timer();
		timer.schedule(task, 1000);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mem_test, menu);
		return true;
	}

	
	public void endTest() {
		Globals.score += score;
		setResult(0);
		finish();
	}
}
