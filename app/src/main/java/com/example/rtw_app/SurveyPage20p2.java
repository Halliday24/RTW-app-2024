package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage20p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page20p2);
    }

    public void goTo(){
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        startActivity(SurveyPage21p1);

    }

    public void goBack(){
        Intent SurveyPage20 = new Intent(this, SurveyPage20p1.class);
        startActivity(SurveyPage20);

    }
}