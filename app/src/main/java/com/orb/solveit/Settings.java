package com.orb.solveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;



public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    boolean bg_music_enabled;
    SwitchCompat musicSwitch;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

intent=new Intent(this,BackgroundMusicService.class);



        sharedPreferences=getSharedPreferences("settings",MODE_PRIVATE);


        bg_music_enabled=sharedPreferences.getBoolean("bg_music",true);

        musicSwitch=(SwitchCompat)findViewById(R.id.music_switch);
        musicSwitch.setChecked(bg_music_enabled);
        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if(musicSwitch.isChecked()){
                    editor.putBoolean("bg_music",true);
                    editor.apply();

                    intent.setAction(BackgroundMusicService.PLAY_BG_MUSIC);
                    startService(intent);

                }else{
                    editor.putBoolean("bg_music",false);
                    editor.apply();

                    intent.setAction(BackgroundMusicService.READ_BG_MUSIC_SETTINGS);
                    startService(intent);

                    intent.setAction(BackgroundMusicService.PAUSE_BG_MUSIC);
                    startService(intent);
                }

            }
        });
    }
}
