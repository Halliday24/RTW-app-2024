package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage20p1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page20);
    }

    public void goTo(){
        Intent SurveyPage20p2 = new Intent(this, SurveyPage20p2.class);
        startActivity(SurveyPage20p2);

    }

    public void goBack(){
        Intent SurveyPage19 = new Intent(this, SurveyPage19.class);
        startActivity(SurveyPage19);

    }
}