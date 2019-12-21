package com.orb.solveit;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GamePlay extends AppCompatActivity implements View.OnClickListener {
    TextView timeCount, titleView, questionView, scoreView, lifeView, questionCount;
    Button optionA, optionB, optionC, optionD;
    Quiz quiz;
    int life, score;
    CountDownTimer countDownTimer;
    Context context;
    boolean z = true;
    boolean indicatorInProgress = false;
    int totalQuestionsAsked, rightAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);


        life = 3;
        score = 0;
        totalQuestionsAsked = 0;
        rightAnswer = 0;


        context = this;
        questionCount = (TextView) findViewById(R.id.questionCount);
        lifeView = (TextView) findViewById(R.id.lifeView);
        timeCount = (TextView) findViewById(R.id.time_count);
        questionView = (TextView) findViewById(R.id.question);
        scoreView = (TextView) findViewById(R.id.scoreView);
        optionA = (Button) findViewById(R.id.option1);
        optionB = (Button) findViewById(R.id.option2);
        optionC = (Button) findViewById(R.id.option3);
        optionD = (Button) findViewById(R.id.option4);

        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);


        titleView = (TextView) findViewById(R.id.titleView);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/michroma.ttf");
        titleView.setTypeface(myCustomFont);


        countDownTimer = new CountDownTimer(11000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeCount.setText(String.valueOf((millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                life--;
                setQuiz();

            }

        };

        quiz = new Quiz(this, "data/computer.json");
        setQuiz();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        Button Selected, Right;
        Selected = (Button) v;
        Right = (Button) v;


        if (!indicatorInProgress) {
            indicatorInProgress = true;
            countDownTimer.cancel();
            if (id == R.id.option4 || id == R.id.option2 || id == R.id.option3 || id == R.id.option1) {
                countDownTimer.cancel();


                int answer = 0;
                if (id == R.id.option1) {
                    answer = 1;
                } else if (id == R.id.option2) {
                    answer = 2;
                } else if (id == R.id.option3) {
                    answer = 3;
                } else if (id == R.id.option4) {
                    answer = 4;
                }


                switch (quiz.getAnswer()) {
                    case 1:
                        Right = (Button) findViewById(R.id.option1);
                        break;
                    case 2:
                        Right = (Button) findViewById(R.id.option2);
                        break;
                    case 3:
                        Right = (Button) findViewById(R.id.option3);
                        break;
                    case 4:
                        Right = (Button) findViewById(R.id.option4);
                        break;
                    default:
                        break;
                }


                if (answer == quiz.getAnswer()) {
                    score += 5;
                    rightAnswer++;

                    showRightAnswer(Right);

                } else {
                    life--;
                    showRightAnswer(Right);
                    showWrongAnswer(Selected);

                }

            }


            // showing the right answer and setting the next question


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    public void showRightAnswer(final Button Right) {
        final Drawable x = Right.getBackground();
        Right.setBackground(ContextCompat.getDrawable(context, R.drawable.right_answeer_indicator));

        new CountDownTimer(1500, 1200) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Right.setBackground(x);
                setQuiz();
                indicatorInProgress = false;
            }
        }.start();

    }

    public void showWrongAnswer(final Button Wrong) {
        final Drawable drawablePreserved = Wrong.getBackground();

        new CountDownTimer(1400, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (z) {
                    Wrong.setBackground(ContextCompat.getDrawable(context, R.drawable.wrong_answer_indicator));
                    z = !z;

                } else {
                    Wrong.setBackground(drawablePreserved);
                    z = !z;
                }

            }

            @Override
            public void onFinish() {
                z = true;
                Wrong.setBackground(drawablePreserved);

            }
        }.start();


    }

    public void setQuiz() {
        // setting the score
        scoreView.setText("Score: " + score);
        lifeView.setText("+" + life);
        questionCount.setText(rightAnswer + "/" + totalQuestionsAsked);

        if (life > 0 && quiz.hasNext()) {


            quiz.nextQuiz();
            questionView.setText(quiz.getQuestion());
            optionA.setText(quiz.getOptionA());
            optionB.setText(quiz.getOptionB());
            optionC.setText(quiz.getOptionC());
            optionD.setText(quiz.getOptionD());
            countDownTimer.start();
            totalQuestionsAsked++;

        } else {
            // show score and end the game
            countDownTimer.cancel();
            Intent showScore = new Intent(this, ScoreView.class);
            showScore.putExtra("score", score);
            showScore.setAction("SHOW_SCORE");
            startActivity(showScore);
            finish();
        }
    }


}