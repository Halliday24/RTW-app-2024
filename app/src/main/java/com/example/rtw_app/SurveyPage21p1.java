package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage21p1 extends AppCompatActivity {

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p1);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
    }

    public void goTo(){
        Intent SurveyPage21p2 = new Intent(this, surveyPage21p2.class);
        startActivity(SurveyPage21p2);

    }

    public void goBack(){
        Intent SurveyPage20p2 = new Intent(this, SurveyPage20p2.class);
        startActivity(SurveyPage20p2);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage21p1.this, Hint.class);
        startActivity(Hint);
    }

}