package com.orb.solveit;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import tyrantgit.explosionfield.ExplosionField;


public class MainActivity extends AppCompatActivity {
    // variables
    Button playBtn, btn_settings, btn_about, btn_exit, btnHighScore;
    MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // starting the background Music

        Intent bgmusicService = new Intent(this, BackgroundMusicService.class);
        bgmusicService.setAction(BackgroundMusicService.PLAY_BG_MUSIC);
        startService(bgmusicService);


        final Typeface playFont = Typeface.createFromAsset(getAssets(), "fonts/Franks.otf");


        clickSound = MediaPlayer.create(this, R.raw.button);

        clickSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                clickSound.stop();
                clickSound.reset();
                clickSound = null;
                clickSound = MediaPlayer.create(MainActivity.this, R.raw.button);


            }
        });


        // settings button
        btn_settings = (Button) findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });


        // about button
        btn_about = (Button) findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });


        // exit button
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm Exit");
                builder.setMessage("Are you sure to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });


        // play button
        playBtn = (Button) findViewById(R.id.play);
        playBtn.setTypeface(playFont);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();

                playBtn.setVisibility(View.INVISIBLE);
                // show the explosion of particle
//                new ParticleSystem(MainActivity.this, 100, R.drawable.particle, 1000)
//                        .setSpeedRange(0.2f, 0.5f)
//                        .oneShot(playBtn, 100);
//

                ExplosionField explosionField=ExplosionField.attach2Window(MainActivity.this);
                explosionField.explode(playBtn);


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // start the activity
                        Intent intent = new Intent(MainActivity.this, GamePlay.class);
                        startActivity(intent);
                    }
                }, 1000);


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset(playBtn);
                        playBtn.setVisibility(View.VISIBLE);
                    }
                }, 1500);


            }
        });


        btnHighScore = (Button) findViewById(R.id.btn_high_score);
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSound.start();
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, BackgroundMusicService.class);
        intent.setAction(BackgroundMusicService.PAUSE_BG_MUSIC);
        startService(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundMusicService.class);
        intent.setAction(BackgroundMusicService.PLAY_BG_MUSIC);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cancel any previous notification set


        // create new reminder notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("How good can you solve?")
                .setContentText("Let's solve these quiz and score.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis() + 1000 * 50)
                .setShowWhen(true)
                .setColor(Color.argb(0, 255, 0, 0))
                .setTicker("Play Solve it ! now")
                .setSubText("Be a Quiz Hero")
                .setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            Intent intent = new Intent(this, BackgroundMusicService.class);
            intent.setAction(BackgroundMusicService.PAUSE_BG_MUSIC);
            startService(intent);
        }

    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }


}
