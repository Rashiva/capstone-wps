package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WPSVideoLibrary extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.videolib);
        
 	   Button next = (Button) findViewById(R.id.btnVLBack);
 	   next.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPS_Prototype_3Activity.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   }); 
 	   
 	   Button videoA = (Button) findViewById(R.id.btnVLA);
 	   videoA.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPSVideo.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   }); 
 	   
 	   Button videoB = (Button) findViewById(R.id.btnVLB);
 	   videoB.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPSVideo.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   }); 
 	   
 	   Button videoC = (Button) findViewById(R.id.btnVLC);
 	   videoC.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPSVideo.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   }); 
 	   
 	   Button videoD = (Button) findViewById(R.id.btnVLD);
 	   videoD.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View view) {
 	           Intent myIntent = new Intent(view.getContext(), WPSVideo.class);
 	           startActivityForResult(myIntent, 0);
 	       }
 	   }); 
    }
}
