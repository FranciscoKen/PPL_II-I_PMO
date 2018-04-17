package ppl.pmotrainingapps.Video;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import ppl.pmotrainingapps.R;

public class VideoActivityExample extends AppCompatActivity{
    private ProgressDialog mDialog;
    private VideoView videoView;
    private ImageButton mPlayButton;

    String videoURL = "http://pplk2a.if.itb.ac.id/ppl/uploads/video/Raft.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        videoView = (VideoView) findViewById(R.id.VideoView);
        mPlayButton = (ImageButton) findViewById(R.id.play_button);

        String fullscreen = getIntent().getStringExtra("fullScreenInd");
        if("y".equals(fullscreen)){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //getSupportActionBar().hide();
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
//            setRequestedOrientat/ion(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){

                }else {
                    mDialog = new ProgressDialog(VideoActivityExample.this);
                    mDialog.setMessage("Please wait...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();

                    try{
                        if(!videoView.isPlaying()){

                            //original window
                            Uri uri = Uri.parse(videoURL);
                            videoView.setVideoURI(uri);
                            videoView.start();
                        } else {
                            videoView.pause();
                        }

                        mPlayButton.setVisibility(View.GONE);

                    } catch (Exception e){

                    }
                }
            }
        });

        FullScreenMediaController vidControl = new FullScreenMediaController(this);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();
                mp.setLooping(true);
                videoView.start();
            }
        });
    }

    private boolean isLandscape() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            Log.d("IS LANDSCAPE", "IT IS LANDSCAPE");
            return true;
        }

        return false;
    }
}
