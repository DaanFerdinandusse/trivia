package com.example.daan.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class highScores extends AppCompatActivity implements highscoreRequest.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        highscoreRequest h = new highscoreRequest(this);
        h.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<String> Scores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.scores, Scores);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
