package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
//written response section under survey page 7
public class SurveyPage8 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page8);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        TextView textView = (TextView) findViewById(R.id.surveyPage8_Question);
        textView.setText("Please describe any other barriers, including any extenuating circumstances, you " +
                "may have encountered. For example: family emergencies or child care issues. ");

        TextView textView2 = (TextView) findViewById(R.id.surveyPage8_Question2);
        textView2.setText("What are the primary sources of stress in your life?");




        Button buttonNext=findViewById(R.id.nextButton);

        //set a click listener for the next Button
        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToMindsetPage();
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

    public void goToMindsetPage(){
        Intent MindsetPage = new Intent(this, MindsetPage.class);
        startActivity(MindsetPage);

    }

    public void goBack(){
        Intent SurveyPage7 = new Intent(this, SurveyPage7.class);
        startActivity(SurveyPage7);

    }
}