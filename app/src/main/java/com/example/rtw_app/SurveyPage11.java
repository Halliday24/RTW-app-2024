package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class SurveyPage11 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ImageView TopGreenBar;
    private ImageView BottomGreenBar;
    private CheckBox degreeCheckBox;
    private CheckBox helpOthersCheckBox;
    private CheckBox getJobCheckBox;
    private CheckBox supportFamilyCheckBox;
    private CheckBox lifeGoalCheckBox;
    private CheckBox differenceCheckBox;
    private CheckBox learnCheckBox;
    private CheckBox unsureCheckBox;
    private CheckBox otherCheckBox;
    private EditText otherReasonEditText;
    private Button backButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page11);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        TopGreenBar = findViewById(R.id.TopGreenBar);
        BottomGreenBar = findViewById(R.id.BottomGreenBar);
        degreeCheckBox = findViewById(R.id.degreeCheckBox);
        helpOthersCheckBox = findViewById(R.id.helpOthersCheckBox);
        getJobCheckBox = findViewById(R.id.getJobCheckBox);
        supportFamilyCheckBox = findViewById(R.id.supportFamilyCheckBox);
        lifeGoalCheckBox = findViewById(R.id.lifeGoalCheckBox);
        differenceCheckBox = findViewById(R.id.differenceCheckBox);
        learnCheckBox = findViewById(R.id.learnCheckBox);
        unsureCheckBox = findViewById(R.id.unsureCheckBox);
        otherCheckBox = findViewById(R.id.otherCheckBox);
        otherReasonEditText = findViewById(R.id.otherReasonEditText);
        backButton = findViewById(R.id.BackButton);
        nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectAndStoreResponses();
                goToNextPage();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    private void goBack() {
        Intent SurveyPage10 = new Intent(this, SurveyPage10.class);
        startActivity(SurveyPage10);
    }

    private void collectAndStoreResponses() {
        // Collect and store user responses in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("degreeCheckBox", degreeCheckBox.isChecked());
        editor.putBoolean("helpOthersCheckBox", helpOthersCheckBox.isChecked());
        editor.putBoolean("getJobCheckBox", getJobCheckBox.isChecked());
        editor.putBoolean("supportFamilyCheckBox", supportFamilyCheckBox.isChecked());
        editor.putBoolean("lifeGoalCheckBox", lifeGoalCheckBox.isChecked());
        editor.putBoolean("differenceCheckBox", differenceCheckBox.isChecked());
        editor.putBoolean("learnCheckBox", learnCheckBox.isChecked());
        editor.putBoolean("unsureCheckBox", unsureCheckBox.isChecked());
        editor.putString("otherReason", otherReasonEditText.getText().toString());
        editor.apply();
    }

    private void goToNextPage() {
        // Handle going to the next page or performing any necessary actions
        // For example, you can collect user input, validate, and then proceed
        //Intent nextIntent = new Intent(this, NextActivity.class); // Change NextActivity to the actual next activity
        //startActivity(nextIntent);
    }

    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof android.widget.TextView) {
                // Check if the view is a TextView
                android.widget.TextView textView = (android.widget.TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                // If the view is a ViewGroup, recursively call the method
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }


}
