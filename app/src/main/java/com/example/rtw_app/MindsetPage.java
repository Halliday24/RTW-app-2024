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

        TextView textView = (TextView) findViewById(R.id.Heading) ;
        textView.setText("Mindset");
        TextView textView2 = (TextView) findViewById(R.id.Mindset);
        textView2.setText("Building a positive attitude and a growth mindset are important skills to help you achieve " +
                "your goals. These next sections will help you reflect on your current mindsets and help " +
                "you start thinking about your goals and how you can start achieving them. " +
                "Please indicate how often the statements below are true for you: "); //set text for text view

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goToSurveyPage9();
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

    public void goToSurveyPage9(){
        Intent SurveyPage9 = new Intent(this, SurveyPage10.class);
        startActivity(SurveyPage9);

    }

    public void goBack(){
        Intent SurveyPage8 = new Intent(this, SurveyPage8.class);
        startActivity(SurveyPage8);

    }
}