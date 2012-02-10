package com.breakawaypcsolutions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.Random;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.RoundRectShape;


public class WPS_Prototype_3Activity extends Activity {
/** Called when the activity is first created. */
	
	ShapeDrawable pgDrawable1, pgDrawable2, pgDrawable3, pgDrawable4;
	int sysStatus = 0; // 0 = Off, 1 = On, 2 = Override
	int feedPumpStatus = 0; //0 = Out of Water, 1 = In Water
	int interfaceTest;
	int filters = 90;
	int roMembrane = 93;
	int waterPurity = 97;
	int voltage = 100;
	int pos1 = 0;
	int pos2 = 1;
	int pos3 = 1;
	int pos4 = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        
		Log.v("whatever", (getFilesDir()).getAbsolutePath());
		final Button pwr = (Button) findViewById(R.id.btnPwr);
		
		pwr.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
               
        final ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
		final ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
		final ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
		final ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
			
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
		
          if(sysStatus == 0) {
        	  pwr.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
       	   	  pwr.setText("Power: OFF");
          } else if (sysStatus == 1) {
        	  pwr.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
        	  pwr.setText("Power: ON");
          } else {
        	  pwr.getBackground().setColorFilter(0xffffff00, PorterDuff.Mode.MULTIPLY);
      	   	  pwr.setText("Power: OVERRIDE");
          }
          
          ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
          if(feedPumpStatus == 1)
	   		{
	   			feed_pump.setImageResource(R.drawable.green_check);
	   		}
	   		else
	   		{
	   			feed_pump.setImageResource(R.drawable.red_x);
	   		}
        
