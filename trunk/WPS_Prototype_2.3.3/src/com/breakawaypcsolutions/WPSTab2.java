package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WPSTab2 extends Activity {
    
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
 	   
 	   // Input Data Table (Event Driven)
 	   
 	   // Save Data Locally (Necessary ???)
 	   
 	   // Interpret Data
 	   
 	   // Update Progress Bars
 	   ProgressBar pbFeedPump = (ProgressBar) findViewById(R.id.progressBar1);
 	   pbFeedPump.setProgress(25);
 	   
 	   ProgressBar pbMediaFilter = (ProgressBar) findViewById(R.id.progressBar2);
	   pbMediaFilter.setProgress(50);
	   
	   ProgressBar pbCartridges = (ProgressBar) findViewById(R.id.progressBar3);
 	   pbCartridges.setProgress(75);
 	   
 	   ProgressBar pbMotor = (ProgressBar) findViewById(R.id.progressBar4);
	   pbMotor.setProgress(100);
	   
	   // Output Data Table to Controllers
 	   
    }
}