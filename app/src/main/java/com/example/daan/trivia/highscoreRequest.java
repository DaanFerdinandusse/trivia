package com.example.daan.trivia;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class highscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener  {

    private Context context;
    private Callback activity;
    private ArrayList<String> scores = new ArrayList<>();

    public interface Callback {
        void gotHighscores(ArrayList<String> Scores);
        void gotHighscoresError(String message);
    }

    public highscoreRequest(Context context){
        this.context = context;
    }

    @Override
    public void onResponse(JSONArray response) {
        ArrayList<JSONObject> sortedList = sortScores(response);
        for(int i = 0; i<sortedList.size(); i++){
            try {
                JSONObject scoreItem = sortedList.get(i);
                scores.add(scoreItem.getString("name") + ": " + scoreItem.getInt("score"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        activity.gotHighscores(scores);
    }

    public ArrayList<JSONObject> sortScores(JSONArray response){
        ArrayList<JSONObject> highscoreList = new ArrayList<JSONObject>();
        for (int i = 0; i < response.length(); i++) {
            try {
                highscoreList.add(response.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(highscoreList, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject scoreA, JSONObject scoreB) {
                int compare = 0;
                try
                {
                    int keyA = scoreA.getInt("score");
                    int keyB = scoreB.getInt("score");
                    compare = Integer.compare(keyA, keyB);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                return -compare;
            }
        });
        return highscoreList;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
    }

    public void getHighscores(highscoreRequest.Callback activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest("https://ide50-dferdi.cs50.io:8080/list", this, this);
        queue.add(jsonObjectRequest);
        this.activity = activity;
    }
}
