package com.example.daan.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View view) {
        Intent intent = new Intent(MainActivity.this, game.class);
        startActivity(intent);
    }

    public void getHighscores(View view) {
        Intent intent = new Intent(MainActivity.this, highScores.class);
        startActivity(intent);
    }

}
