package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class surveyPage21p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p2);
    }

    public void goTo(){
        Intent EndPage = new Intent(this, EndPage.class);
        startActivity(EndPage);

    }

    public void goBack(){
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        startActivity(SurveyPage21p1);

    }
}