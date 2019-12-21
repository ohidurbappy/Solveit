package com.orb.solveit;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScore extends AppCompatActivity {

    TextView highScoreView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        backButton = (Button) findViewById(R.id.backBtn);
        backButton.setVisibility(View.INVISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        int highScore = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
        highScore = sharedPreferences.getInt("high_score", 0);


        highScoreView = (TextView) findViewById(R.id.score);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(0, highScore);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                highScoreView.setText("" + animation.getAnimatedValue());
            }
        });

        valueAnimator.setDuration(1500);
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
}
