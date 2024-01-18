package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SurveyPage14p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page14p2);
    }



    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage15p1.class);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Education_And_Goals2 = new Intent(this, SurveyPage14p1.class);
        startActivity(Education_And_Goals2);

    }
}