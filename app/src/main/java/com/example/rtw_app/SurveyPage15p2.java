package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage15p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page15p2);
    }

    public void goTo(){
        Intent SurveyPage16p1 = new Intent(this, SurveyPage16p1.class);
        startActivity(SurveyPage16p1);

    }

    public void goBack(){
        Intent SurveyPage15 = new Intent(this, SurveyPage15.class);
        startActivity(SurveyPage15);

    }
}