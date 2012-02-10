package com.breakawaypcsolutions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
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
        
        VideoView mVideoView = (VideoView) findViewById(R.id.videoView1);

        try{
        	String path = "/sdcard/sample_mpeg4.mp4";
            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.start();
        }catch(Exception e){}
        
  	   Button next = (Button) findViewById(R.id.btnVidBack);
  	   next.setOnClickListener(new View.OnClickListener() {
  	       public void onClick(View view) {
  	           finish();
  	       }
  	   }); 
    }
}
