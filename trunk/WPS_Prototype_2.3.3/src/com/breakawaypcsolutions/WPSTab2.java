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
 	   
 	   // change progress bars
 	   ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
 	   pb1.setProgress(25);
 	   
 	   ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
	   pb2.setProgress(50);
	   
	   ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
 	   pb3.setProgress(75);
 	   
 	  ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
	   pb4.setProgress(100);
 	   
    }
}