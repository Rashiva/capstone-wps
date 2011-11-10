package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    }
}