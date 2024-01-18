package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage19 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page19);
    }

    public void goTo(){
        Intent SurveyPage20 = new Intent(this, SurveyPage20p1.class);
        startActivity(SurveyPage20);

    }

    public void goBack(){
        Intent SurveyPage18 = new Intent(this, SurveyPage18.class);
        startActivity(SurveyPage18);

    }
}