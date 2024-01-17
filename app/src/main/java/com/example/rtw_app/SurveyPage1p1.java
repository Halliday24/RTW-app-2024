package com.example.rtw_app;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ProgressBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;


public class SurveyPage1p1 extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    private int currentQuestion = 1;
    private int totalQuestions = 5; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup, preparationRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentQuestion = extras.getInt("data1");

        }

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        updateProgress();


        // Initialize your RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        preparationRadioGroup = findViewById(R.id.preparationRadioGroup);
        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion++;
                updateProgress();
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
                int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();


                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1 && selectedPreparationId != -1) {


                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();
                    String selectedPreparation = ((RadioButton) findViewById(selectedPreparationId)).getText().toString();


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putString("impact_color5", selectedPreparation);
                    editor.apply();


                    Toast.makeText(SurveyPage1p1.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactAcademicPage2();
                } else {
                    Toast.makeText(SurveyPage1p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion--;
                updateProgress();
                goBack();
            }
        });


        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        int selectedStudyId = studyRadioGroup.getCheckedRadioButtonId();
        int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
        int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
        int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
        int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();


        outState.putInt("selectedStudyId", selectedStudyId);
        outState.putInt("selectedTimeId", selectedTimeId);
        outState.putInt("selectedPoorStudyId", selectedPoorStudyId);
        outState.putInt("selectedDisabilityId", selectedDisabilityId);
        outState.putInt("selectedPreparationId", selectedPreparationId);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        int selectedStudyId = savedInstanceState.getInt("selectedStudyId");
        int selectedTimeId = savedInstanceState.getInt("selectedTimeId");
        int selectedPoorStudyId = savedInstanceState.getInt("selectedPoorStudyId");
        int selectedDisabilityId = savedInstanceState.getInt("selectedDisabilityId");
        int selectedPreparationId = savedInstanceState.getInt("selectedPreparationId");


        studyRadioGroup.check(selectedStudyId);
        timeRadioGroup.check(selectedTimeId);
        poorStudyRadioGroup.check(selectedPoorStudyId);
        disabilityRadioGroup.check(selectedDisabilityId);
        preparationRadioGroup.check(selectedPreparationId);
    }


    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof TextView) {
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }


    public void goToImpactAcademicPage2() {
        updateProgress();
        Intent myIntent = new Intent(SurveyPage1p1.this, SurveyPage1p2.class);
        myIntent.putExtra("data1", currentQuestion);
        SurveyPage1p1.this.startActivity(myIntent);

        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);


    }


    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, InstructionPage.class);
        startActivity(impactAcademicPage2);

        Intent myIntent = new Intent(SurveyPage1p1.this, InstructionPage.class);
        myIntent.putExtra("data1", currentQuestion);
        SurveyPage1p1.this.startActivity(myIntent);
    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}

