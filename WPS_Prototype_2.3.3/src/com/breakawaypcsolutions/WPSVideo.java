package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class WPSVideo extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video);
        
        VideoView videoView = (VideoView) findViewById(R.id.videoView1);
        /*MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setVideoPath("C:/User/Alex/Facebook.3gp");
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();*/
        try{
        MediaPlayer mp=new MediaPlayer();
        SurfaceHolder holder = videoView.getHolder();
        mp.setDataSource("C:/User/Alex/Facebook.3gp"); 
        mp.setScreenOnWhilePlaying(true); 
        mp.setDisplay(holder); 
        mp.prepare(); 
        mp.start();
        }catch(Exception e){}
        
  	   Button next = (Button) findViewById(R.id.btnVidBack);
  	   next.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	           Intent myIntent = new Intent(view.getContext(), WPSVideoLibrary.class);
  	           startActivityForResult(myIntent, 0);
  	       }
  	   }); 
    }
}
