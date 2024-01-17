package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPage10 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page10);

        TextView textView = (TextView) findViewById(R.id.surveyPage9_Question);
        textView.setText("Attitude and Motivation");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage9_Option1);
        textView1.setText("Class lectures and discussion " +
                "stimulate me");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage9_Option2);
        textview2.setText("I enjoy and want to be at " +
                "university ");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage9_Option3);
        textview3.setText("There are one or two subjects " +
                "in school that I always enjoy  ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage9_Option4);
        textview4.setText(" I attend class ");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.surveyPage9_Option5);
        textview5.setText("I am engaged in class and in " +
                "small group discussions");



        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goTohere_at_uofa_page();
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

    public void goTohere_at_uofa_page(){
        Intent here_at_uofa_page = new Intent(this, SurveyPage11.class);
        here_at_uofa_page.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(here_at_uofa_page);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage9.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }

}