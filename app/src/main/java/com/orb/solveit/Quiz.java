package com.orb.solveit;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class Quiz {
    private JSONObject quizData;
    private JSONArray quizArray;
    private JSONObject currentQuiz;
    private int currentPosition = -1;
    private int quizCount = 0;

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int answer;

    Quiz(Context context, String filename) {
        try {
            InputStream inputStream = (context).getAssets().open(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                buffer.append(string);
            }

            this.quizData = new JSONObject(buffer.toString());
            this.quizArray = this.quizData.getJSONArray("data");
            quizArray=shuffleJsonArray(quizArray);
            quizCount = quizArray.length();

        } catch (IOException | JSONException e1) {
            e1.printStackTrace();
        }
    }

    public void nextQuiz() {
        currentPosition++;
        try {
            currentQuiz = quizArray.getJSONObject(currentPosition);
            question = currentQuiz.getString("question");
            JSONObject options = currentQuiz.getJSONObject("options");

            optionA = options.getString("1");
            optionB = options.getString("2");
            optionC = options.getString("3");
            optionD = options.getString("4");
            answer = currentQuiz.getInt("answer");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public boolean hasNext() {
        return (currentPosition < (quizCount - 1));

    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public int getAnswer() {
        return answer;
    }

    public static JSONArray shuffleJsonArray(JSONArray jsonArray) throws JSONException {
        Random random = new Random();
        for (int i = jsonArray.length() - 1; i >= 0; i--) {
            int j = random.nextInt(i + 1);
            Object object = jsonArray.get(j);
            jsonArray.put(j, jsonArray.get(i));
            jsonArray.put(i, object);
        }
        return jsonArray;

    }
}
