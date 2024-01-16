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

    public void goToSurveyPage14p2() {
        Intent SurveyPage14p2 = new Intent(this, SurveyPage16p2.class);
        startActivity(SurveyPage14p2);
    }
}