package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage13 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page13);
    }

    public void goToSurveyPage14() {
        Intent SurveyPage14 = new Intent(this, SurveyPage14.class);
        startActivity(SurveyPage14);
    }
}