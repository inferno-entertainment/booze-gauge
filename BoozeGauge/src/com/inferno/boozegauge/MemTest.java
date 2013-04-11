package com.inferno.boozegauge;


import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Activity to handle the memory test
public class MemTest extends SuperActivity {
	public TextView textSeq; //Variable to access the user sequence display
	private double score = 0; //Holds the user's in test score
	private int next = 0; //Next random number in the sequence
	private int length; // Current length of the sequence
	private List<Integer> sequence = new ArrayList<Integer>(); //The randomly generated sequence
	private List<Integer> attempt = new ArrayList<Integer>(); //User's guess to the sequence
	private Random sequenceGen = new Random(); //Generates the elements to the random sequence
	private int place = 0; //For loop counter
	private String disp;
	
	//Generate and display the next sequence or call end test
	public void nextSequence()
	{
		String temp = "";
		//Get access to the user sequence display
		textSeq = (TextView) findViewById(R.id.seqDisp);
		if (length > 7)
		{
			//Compare the sequence to the answers
			for(int i = 0; i < length; i++)
			{
				if (sequence.get(i) == attempt.get(i))
				{
					score++;
				}
			}
			//Clear the lists for reuse
			sequence.clear();
			attempt.clear();
		}
		
		//Increase the sequence length
		length++;
		//A sequence of length 4 and 5 has been done
		if (length > 10) 
		{
			//End the test
			endTest();
		}
		
		//Generate a new sequence
		else
		{
			for(place = 0; place < length; place++)
			{
				//Generate random number
				//TO DO: remove dependencies on this system to simplify it
				next = sequenceGen.nextInt(4) + 1;
				//Add the number to the sequence
				temp += Integer.toString(next);
				sequence.add(next);
			}
		}

		disp = temp;
		textSeq.setText(disp);	
	}
	
	//Button1 pressed
	public void press1(View view)
	{
		//Remove the current sequence from view
		textSeq.setText("");
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
		//Remove the current sequence from view
		textSeq.setText("");
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
		//Remove the current sequence from view
		textSeq.setText("");
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
		//Remove the current sequence from view
		textSeq.setText("");
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
		//Calculate the first with a length of 4 sequence to start the test
		length = 7;
		nextSequence(); 
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mem_test, menu);
		return true;
	}

	//Update the Score
	public void calculateScore() {

		Globals.score += 100*(score/27);
	}
}
