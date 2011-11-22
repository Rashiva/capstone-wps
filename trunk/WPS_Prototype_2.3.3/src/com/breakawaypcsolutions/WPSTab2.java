package com.breakawaypcsolutions;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WPSTab2 extends Activity{
	    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.progressbar);
        
 	   Button next = (Button) findViewById(R.id.btnVideoLib2);
 	   next.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPSVideoLibrary.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   });	
 	   
 	   Button dtTest = (Button) findViewById(R.id.button1);
 	   dtTest.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	    	    
 	    	  TimerTask task = new TimerTask()
 	    	  {
				@Override
				public void run() {
	    	   			// Generate Data Table
	    	   			int pb1 = (int)(Math.random()*100);
	    	   			int pb2 = (int)(Math.random()*100);
	    	   			int pb3 = (int)(Math.random()*100);
	    	   			int pb4 = (int)(Math.random()*100);
	      		
	    	   			// Update Progress Bars
	    	   			ProgressBar pbFeedPump = (ProgressBar) findViewById(R.id.progressBar1);
	    	   			ProgressBar pbMediaFilter = (ProgressBar) findViewById(R.id.progressBar2);
	    	   			ProgressBar pbCartridges = (ProgressBar) findViewById(R.id.progressBar3);
	    	   			ProgressBar pbMotor = (ProgressBar) findViewById(R.id.progressBar4);
	      		   
	    	   			// Update Progress Bars
	    	   			pbFeedPump.setProgress(pb1);
	    	   			pbMediaFilter.setProgress(pb2);
	    	   			pbCartridges.setProgress(pb3);
	    	   			pbMotor.setProgress(pb4);		
				} 
 	    	  };
 	    	  
	    	  new Timer().scheduleAtFixedRate(task, 2000, 2000);
 	    	  
 	       }
 	    	  
 	   });	
 	   
	}

	}
