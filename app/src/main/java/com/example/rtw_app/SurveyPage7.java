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
public class SurveyPage7 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page7);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage7_Question);
        textView.setText("How much of an impact did each of these potential transportation barriers have on your " +
                "experience last year:");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage7_Option1);
        textView1.setText("Long commute times to " +
                "campus");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage7_Option2);
        textview2.setText("Lack of personal vehicle to get " +
                "to campus");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage7_Option3);
        textview3.setText("Challenges getting to shops to " +
                "purchase required supplies ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage7_Option4);
        textview4.setText("Transportation costs");
    }
}