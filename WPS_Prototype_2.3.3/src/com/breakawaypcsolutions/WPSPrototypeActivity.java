package com.breakawaypcsolutions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.RoundRectShape;


public class WPSPrototypeActivity extends Activity {
    /** Called when the activity is first created. */
	
	ShapeDrawable pgDrawable1, pgDrawable2, pgDrawable3, pgDrawable4;
	boolean sysStatus = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        
		Log.v("whatever", (getFilesDir()).getAbsolutePath());
        
     //   Button status = (Button) findViewById(R.id.status_indicator);
     //   status.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
               
        ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
		ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
		ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
		ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
			
		final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 }; 
		
		pgDrawable1 = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null)); 
		pgDrawable1.getPaint().setColor(0xffff0000); 
		pgDrawable2 = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null)); 
		pgDrawable2.getPaint().setColor(0xffff0000); 
		pgDrawable3 = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null)); 
		pgDrawable3.getPaint().setColor(0xffff0000); 
		pgDrawable4 = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null)); 
		pgDrawable4.getPaint().setColor(0xffff0000);
    
		ClipDrawable progress1 = new ClipDrawable(pgDrawable1, Gravity.LEFT, ClipDrawable.HORIZONTAL); 
		ClipDrawable progress2 = new ClipDrawable(pgDrawable2, Gravity.LEFT, ClipDrawable.HORIZONTAL); 
		ClipDrawable progress3 = new ClipDrawable(pgDrawable3, Gravity.LEFT, ClipDrawable.HORIZONTAL); 
		ClipDrawable progress4 = new ClipDrawable(pgDrawable4, Gravity.LEFT, ClipDrawable.HORIZONTAL);
     
		pb1.setProgressDrawable(progress1);    
		pb1.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal)); 
		pb2.setProgressDrawable(progress2);    
		pb2.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal)); 
		pb3.setProgressDrawable(progress3);    
		pb3.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal)); 
		pb4.setProgressDrawable(progress4);    
		pb4.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal)); 
        
  	   Button next = (Button) findViewById(R.id.btnVideoLib2);
  	   next.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	           Intent myIntent = new Intent(view.getContext(), WPSVideoLibrary.class);
  	           startActivityForResult(myIntent, 0);
  	       }
  	   });
  	   
  	   Button pwr = (Button) findViewById(R.id.btnPwr);
  	   pwr.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	    	   TextView txtPwr = (TextView) findViewById(R.id.txtPwr);
  	           if(!sysStatus) {
  	        	   sysStatus = !sysStatus;
  	        	   txtPwr.setTextColor(Color.RED);
  	        	   txtPwr.setText("OFF");
  	           } else {
  	        	   sysStatus = !sysStatus;
  	        	   txtPwr.setTextColor(Color.GREEN);
  	        	   txtPwr.setText("ON");
  	           }
  	       }
  	   });
  	   
  	   Button dtTest = (Button) findViewById(R.id.button1);
  	   dtTest.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	    	    
  	    	  TimerTask task = new TimerTask()
  	    	  {
 				@Override
 				public void run() {
 	      		
 	    	   			// Update Progress Bars
 	    	   			ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
 	    	   			ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
 	    	   			ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
 	    	   			ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
  	   			
 	    	   			// Save/Generate Data Table
 	    	   			saveToMemory();
 	    	   			
 	    	   			// Get Data Table
 	    	   			int[] data = loadFromMemory();
 	      		   
 	    	   			// Update Progress Bars
 	    	   			if(data[0] < 50)
 	    	   				pgDrawable1.getPaint().setColor(0xffff0000); 
 	    	   			else
 	    	   				pgDrawable1.getPaint().setColor(0xff00ff00); 
 	    	   			if(data[1] < 50)
 	    	   				pgDrawable2.getPaint().setColor(0xffff0000); 
 	    	   			else
 	    	   				pgDrawable2.getPaint().setColor(0xff00ff00); 
 	    	   			if(data[2] < 50)
 	    	   				pgDrawable3.getPaint().setColor(0xffff0000); 
 	    	   			else
 	    	   				pgDrawable3.getPaint().setColor(0xff00ff00);
 	    	   			if(data[3] < 50)
	    	   				pgDrawable4.getPaint().setColor(0xffff0000); 
	    	   			else
	    	   				pgDrawable4.getPaint().setColor(0xff00ff00);
 	    	   			
 	    	   			pb1.setProgress(data[0]);
 	    	   			pb2.setProgress(data[1]);
 	    	   			pb3.setProgress(data[2]);
 	    	   			pb4.setProgress(data[3]);	
 				} 
  	    	  };
  	    	  
 	    	  new Timer().scheduleAtFixedRate(task, 4000, 4000);
  	    	  
  	       }
  	    	  
  	   });	
  	   
 	}
 	
 	public void saveToMemory() 
 	{
 		int feedPump = (int)(Math.random()*100);
 		int mediaFilter = (int)(Math.random()*100);
 		int cartridges = (int)(Math.random()*100);
 		int motor = (int)(Math.random()*100);
 		
 		try
 		{
 			FileOutputStream fos = openFileOutput ("DataTable.rpe", Context.MODE_PRIVATE);
         	ObjectOutputStream oos = new ObjectOutputStream(fos);
         	oos.writeBytes("*Data_Table*\n");
         	
         	//Data Table
         	oos.writeBytes("FeedPump " + feedPump + "\n");
         	oos.writeBytes("MediaFilter " + mediaFilter + "\n");
         	oos.writeBytes("Cartridges " + cartridges + "\n");
         	oos.writeBytes("Motor " + motor + "\n");
         	
         	oos.close();
         	fos.close();
         }
         catch (IOException e)
         {
         	e.printStackTrace();
         }
 		}
 	
 	
 	public int[] loadFromMemory()
 	{
 		
 		int data[] = new int[5];
 		data[0] = 75;
 		data[1] = 75;
 		data[2] = 75;
 		data[3] = 75;
 		
 		try
 		{
 			FileInputStream fin = openFileInput ("DataTable.rpe");
 			DataInputStream in = new DataInputStream(fin);
 			BufferedReader br = new BufferedReader(new InputStreamReader(in));
 
 			String[] mySplit = new String[2];
 			String header;
 			String strLine;
 			int value;
 		
 			header = br.readLine(); //ignore this data
 		
 			//Feed Pump
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[0] = value;
 		
 			//Media Filter
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[1] = value;
 		
 			//Cartridges
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[2] = value;
 		
 			//Motor
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[3] = value;
 		
 			in.close();
 			
 			return data;
 		}	
 		catch (IOException e)
 		{
 			e.printStackTrace();
 		}
 		
 		return data;
 	}

}