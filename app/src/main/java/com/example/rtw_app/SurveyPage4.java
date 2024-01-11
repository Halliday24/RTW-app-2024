package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
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

        Button nextButton = findViewById(R.id.nextButton);


        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToImpactFinancial();
            }
        });

        Button buttonBack=findViewById(R.id.BackButton);

        //set a click listener for the next Button
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof TextView) {
                // Check if the view is a TextView
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                // If the view is a ViewGroup, recursively call the method
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }

    }

    public void goToImpactFinancial(){
        Intent SurveyPage4 = new Intent(this, SurveyPage6.class);
        startActivity(SurveyPage4);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage3.class);
        startActivity(impactAcademicPage2);

    }
}