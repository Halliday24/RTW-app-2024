package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Self_efficacy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_self_efficacy);
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

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToself_efficacy2();
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

    public void goToself_efficacy2(){
        Intent Self_efficacy2 = new Intent(this, Self_efficacy2.class);
        startActivity(Self_efficacy2);

    }

    public void goBack(){
        Intent SurveyPage10 = new Intent(this, SurveyPage10.class);
        startActivity(SurveyPage10);

    }
}