package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPage12p2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page12p2);

        //option1
        TextView textView1 = (TextView) findViewById(R.id.self_efficacy2_Option1);
        textView1.setText("I don’t believe in limitations and  " +
                "                I can create opportunities for " +
                "                myself");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.self_efficacy2_Option2);
        textview2.setText("I love trying new things and " +
                "taking on new challenges ");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.self_efficacy2_Option3);
        textview3.setText("Failure doesn’t define me and I " +
                "can learn from my mistakes");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.self_efficacy2_Option4);
        textview4.setText("I avoid negative self-talk and " +
                "speak constructively about " +
                "myself and others ");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.self_efficacy2_Option5);
        textview5.setText(" Challenges are opportunities " +
                "for growth");

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToEducation_And_Goals();
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

    public void goToEducation_And_Goals(){
        Intent Education_And_Goals = new Intent(this, SurveyPage13p1.class);
        startActivity(Education_And_Goals);

    }

    public void goBack(){
        Intent Self_efficacy = new Intent(this, SurveyPage12p1.class);
        startActivity(Self_efficacy);

    }
}