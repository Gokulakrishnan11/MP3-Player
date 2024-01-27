package com.example.media_player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.nio.channels.SeekableByteChannel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnp,btnb,btnf;
    private MediaPlayer player;
    private SeekBar sk;
    private Runnable runnable;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnp=findViewById(R.id.btnplay);
        btnb=findViewById(R.id.btnbackward);
        btnf=findViewById(R.id.btnforward);
        sk=findViewById(R.id.seekbar);
        handler= new Handler();
        btnp.setOnClickListener(this);
        btnb.setOnClickListener(this);
        btnf.setOnClickListener(this);
        player=MediaPlayer.create(this,R.raw.song);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                sk.setMax(player.getDuration());
                player.start();
                changeSeekbar();
            }
        });
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeSeekbar() {
        sk.setProgress(player.getCurrentPosition());
        if(player.isPlaying())
        {
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnplay:
                if(player.isPlaying())
                {
                    player.pause();
                    btnp.setText(">");
                }
                else
                {
                    player.start();
                    btnp.setText("||");
                    changeSeekbar();
                }
                break;
            case R.id.btnforward:
                player.seekTo(player.getCurrentPosition()+5000);
                break;
            case R.id.btnbackward:
                player.seekTo(player.getCurrentPosition()-5000);
                break;
        }
    }
}