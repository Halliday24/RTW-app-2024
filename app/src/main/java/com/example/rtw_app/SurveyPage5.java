package com.example.rtw_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//5 should be Please indicate how much of an impact each of these potential financial barriers had
// on your ability to participate in your education but the s=xml is still correct just missing words
public class SurveyPage5 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page5);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        // Initialize UI elements
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                //handleNextButtonClick();
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

    private void handleNextButtonClick() {
        RadioGroup studyRadioGroup = findViewById(R.id.studyRadioGroup);
        RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        RadioGroup poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);
        RadioGroup disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        // Get selected answers
        String selectedMoney = getSelectedRadioButtonText(studyRadioGroup);
        String selectedAid = getSelectedRadioButtonText(timeRadioGroup);
        String selectedManagement = getSelectedRadioButtonText(poorStudyRadioGroup2);
        String selectedTools = getSelectedRadioButtonText(disabilityRadioGroup);

        // Store responses in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Worried about money", selectedMoney);
        editor.putString("Inadequate financial aid/scholarship", selectedAid);
        editor.putString("Limited financial management skills", selectedManagement);
        editor.putString("Unable to purchase textbooks, laptop, or other necessary learning tools", selectedTools);
        editor.apply();

        // Display a success message
        Toast.makeText(SurveyPage5.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to the next page
        goToNextPage();


    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    private void goToNextPage() {
        Intent intent = new Intent(this, SurveyPage6.class); // Replace with the actual next activity
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage4.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }
}
