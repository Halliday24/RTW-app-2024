package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class SurveyPage2 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page2);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup hoursRadioGroup = findViewById(R.id.hoursRadioGroup);
        final RadioGroup lateRadioGroup = findViewById(R.id.lateRadioGroup);
        final RadioGroup unemployedRadioGroup = findViewById(R.id.unemployedRadioGroup);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHoursId = hoursRadioGroup.getCheckedRadioButtonId();
                int selectedLateId = lateRadioGroup.getCheckedRadioButtonId();
                int selectedUnemployedId = unemployedRadioGroup.getCheckedRadioButtonId();

                if (selectedHoursId != -1 && selectedLateId != -1 && selectedUnemployedId != -1) {
                    // Get selected answers
                    String selectedHours = ((RadioButton) findViewById(selectedHoursId)).getText().toString();
                    String selectedLate = ((RadioButton) findViewById(selectedLateId)).getText().toString();
                    String selectedUnemployed = ((RadioButton) findViewById(selectedUnemployedId)).getText().toString();
                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Work Too many hours", selectedHours);
                    editor.putString("Work late hours, or schedules\n" +
                            "that conflict with class time", selectedLate);
                    editor.putString("Unemployed", selectedUnemployed);
                    editor.apply();

                    //


                    // Display a success message
                    Toast.makeText(SurveyPage2.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

//                    // Check if the app has permission to write to external storage
//                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        // Request permission
//                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    } else {
//                        // Permission already granted, proceed with generating PDF
//                        generatePDF(selectedColor, selectedProgramming);
//                    }
//
//                    generatePDF(selectedColor, selectedProgramming);

                    // Go to Next page
                    goToSurveyPage3();

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage2.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
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

    public void goToSurveyPage3(){
        Intent SurveyPage3 = new Intent(this, SurveyPage3.class);
        startActivity(SurveyPage3);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p3.class);
        startActivity(impactAcademicPage2);

    }
}