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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
	      		
	    	   			// Update Progress Bars
	    	   			ProgressBar pbFeedPump = (ProgressBar) findViewById(R.id.progressBar1);
	    	   			ProgressBar pbMediaFilter = (ProgressBar) findViewById(R.id.progressBar2);
	    	   			ProgressBar pbCartridges = (ProgressBar) findViewById(R.id.progressBar3);
	    	   			ProgressBar pbMotor = (ProgressBar) findViewById(R.id.progressBar4);
	    	   			
	    	   			
	    	   			
	    	   			// Save/Generate Data Table
	    	   			saveToMemory();
	    	   			
	    	   			// Get Data Table
	    	   			int[] data = loadFromMemory();
	      		   
	    	   			// Update Progress Bars
	    	   			pbFeedPump.setProgress(data[0]);
	    	   			pbMediaFilter.setProgress(data[1]);
	    	   			pbCartridges.setProgress(data[2]);
	    	   			pbMotor.setProgress(data[3]);		
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