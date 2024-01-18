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

public class SurveyPage7 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page7);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage7_Question);
        textView.setText("How much of an impact did each of these potential transportation barriers have on your " +
                "ability to participate in your education:");
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


        Button buttonNext=findViewById(R.id.nextButton);
        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        //set a click listener for the next Button
        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToSurveyPage8();
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

    public void goToSurveyPage8(){
        Intent SurveyPage8 = new Intent(this, SurveyPage8.class);
        SurveyPage8.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage8);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage6p4.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage7.this, Hint.class);
        startActivity(Hint);
    }
}