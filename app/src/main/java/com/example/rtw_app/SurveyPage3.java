package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;


public class SurveyPage3 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page3);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
       // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage3_Question);
        textView.setText("How did each of these potential major-related barriers impact your education?");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage3_Option1);
        textView1.setText("I was not admitted to my first choice program");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage3_Option2);
        textview2.setText("Unclear education or career goals");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage3_Option3);
        textview3.setText("Pressure to choose a path that was not a good fit for me");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage3_Option4);
        textview4.setText("Major related classes were unavailable");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.surveyPage3_Option5);
        textview5.setText("Major not offered");
        //option6
        TextView textview6 = (TextView) findViewById(R.id.surveyPage3_Option6);
        textview6.setText("Unhappy with major");


        Button submitButton = findViewById(R.id.nextButton);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToSurveyPage4();
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
    public void goToSurveyPage4(){
        Intent SurveyPage4 = new Intent(this, SurveyPage4.class);
        startActivity(SurveyPage4);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage2.class);
        startActivity(impactAcademicPage2);

    }
        }