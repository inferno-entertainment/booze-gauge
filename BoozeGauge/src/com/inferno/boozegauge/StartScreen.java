package com.inferno.boozegauge;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StartScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_screen, menu);
        return true;
    }
    
    public void playFull() {
    	playAlpha();
    	playBal();
    	playMem();
    	playLog();
    	
    	showRes();
    }
    
    public void playAlpha() {
    
    }
    
    public void playBal() {
    	
    }
    
    public void playMem() {
    	
    }
    
    public void playLog() {
    	
    }
    
    public void showRes() {
    	
    }
}
