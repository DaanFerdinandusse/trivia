package com.example.daan.trivia;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    private String question;
    private String correct;
    private ArrayList<String> options = new ArrayList<>();

    public Question(String question, String correct, JSONArray incorrect) {
        this.question = question;
        this.correct = correct;
        for(int i =0; i<incorrect.length(); i++){
            try {
                this.options.add(incorrect.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.options.add(correct);
        Collections.shuffle(options);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }


    public ArrayList getOptions(){
        return options;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