  	   Button next = (Button) findViewById(R.id.btnVideoLib2);
  	   next.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	           Intent myIntent = new Intent(view.getContext(), WPSVideoLibrary.class);
  	           startActivityForResult(myIntent, 0);
  	       }
  	   });
  	   
  	 final TimerTask task = new TimerTask()
   	  {
			@Override
			public void run() {
				
				ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
    		
  	   			// Update Progress Bars
  	   			ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
  	   			ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
  	   			ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
  	   			ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
  			
  	   			// Save/Generate Data Table
  	   			saveToMemory();
  	   			
  	   			// Get Data Table
  	   			int[] data = loadFromMemory();
  	   			
  	   			sysStatus = data[0];
	  	   		
  	   			// Update Progress Bars
  	   			if(data[2] <= 20)
  	   				pgDrawable1.getPaint().setColor(0xffff0000); 
  	   			else if (data[2] <= 50)
  	   				pgDrawable1.getPaint().setColor(0xffffff00);
  	   			else
  	   				pgDrawable1.getPaint().setColor(0xff00ff00); 
  	   			if(data[3] <= 20)
  	   				pgDrawable2.getPaint().setColor(0xffff0000); 
  	   			else if (data[3] <= 50)
	   				pgDrawable2.getPaint().setColor(0xffffff00);
  	   			else
  	   				pgDrawable2.getPaint().setColor(0xff00ff00); 
  	   			if(data[4] <= 20)
  	   				pgDrawable3.getPaint().setColor(0xffff0000);
  	   			else if (data[4] <= 50)
	   				pgDrawable3.getPaint().setColor(0xffffff00);
  	   			else
  	   				pgDrawable3.getPaint().setColor(0xff00ff00);
  	   			if(data[5] <= 20)
	   				pgDrawable4.getPaint().setColor(0xffff0000); 
  	   			else if (data[5] <= 50)
	   				pgDrawable4.getPaint().setColor(0xffffff00);
	   			else
	   				pgDrawable4.getPaint().setColor(0xff00ff00); 
  	   			
  	   			if(feedPumpStatus == 0)
  	   			{
	  	   			pb1.setProgress(0);
		   			pb2.setProgress(0);
		   			pb3.setProgress(0);
		   			pb4.setProgress(0);
  	   			}
  	   			else
  	   			{
	  	   			pb1.setProgress(data[2]);
	  	   			pb2.setProgress(data[3]);
	  	   			pb3.setProgress(data[4]);
	  	   			pb4.setProgress(data[5]);
  	   			}
			} 
			
   	  };
  	  
   	  new Timer().scheduleAtFixedRate(task, 0, 1500);
   	  
 	   pwr.setOnClickListener(new View.OnClickListener() {
 		   public void onClick(View view) {
 	           if(sysStatus == 0) {
 	        	   sysStatus = 1;
 	        	   pwr.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
 	        	   pwr.setText("Power: ON");
 	        	   feedPumpStatus = 1;
 	        	   ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
 	        	   feed_pump.setImageResource(R.drawable.green_check);
 	           } else {
 	        	   sysStatus = 0;
 	        	   pwr.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
	        	   pwr.setText("Power: OFF");
	        	   feedPumpStatus = 0;
	        	   ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
 	        	   feed_pump.setImageResource(R.drawable.red_x);
 	        	   ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
 	  	   			ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
 	  	   			ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
 	  	   			ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
		  	   		pb1.setProgress(0);
		   			pb2.setProgress(0);
		   			pb3.setProgress(0);
		   			pb4.setProgress(0);
 	           }
 	       }
 	   });
  	  
	  Button test1 = (Button) findViewById(R.id.btnTest1);
	  test1.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	         // Filters Fail Test
	       task.cancel();
	       }
	   });
	   
		
	   Button test2 = (Button) findViewById(R.id.btnTest2);
	   test2.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           // Membrane Fail Test
	    	   task.cancel();
	       }
	   });
	   
		
	   Button test3 = (Button) findViewById(R.id.btnTest3);
	   test3.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           // Water Purity Fail Test
	    	   task.cancel();
	       }
	   }); 
  	   
 	}
 	
 	public void saveToMemory() 
 	{
 		Random r = new Random();
 		int dev1 = r.nextInt(4);
 		int dev2 = r.nextInt(4);
 		int dev3 = r.nextInt(4);
 		int dev4 = r.nextInt(4);
 		
 		if(pos1 == 0)
 		{
 			pos1 = 1;
 			dev1 *= -1;
 		}
 		else
 			pos1 = 0;
 		
 		if(pos2 == 0)
 		{
 			pos2 = 1;
 			dev2 *= -1;
 		}
 		else
 			pos2 = 0;
 		
 		if(pos3 == 0)
 		{
 			pos3 = 1;
 			dev3 *= -1;
 		}
 		else
 			pos3 = 0;
 		
 		if(pos4 == 0)
 		{
 			pos4 = 1;
 			dev4 *= -1;
 		}
 		else
 			pos4 = 0;
 		
 		filters = filters + dev1;
 		if(filters > 100)
 			filters = 100;
 		else if(filters < 0)
 			filters = 0;
 		
 		roMembrane = roMembrane + dev2;
 		if(roMembrane > 100)
 			roMembrane = 100;
 		else if(roMembrane < 0)
 			roMembrane = 0;
 		

 		voltage = voltage + dev3;
 		if(voltage > 100)
 			voltage = 100;
 		else if(voltage < 0)
 			voltage = 0;
 		
 		waterPurity = waterPurity + dev4;
 		if(waterPurity > 100)
 			waterPurity = 100;
 		else if(waterPurity < 0)
 			waterPurity = 0;
 		try
 		{
 			FileOutputStream fos = openFileOutput ("DataTable.rpe", Context.MODE_PRIVATE);
         	ObjectOutputStream oos = new ObjectOutputStream(fos);
         	oos.writeBytes("*Data_Table*\n");
         	
         	//Data Table
         	oos.writeBytes("Power " + sysStatus + "\n"); //Power: 0 = Off, 1 = On, 2 = Override
         	oos.writeBytes("FeedPump " + feedPumpStatus + "\n"); //Feed Pump in water: 0=Not in water, 1=In water
         	oos.writeBytes("Filters " + filters + "\n"); //Filters: Percentage value 0 - 100
         	oos.writeBytes("ROMembrane " + roMembrane + "\n"); //R/O Membrane: Percentage value 0 - 100
         	oos.writeBytes("Voltage " + voltage + "\n"); //Voltage: Percentage value 0 - 100
         	oos.writeBytes("WaterPurity " + waterPurity + "\n"); //Water Purity: Percentage value 0 - 100
         	
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
 		
 		int data[] = new int[6];
 		data[0] = 0;
 		data[1] = 0;
 		data[2] = 75;
 		data[3] = 75;
 		data[4] = 75;
 		data[5] = 75;
 		
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
 		
 			//Power
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[0] = value;
 		
 			//Feed Pump
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[1] = value;
 		
 			//Filters
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[2] = value;
 		
 			//R/O Membrane
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[3] = value;
 			
 			//Voltage
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[4] = value;
 			
 			//Water Purity
 			strLine = br.readLine();
 			mySplit = strLine.split("\\s+");
 			strLine = mySplit[0];
 			value = Integer.parseInt(mySplit[1]);
 			data[5] = value;
 		
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