package com.example.daan.trivia;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class submitScore extends AppCompatActivity implements Response.Listener, Response.ErrorListener  {

    private String score;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_score);

        Intent intent = getIntent();
        this.score = Integer.toString(intent.getIntExtra("score", 0));

        TextView scoreView = findViewById(R.id.score);
        scoreView.setText("Congratulations your score is: " + score);
    }

    public void home(View view) {
        TextInputEditText nameView = findViewById(R.id.name);
        String name = nameView.getText().toString();
        this.name = name;

        String url = "https://ide50-dferdi.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(this);
        submitScore.scorePushRequest request = new submitScore.scorePushRequest(Request.Method.POST, url, this, this);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {
        Intent intent = new Intent(submitScore.this, MainActivity.class);
        startActivity(intent);
    }

    public class scorePushRequest extends StringRequest {


        public scorePushRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("score", score);
            return params;
        }
    }
}
