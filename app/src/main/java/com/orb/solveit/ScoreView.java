package com.orb.solveit;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreView extends AppCompatActivity {
    TextView score, highScore;
    Button exitBtn, replayBtn;
    MediaPlayer mediaPlayer;
    int gameScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_view);

        exitBtn = (Button) findViewById(R.id.exitBtn);
        replayBtn = (Button) findViewById(R.id.replayBtn);
        score = (TextView) findViewById(R.id.score);
        highScore = (TextView) findViewById(R.id.highScore);


        exitBtn.setVisibility(View.INVISIBLE);
        replayBtn.setVisibility(View.INVISIBLE);


        Intent receivedIntent = getIntent();
        if (receivedIntent.getAction() == "SHOW_SCORE") {

            gameScore = receivedIntent.getIntExtra("score", 0);

            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setObjectValues(0, gameScore);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    score.setText("" + animation.getAnimatedValue());

                }
            });
            valueAnimator.setDuration(1500);
            valueAnimator.start();

        }


        mediaPlayer = MediaPlayer.create(this, R.raw.inspire);
        mediaPlayer.setLooping(false);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.reset();
                mediaPlayer = null;

                exitBtn.setVisibility(View.VISIBLE);
                replayBtn.setVisibility(View.VISIBLE);

            }
        });


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // get the previous highscore
        SharedPreferences sp = getSharedPreferences("score", MODE_PRIVATE);
        int prevHighScore;
        prevHighScore = sp.getInt("high_score", 0);


        if (gameScore > prevHighScore) {
            highScore.setText("New HighScore !!! ");
            mediaPlayer.start();

            // save the new high Score
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("high_score", gameScore);
            editor.apply();


        } else {
            highScore.setText("High Score: " + prevHighScore);
            exitBtn.setVisibility(View.VISIBLE);
            replayBtn.setVisibility(View.VISIBLE);
        }


        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreView.this, GamePlay.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
