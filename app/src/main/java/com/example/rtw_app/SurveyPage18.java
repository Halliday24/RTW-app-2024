package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage18 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page18);
    }

    public void goTo(){
        Intent SurveyPage19 = new Intent(this, SurveyPage19.class);
        startActivity(SurveyPage19);

    }

    public void goBack(){
        Intent SurveyPage17 = new Intent(this, SurveyPage17.class);
        startActivity(SurveyPage17);

    }
}