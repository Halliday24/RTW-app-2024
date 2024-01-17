package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage16p1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page16p);
    }

    public void goTo(){
        Intent SurveyPage16p2 = new Intent(this, SurveyPage16p2.class);
        startActivity(SurveyPage16p2);

    }

    public void goBack(){
        Intent SurveyPage15p2 = new Intent(this, SurveyPage15p2.class);
        startActivity(SurveyPage15p2);

    }
}