package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class SurveyPage2 extends AppCompatActivity {

    private int currentQuestion;

    //changed to 3 since only 3 questions are on page
    private int totalQuestions = 3; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private SharedPreferences sharedPreferences;
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentQuestion = extras.getInt("data1");

        }

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        updateProgress();
        hint = findViewById(R.id.hint);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup studyRadioGroup = findViewById(R.id.studyRadioGroup);
        final RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        final RadioGroup poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion++;
                updateProgress();


                int selectedHoursId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedLateId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedUnemployedId = poorStudyRadioGroup2.getCheckedRadioButtonId();

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

                currentQuestion--;
                updateProgress();
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
        SurveyPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage3);

        Intent myIntent = new Intent(SurveyPage2.this, SurveyPage3.class);
        myIntent.putExtra("data1", currentQuestion);
        SurveyPage2.this.startActivity(myIntent);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p3.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

        Intent myIntent = new Intent(SurveyPage2.this, SurveyPage1p3.class);
        myIntent.putExtra("data1", currentQuestion);
        SurveyPage2.this.startActivity(myIntent);

    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage2.this, Hint.class);
        startActivity(Hint);
    }
}