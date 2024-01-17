package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage17 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page17);
    }

    public void goTo(){
        Intent SurveyPage18 = new Intent(this, SurveyPage18.class);
        startActivity(SurveyPage18);

    }

    public void goBack(){
        Intent SurveyPage16p2 = new Intent(this, SurveyPage16p2.class);
        startActivity(SurveyPage16p2);

    }
}