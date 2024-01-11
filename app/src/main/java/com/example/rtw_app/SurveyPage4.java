package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;

public class SurveyPage4 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page4);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage4_Question);
        textView.setText("How much of an impact did each of these potential free-time barriers" +
                " have on your ability to participate in your education:");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage4_Option1);
        textView1.setText("Social Media (Facebook, " +
                "Instagram, YouTube, " +
                "Snapchat, TikTok, Reddit, etc)");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage4_Option2);
        textview2.setText("Too much screen time (video " +
                "games, streaming, internet, " +
                "etc)");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage4_Option3);
        textview3.setText("A very active social life ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage4_Option4);
        textview4.setText("Overextended in my " +
                "extracurricular activities");

        Button nextButton = findViewById(R.id.submitButton);

        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToImpactFinancial();
            }
        });
    }

    public void goToImpactFinancial(){
        Intent SurveyPage4 = new Intent(this, ImpactFinancial.class);
        startActivity(SurveyPage4);

    }
}