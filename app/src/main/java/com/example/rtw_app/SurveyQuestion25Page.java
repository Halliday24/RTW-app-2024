package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//this file is responsible for the end message that will be displayed once completed the workbook
public class SurveyQuestion25Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Button buttonNext=findViewById(R.id.nextButton);

        //set a click listener for the next Button
        buttonNext.setOnClickListener(new View.OnClickListener(){

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
        Intent NextPage = new Intent(this, SurveyQuestions26and27Page.class);
        startActivity(NextPage);
    }

    public void goBack(){
        Intent NextPage = new Intent(this, SurveyQuestions26and27Page.class);
        startActivity(NextPage);
    }
}