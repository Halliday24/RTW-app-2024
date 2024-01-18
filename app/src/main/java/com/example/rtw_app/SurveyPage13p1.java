package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPage13p1 extends AppCompatActivity {
//need to add Education and Goals to top header
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page13p1);

        //option1
        TextView textView1 = (TextView) findViewById(R.id.Education_And_Goals_Option1);
        textView1.setText("I want to complete a degree");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.Education_And_Goals_Option2);
        textview2.setText("I feel confident in my major " +
                "choice  ");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.Education_And_Goals_Option3);
        textview3.setText("I am considering graduate " +
                "school");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.Education_And_Goals_Option4);
        textview4.setText("I’m actively exploring career " +
                "options and pursuing " +
                "opportunities to gain relevant " +
                "experience ");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.Education_And_Goals_Option5);
        textview5.setText("I know what it takes to be in the " +
                "career I have chosen ");

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToNextPage();
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

    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage13p2.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Self_efficacy2 = new Intent(this, SurveyPage12p2.class);
        startActivity(Self_efficacy2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage13p1.this, Hint.class);
        startActivity(Hint);
    }
}