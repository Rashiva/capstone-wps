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
import android.os.Handler;
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


public class WPSPrototypeActivity extends Activity {
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
	int filterTest = 0;
	int membraneTest = 0;
	int voltageTest = 0;
	int waterPurityTest = 0;
	Handler handler = new Handler();
	int overrideMode = 0;
	
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
          
          final Runnable updatePowerButton = new Runnable() {
        	   public void run() {
        		   if(sysStatus == 0) {
        			   pwr.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
    	        	   pwr.setText("Power: OFF");
    	        	   feedPumpStatus = 0;
    	        	   ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
     	        	   feed_pump.setImageResource(R.drawable.red_x);
    		  	   		pb1.setProgress(0);
    		   			pb2.setProgress(0);
    		   			pb3.setProgress(0);
    		   			pb4.setProgress(0);
    		   			overrideMode = 0;
        	          } else if (sysStatus == 1) {
        	        	  pwr.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
        	        	  pwr.setText("Power: ON");
        	        	  overrideMode = 0;
        	          } else {
        	        	  pwr.getBackground().setColorFilter(0xffffff00, PorterDuff.Mode.MULTIPLY);
        	      	   	  pwr.setText("Power: OVERRIDE");
        	      	   	  overrideMode = 1;
        	          }      	   	  
        	   }
        	};
        	
        	final Runnable updateImageView = new Runnable() {
         	   public void run() {
         		   	ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
         		   	feedPumpStatus = 1;
	      	   		feed_pump.setImageResource(R.drawable.green_check);
         	   }
        	};
        
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
  	   			if(filterTest == 0) 
  	   			{
  	   				// Normal Operation
  	   				if(data[2] <= 20)
  	   					pgDrawable1.getPaint().setColor(0xffff0000); 
  	   				else if (data[2] <= 50)
  	   					pgDrawable1.getPaint().setColor(0xffffff00);
  	   				else
  	   					pgDrawable1.getPaint().setColor(0xff00ff00); 
  	   				
  	  	   			if(feedPumpStatus == 0)
  	  	   			{
  		  	   			pb1.setProgress(0);
  			   			filters = 90;
  	  	   			}
  	  	   			else
  	  	   			{
  		  	   			pb1.setProgress(data[2]);
  	  	   			}
  	   			}
  	   			else 
  	   			{
  	   				// Filter Fail Test Case
  	   				int p1 = pb1.getProgress()-5;
  	   				if (p1 <= 0 && sysStatus == 2)
  	   				{
  	   					filters = 0;
  	   					pb1.setProgress(0);
  	   					sysStatus = 0;
  	   					handler.postDelayed(updatePowerButton, 100);
  	   				}
  	   				else if(p1 < 0)
  	   				{
   						filters = 0;
  	   					pb1.setProgress(0);
  	   				}
  	   				else
  	   				{
  	   					filters = p1;
  	   					pb1.setProgress(p1);
  	   				}

  	   				if(pb1.getProgress() <= 20 && overrideMode == 0 && sysStatus != 0)
  	   				{
  	   					sysStatus = 2;
	   					pgDrawable1.getPaint().setColor(0xffff0000);
	   					handler.postDelayed(updatePowerButton, 100);
  	   				}
  	   				else if(pb1.getProgress() <= 20)
	   					pgDrawable1.getPaint().setColor(0xffff0000);
	   				else if (pb1.getProgress() <= 50)
	   					pgDrawable1.getPaint().setColor(0xffffff00);
	   				else
	   					pgDrawable1.getPaint().setColor(0xff00ff00);
  	   			} 
  	   			
