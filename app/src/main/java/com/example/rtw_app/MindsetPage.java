package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MindsetPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindset_page);

        //Set the textview to dislay the message for the mindset page.
        TextView textView = (TextView) findViewById(R.id.Heading) ;
        textView.setText("Mindset");
        TextView textView2 = (TextView) findViewById(R.id.Mindset);
        textView2.setText("Building a positive attitude and a growth mindset are important skills to help you achieve " +
                "your goals.\n"+ "\nThese next sections will help you reflect on your current mindsets and help " +
                "you start thinking about your goals and how you can start achieving them. " +
                "\n \nPlease continue to actively answer these questions as best as you can. "); //set text for text view

        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener(){

            /**
             * This method increments the current question integer if this page hasn't been visited
             * before, checks if all the options are answered.
             * If they are then it saves the user's answers to the shared preferences and moves to the next page.
             * @param view
             */
            @Override
            public void onClick(View view) {
                goToNextPage();
            }
        });


        Button buttonBack=findViewById(R.id.buttonBack);

        //set a click listener for the next Button
        buttonBack.setOnClickListener(new View.OnClickListener(){

            /**
             * This method takes the user back to the previous page once the back button has been clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {

                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }





    /**
     * This method sets the color for all the members in a specific viewGroup.
     * @param viewGroup
     * @param color
     */
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

    /**
     * This method links this page to the next page, SurveyPage9
     */
    public void goToNextPage(){
        Intent SurveyPage9 = new Intent(this, SurveyPage9.class);
        SurveyPage9.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage9);

    }

    /**
     * This method links this page to the previous page, surveyPage8
     */
    public void goBack(){
        Intent SurveyPage8 = new Intent(this, SurveyPage8.class);
        SurveyPage8.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage8);

    }
}