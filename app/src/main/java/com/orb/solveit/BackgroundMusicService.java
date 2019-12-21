package com.orb.solveit;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {
    final static String PLAY_BG_MUSIC = "play_bg_music";
    final static String STOP_BG_MUSIC = "stop_bg_music";
    final static String PAUSE_BG_MUSIC = "pause_bg_music";
    final static String READ_BG_MUSIC_SETTINGS = "read_bg_music_settings";
    final static String RESUME_BG_MUSIC = "resume_bg_music";


    MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences;
    boolean bg_music_enabled;

    public BackgroundMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        bg_music_enabled = sharedPreferences.getBoolean("bg_music", true);


        // background music based on settings
        mediaPlayer = MediaPlayer.create(this, R.raw.journey);
        mediaPlayer.setLooping(true);
        if (bg_music_enabled) {
            mediaPlayer.start();
        }


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        bg_music_enabled = sharedPreferences.getBoolean("bg_music", true);

        if (intent.getAction().equals(PLAY_BG_MUSIC)) {
            if (bg_music_enabled && !mediaPlayer.isPlaying()) {
                mediaPlayer=MediaPlayer.create(this,R.raw.journey);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }

        } else if (intent.getAction().equals(STOP_BG_MUSIC)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

        } else if (intent.getAction().equals(RESUME_BG_MUSIC)) {
            if (bg_music_enabled && !mediaPlayer.isPlaying()) {
                mediaPlayer.release();
            }
        } else if (intent.getAction().equals(PAUSE_BG_MUSIC)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

        }




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        mediaPlayer=null;

    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
