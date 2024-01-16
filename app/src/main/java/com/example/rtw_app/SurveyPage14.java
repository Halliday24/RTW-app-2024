package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPage14 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_management);

        //option1
        TextView textView1 = (TextView) findViewById(R.id.TimeManagement_Option1);
        textView1.setText("I make plans each week about " +
                "how I will spend my time");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.TimeManagement_Option2);
        textview2.setText("I follow through with the plans I " +
                "make around how I spend my " +
                "time");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.TimeManagement_Option3);
        textview3.setText("I complete major assignments " +
                "and hand them in on time ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.TimeManagement_Option4);
        textview4.setText("I study at least 2 hours for " +
                "every hour I spend in class");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.TimeManagement_Option5);
        textview5.setText("I can estimate how much time " +
                "a task is going to take");

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToTimeManagement2();
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

    public void goToTimeManagement2(){
        Intent TimeManagement2 = new Intent(this, SurveyPage14p2.class);
        startActivity(TimeManagement2);

    }

    public void goBack(){
        Intent Education_And_Goals2 = new Intent(this, SurveyPage13p2.class);
        startActivity(Education_And_Goals2);

    }
}