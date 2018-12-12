package com.example.daan.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class triviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;
    private ArrayList<Question> questionList = new ArrayList<>();

    public interface Callback {
        void gotQuestions(ArrayList<Question> MenuList);
        void gotQuestionsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }


    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray questions = response.getJSONArray("results");
            for(int i = 0; i<questions.length(); i++){
                JSONObject question = questions.getJSONObject(i);
                Question q = new Question(question.getString("question"),
                        question.getString("correct_answer"),
                        question.getJSONArray("incorrect_answers"));
                questionList.add(q);
            }
            activity.gotQuestions(questionList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public triviaRequest(Context context){
        this.context = context;
    }

    public void getQuestions(Callback activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=10&category=9&difficulty=medium&type=multiple", null, this, this);
        queue.add(jsonObjectRequest);
        this.activity = activity;
    }
}
