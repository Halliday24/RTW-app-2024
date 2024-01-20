package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage1p3 extends AppCompatActivity {

    private int currentQuestion;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup2, disabilityRadioGroup;
    private int totalQuestions = 5; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;

    private String userInfo;
    private SharedPreferences sharedPreferences;

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p3);
        userInfo = getIntent().getStringExtra("userInfo");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentQuestion = extras.getInt("data1");

        }

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        updateProgress();


        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);

        Button submitButton = findViewById(R.id.nextButton);


        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion++;
                updateProgress();
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup2.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();



                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1) {


                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();



                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.apply();


                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability);

                    Toast.makeText(SurveyPage1p3.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactWorkPage();
                } else {
                    Toast.makeText(SurveyPage1p3.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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


    public void goToImpactWorkPage(){
        Intent impactWorkPage = new Intent(this, SurveyPage2.class);

        startActivity(impactWorkPage);

        Intent myIntent = new Intent(SurveyPage1p3.this, SurveyPage2.class);
        myIntent.putExtra("data1", currentQuestion);
        myIntent.putExtra("userInfo", userInfo);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage1p3.this.startActivity(myIntent);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

        Intent myIntent = new Intent(SurveyPage1p3.this, SurveyPage1p2.class);
        myIntent.putExtra("data1", currentQuestion);
        myIntent.putExtra("userInfo", userInfo);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage1p3.this.startActivity(myIntent);

    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String study = sharedPreferences.getString("impact_study",selectedStudy);
        String time = sharedPreferences.getString("impact_time", selectedTime);
        String poorStudy = sharedPreferences.getString("impact_poor_study", selectedPoorStudy);
        String disability = sharedPreferences.getString("impact_disability", selectedDisability);


        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {study, time, poorStudy, disability};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String selectedStudy,String selectedTime,String selectedPoorStudy,String selectedDisability) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential academic barriers have on your learning and grades last year?";
        // Add your question texts to the list here
        questionTexts.add("Online Courses?");
        questionTexts.add("Lack of proficiency using eClass, Bear Tracks, or required apps?");
        questionTexts.add("Lack of awareness of university policies and/or expectations");
        questionTexts.add("Challenges working in groups with classmates");
        String output = userInfo + "_output3.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability);
        PdfGenerator.generatePdf(SurveyPage1p3.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage1p3.this, Hint.class);
        startActivity(Hint);
    }
}