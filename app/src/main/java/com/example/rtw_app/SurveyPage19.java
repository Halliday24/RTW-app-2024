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

    public void goToPAGE(){
        Intent PAGE = new Intent(this, SurveyPage21p1.class);
        startActivity(PAGE);

    }

    public void goBack(){
        Intent timeManagement = new Intent(this, SurveyPage14p1.class);
        startActivity(timeManagement);

    }
}