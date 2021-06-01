package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class LiveExpedition extends AppCompatActivity {
    ImageView map;
    TextView textView;
    ScrollView scrollView;
    private static final String VIDEO_SAMPLE = "video_demo";
    private static VideoView videoView;
    private static int mCurrentPosition = 0;
    private static  int mStatePosition = 0;
    private static final String PLAYBACK_TIME = "play_time";

    private Uri getMedia (String mediaName)
    {
        return Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.katelemming);
    }

    private void initializePlayer()
    {
        Uri videoUri = getMedia(VIDEO_SAMPLE) ;
        videoView.setVideoURI(videoUri);
        if (mCurrentPosition >0 )
        {
            videoView.seekTo(mCurrentPosition);
        }
        else
        {
            videoView.seekTo(1);
        }
        videoView.pause();
    }

    private void releasePlayer()
    {
        videoView.stopPlayback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_expedition);
        videoView = findViewById(R.id.videoView);
        textView = findViewById(R.id.live_streaming);
        scrollView = findViewById(R.id.scrollView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LiveExpedition.this, Live_Streaming.class));
                finish();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Live Expedition");
            super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            map = (ImageView) findViewById(R.id.imageView8);
            map.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(LiveExpedition.this, LiveMap.class));
                }
            });
        }
        if ( savedInstanceState != null)
        {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        final MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        /*
                         * add media controller
                         */
                        videoView.setMediaController(mediaController);
                        /*
                         * and set its position on screen
                         */
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                mediaController.hide();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatePosition = videoView.getCurrentPosition();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, mStatePosition);
    }

}