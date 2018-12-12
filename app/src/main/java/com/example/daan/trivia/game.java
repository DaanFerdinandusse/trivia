package com.example.daan.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class game extends AppCompatActivity implements triviaRequest.Callback{

    private ArrayList<Question> questionList;
    private int qNumber = 1 ;
    private int qIndex = 0;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        triviaRequest t = new triviaRequest(this);
        t.getQuestions(this);
    }

    @Override
    public void gotQuestions(ArrayList<Question> MenuList) {
        this.questionList = MenuList;
        Question q1 = questionList.get(qIndex);
        TextView question = findViewById(R.id.question);
        question.setText(q1.getQuestion());
        setOptions(q1);
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setOptions(Question q){
        ArrayList<String> options = q.getOptions();
        TextView option1 = findViewById(R.id.option1);
        TextView option2 = findViewById(R.id.option2);
        TextView option3 = findViewById(R.id.option3);
        TextView option4 = findViewById(R.id.option4);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));
    }

    public void next(View view) {
        RadioGroup answerID = findViewById(R.id.answer);
        RadioButton answerButton = findViewById(answerID.getCheckedRadioButtonId());
        if(answerButton.getText().toString().equals(questionList.get(qIndex).getCorrect())){
            score += 10;
            TextView s = findViewById(R.id.score);
            s.setText("Score: " + score);
        }
        qIndex++;
        qNumber++;
        if(qIndex<questionList.size()){
            Question q = questionList.get(qIndex);
            TextView count = findViewById(R.id.count);

            count.setText("Question" + qNumber);
            TextView question = findViewById(R.id.question);
            question.setText(q.getQuestion());
            setOptions(q);
        } else{
            Intent intent = new Intent(game.this, submitScore.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }

    }
}
