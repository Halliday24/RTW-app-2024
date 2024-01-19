package com.example.rtw_app;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class SurveyPage1p2 extends AppCompatActivity {

    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup2, disabilityRadioGroup;

    private int currentQuestion;

    private static final String KEY_CURRENT_QUESTION = "current_question";

    private SharedPreferences sharedPreferences;
    private int totalQuestions = 5; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p2);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);


        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,1);

        updateProgress();

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
            public void onClick(View view) {
                //currentQuestion++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, 1);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                goToImpactAcademicPage3();
            }
        });

        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentQuestion--;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, 1);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                goBack();
            }
        });

        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
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

    public void goToImpactAcademicPage3() {
        Intent impactAcademicPage3 = new Intent(this, SurveyPage1p3.class);
        impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage3);


    }
    // Method to get survey answers in a list
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
        questionTexts.add("Inadequate Reading/Writing Skills?");
        questionTexts.add("Inadequate Math Skills?");
        questionTexts.add("Easily Distracted?");
        questionTexts.add("Unhappy with instructor?");
        String userInfo = "";
        String output = userInfo + "_output1.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability);
        PdfGenerator.generatePdf(SurveyPage1p2.this, "survey_output2.pdf", surveyAnswers, questionTexts, mainQuestion);
    }
    public void goBack() {
        Intent impactAcademicPage3 = new Intent(this, SurveyPage1p1.class);
        impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage3);



    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage1p2.this, Hint.class);
        startActivity(Hint);
    }
}
