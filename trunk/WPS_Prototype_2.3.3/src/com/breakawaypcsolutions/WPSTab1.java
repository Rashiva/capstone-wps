package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WPSTab1 extends Activity {
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.graphical);
	
	   Button next = (Button) findViewById(R.id.btnVideoLib);
	   next.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           Intent myIntent = new Intent(view.getContext(), WPSVideoLibrary.class);
	           startActivityForResult(myIntent, 0);
	       }
	   });     
}
	
}