  	   			if(membraneTest == 0)
  	   			{
  	   				//Normal Operation
  	   				if(data[3] <= 20)
  	   					pgDrawable2.getPaint().setColor(0xffff0000); 
  	   				else if (data[3] <= 50)
  	   					pgDrawable2.getPaint().setColor(0xffffff00);
  	   				else
  	   					pgDrawable2.getPaint().setColor(0xff00ff00); 
  	   				
  	  	   			if(feedPumpStatus == 0)
  	  	   			{
  		  	   			pb2.setProgress(0);
  			   			roMembrane = 93;
  	  	   			}
  	  	   			else
  	  	   			{
  		  	   			pb2.setProgress(data[3]);
  	  	   			}
  	   			}
  	   			else
  	   			{
  	   				// Membrane Fail Test Case
  	   				int p2 = pb2.getProgress()-5;
  	   				if (p2 <= 0 && sysStatus == 2)
	   				{
	   					roMembrane = 0;
	   					pb2.setProgress(0);
	   					sysStatus = 0;
	   					handler.postDelayed(updatePowerButton, 100);
	   				}
	   				else if(p2 < 0)
	   				{
	   					roMembrane = 0;
	   					pb2.setProgress(0);
	   				}
	   				else
	   				{
	   					roMembrane = p2;
	   					pb2.setProgress(p2);
	   				}

  	   				if(pb2.getProgress() <= 20 && overrideMode == 0 && sysStatus != 0)
  	   				{
  	   					sysStatus = 2;
	   					pgDrawable2.getPaint().setColor(0xffff0000);
	   					handler.postDelayed(updatePowerButton, 100);
  	   				}
	  	   			else if(pb2.getProgress() <= 20)
	   					pgDrawable2.getPaint().setColor(0xffff0000);
	   				else if (pb2.getProgress() <= 50)
	   					pgDrawable2.getPaint().setColor(0xffffff00);
	   				else
	   					pgDrawable2.getPaint().setColor(0xff00ff00);
  	   			}
  	   			if(voltageTest == 0)
  	   			{
  	   				// Normal Operation
  	   				if(data[4] <= 20)
  	   					pgDrawable3.getPaint().setColor(0xffff0000);
  	   				else if (data[4] <= 50)
  	   					pgDrawable3.getPaint().setColor(0xffffff00);
  	   				else
  	   					pgDrawable3.getPaint().setColor(0xff00ff00);
  	   				
  	  	   			if(feedPumpStatus == 0)
  	  	   			{
  		  	   			pb3.setProgress(0);
  			   			voltage = 97;
  	  	   			}
  	  	   			else
  	  	   			{
  		  	   			pb3.setProgress(data[4]);
  	  	   			}
  	   			}
  	   			else
  	   			{		
  	   				// Voltage Fail Test Case
  	   				int p3 = pb3.getProgress()-5;
  	   				if (p3 <= 0 && sysStatus == 2)
	   				{
  	   					voltage = 0;
	   					pb3.setProgress(0);
	   					sysStatus = 0;
	   					handler.postDelayed(updatePowerButton, 100);
	   				}
	   				else if(p3 < 0)
	   				{
	   					voltage = 0;
	   					pb3.setProgress(0);
	   				}
	   				else
	   				{
	   					voltage = p3;
	   					pb3.setProgress(p3);
	   				}

  	   				if(pb3.getProgress() <= 20 && overrideMode == 0 && sysStatus != 0)
  	   				{
	   					sysStatus = 2;
	   					pgDrawable3.getPaint().setColor(0xffff0000);
	   					handler.postDelayed(updatePowerButton, 100);
  	   				}
	  	   			else if(pb3.getProgress() <= 20)
	   					pgDrawable3.getPaint().setColor(0xffff0000);
	   				else if (pb3.getProgress() <= 50)
	   					pgDrawable3.getPaint().setColor(0xffffff00);
	   				else
	   					pgDrawable3.getPaint().setColor(0xff00ff00);
  	   			}
  	   			if(waterPurityTest == 0)
  	   			{
  	   				// Normal Operation
  	   				if(data[5] <= 20)
  	   					pgDrawable4.getPaint().setColor(0xffff0000); 
  	   				else if (data[5] <= 50)
  	   					pgDrawable4.getPaint().setColor(0xffffff00);
  	   				else
  	   					pgDrawable4.getPaint().setColor(0xff00ff00); 
  	   				
  	  	   			if(feedPumpStatus == 0)
  	  	   			{
  		  	   			pb4.setProgress(0);
  			   			waterPurity = 100;
  	  	   			}
  	  	   			else
  	  	   			{
  		  	   			pb4.setProgress(data[5]);
  	  	   			}
  	   			}
  	   			else
  	   			{
  	   				// Fail Test Case
  	   				int p4 = pb4.getProgress()-5;
  	   				if (p4 <= 0 && sysStatus == 2)
	   				{
  	   					waterPurity = 0;
	   					pb4.setProgress(0);
	   					sysStatus = 0;
	   					handler.postDelayed(updatePowerButton, 100);
	   				}
	   				else if(p4 < 0)
	   				{
	   					waterPurity = 0;
	   					pb4.setProgress(0);
	   				}
	   				else
	   				{
	   					waterPurity = p4;
	   					pb4.setProgress(p4);
	   				}

  	   				if(pb4.getProgress() <= 20 && overrideMode == 0 && sysStatus != 0)
  	   				{
  	   					sysStatus = 2;
	   					pgDrawable4.getPaint().setColor(0xffff0000);
	   					handler.postDelayed(updatePowerButton, 100);
  	   				}
	  	   			else if(pb4.getProgress() <= 20)
	   					pgDrawable4.getPaint().setColor(0xffff0000);
	   				else if (pb4.getProgress() <= 50)
	   					pgDrawable4.getPaint().setColor(0xffffff00);
	   				else
	   					pgDrawable4.getPaint().setColor(0xff00ff00);
  	   			}
			} 
			
   	  };
  	  
   	  new Timer().scheduleAtFixedRate(task, 0, 1500);
   	  
 	   pwr.setOnClickListener(new View.OnClickListener() {
 		   public void onClick(View view) {
 			   ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
	   			ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
	   			ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);
	   			ProgressBar pb4 = (ProgressBar) findViewById(R.id.progressBar4);
 			   if(sysStatus == 0) {
 	        	   sysStatus = 1;
 	        	   pwr.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
 	        	   pwr.setText("Power: ON");
 	        	   handler.postDelayed(updateImageView, 5000);
 	        	   filterTest = 0;
 	        	   membraneTest = 0;
 	        	   voltageTest = 0;
 	        	   waterPurityTest = 0;
 	        	   overrideMode = 0;
 	           } else if(sysStatus == 1) {
 	        	   sysStatus = 0;
 	        	   pwr.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
	        	   pwr.setText("Power: OFF");
	        	   feedPumpStatus = 0;
	        	   ImageView feed_pump = (ImageView) findViewById(R.id.imageView1);
 	        	   feed_pump.setImageResource(R.drawable.red_x);
		  	   		pb1.setProgress(0);
		   			pb2.setProgress(0);
		   			pb3.setProgress(0);
		   			pb4.setProgress(0);
		   			filterTest = 0;
 	        	   membraneTest = 0;
 	        	   voltageTest = 0;
 	        	   waterPurityTest = 0;
		   			overrideMode = 0;
 	           } else {
        		   sysStatus = 1;
 	        	   pwr.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
 	        	   pwr.setText("Power: ON");
 	        	   filterTest = 0;
	        	   membraneTest = 0;
	        	   voltageTest = 0;
	        	   waterPurityTest = 0;
	        	   overrideMode = 0;
 	           }
 	       }
 	   });
  	  
	  Button test1 = (Button) findViewById(R.id.btnTest1);
	  test1.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	         // Filters Fail Test
	         filterTest = 1;
	       }
	   });
	   
		
	   Button test2 = (Button) findViewById(R.id.btnTest2);
	   test2.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           // Membrane Fail Test
	    	   membraneTest = 1;
	       }
	   });
	   
		
	   Button test3 = (Button) findViewById(R.id.btnTest3);
	   test3.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           // Voltage Fail Test
	    	   voltageTest = 1;
	       }
	   }); 
	   
	   Button test4 = (Button) findViewById(R.id.btnTest4);
	   test4.setOnClickListener(new View.OnClickListener() {
	       public void onClick(View view) {
	           // Water Purity Fail Test
	    	   waterPurityTest = 1;
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