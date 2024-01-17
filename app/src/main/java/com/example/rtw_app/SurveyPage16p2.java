package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage16p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page16p2);
    }

    public void goTo(){
        Intent SurveyPage17 = new Intent(this, SurveyPage17.class);
        startActivity(SurveyPage17);

    }

    public void goBack(){
        Intent SurveyPage16p1 = new Intent(this, SurveyPage16p1.class);
        startActivity(SurveyPage16p1);

    }
}