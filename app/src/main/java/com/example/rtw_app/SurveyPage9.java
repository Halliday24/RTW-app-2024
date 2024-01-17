package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;
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

public class SurveyPage9 extends AppCompatActivity {
//XML Should have Values and Goals on top but is fine regardless
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page9);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup purposeRadioGroup = findViewById(R.id.purposeRadioGroup);
        final RadioGroup confidenceRadioGroup = findViewById(R.id.confidenceRadioGroup);
        final RadioGroup goalsRadioGroup = findViewById(R.id.goalsRadioGroup);
        final RadioGroup valuesRadioGroup = findViewById(R.id.valuesRadioGroup);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPurposeId = purposeRadioGroup.getCheckedRadioButtonId();
                int selectedConfidenceId = confidenceRadioGroup.getCheckedRadioButtonId();
                int selectedGoalsId = goalsRadioGroup.getCheckedRadioButtonId();
                int selectedValuesId = valuesRadioGroup.getCheckedRadioButtonId();

                if (selectedPurposeId != -1 && selectedConfidenceId != -1
                        && selectedGoalsId != -1 && selectedValuesId != -1) {
                    // Get selected answers
                    String selectedPurpose = ((RadioButton) findViewById(selectedPurposeId)).getText().toString();
                    String selectedConfidence = ((RadioButton) findViewById(selectedConfidenceId)).getText().toString();
                    String selectedGoals = ((RadioButton) findViewById(selectedGoalsId)).getText().toString();
                    String selectedValues = ((RadioButton) findViewById(selectedValuesId)).getText().toString();

                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Purpose", selectedPurpose);
                    editor.putString("Confidence", selectedConfidence);
                    editor.putString("Goals", selectedGoals);
                    editor.putString("Values", selectedValues);
                    editor.apply();

                    // Display a success message
                    Toast.makeText(SurveyPage9.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Go to Next page
                    goToSurveyPage3();

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage9.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Back button click
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

    public void goToSurveyPage3() {
        Intent surveyPage3 = new Intent(this, SurveyPage10.class);
        surveyPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(surveyPage3);
    }

    public void goBack() {
        Intent Mindset = new Intent(this, MindsetPage.class);
        Mindset.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Mindset);
    }
}
