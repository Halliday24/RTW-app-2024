package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage21p1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p1);
    }

    public void goTo(){
        Intent SurveyPage21p2 = new Intent(this, surveyPage21p2.class);
        startActivity(SurveyPage21p2);

    }

    public void goBack(){
        Intent SurveyPage20p2 = new Intent(this, SurveyPage20p2.class);
        startActivity(SurveyPage20p2);

    }
}