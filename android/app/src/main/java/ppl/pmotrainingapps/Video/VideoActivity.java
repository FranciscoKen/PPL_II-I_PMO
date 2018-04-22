package ppl.pmotrainingapps.Video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import ppl.pmotrainingapps.Materi.ContentMateri;
import ppl.pmotrainingapps.R;

public class VideoActivity extends AppCompatActivity{
    private ProgressDialog mDialog;
    private VideoView videoView;
    private ImageButton mPlayButton;
    private int position = 0;
    private int id_video;

    String videoURL = "http://pplk2a.if.itb.ac.id/ppl/uploads/video/Raft.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        videoURL = intent.getStringExtra(ContentMateri.EXTRA_MESSAGE);

        videoView = (VideoView) findViewById(R.id.VideoView);
        mPlayButton = (ImageButton) findViewById(R.id.play_button);


        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();
                mp.setLooping(false);
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideo();
            }
        });

        mDialog = new ProgressDialog(VideoActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        Uri uri = Uri.parse(videoURL);
        videoView.setVideoURI(uri);
        videoView.seekTo(100);

    }

    public void startVideo(){
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);
        mPlayButton.setVisibility(View.GONE);
        try{
            if(!videoView.isPlaying()){
                videoView.seekTo(position);
                if(position == 0){
                    videoView.start();
                } else {
                    videoView.pause();
                }

            } else {
                videoView.pause();
            }


        } catch (Exception e){

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",videoView.getCurrentPosition());
        videoView.pause();
    }

}
