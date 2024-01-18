package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPage12p1 extends AppCompatActivity {

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page12p1);
        TextView textView = (TextView) findViewById(R.id.self_efficacy_Question2);
        textView.setText("Self-Efficacy and Growth Mindset");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.self_efficacy_Option1);
        textView1.setText("Overall, I’m a pretty positive " +
                "person and I believe in my " +
                "potential");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.self_efficacy_Option2);
        textview2.setText("I believe my academic ability is " +
                "something I can substantially " +
                "change");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.self_efficacy_Option3);
        textview3.setText("Hard work, focus, and " +
                "perseverance determine my " +
                "results");

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
        Intent nextPage = new Intent(this, SurveyPage12p2.class);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent SurveyPage10 = new Intent(this, SurveyPage11.class);
        startActivity(SurveyPage10);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage12p1.this, Hint.class);
        startActivity(Hint);
    }
}