package com.example.rtw_app;

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

/**
 * SurveyPage1p3 represents the third part of the academic impact survey page in an Android application.
 * It gathers information related to the impact of various factors on learning and grades.
 */
public class SurveyPage1p3 extends AppCompatActivity {

    private int currentQuestion;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup2, disabilityRadioGroup;
    private int totalQuestions = 35; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private String userInfo;
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private SharedPreferences sharedPreferences;
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p3);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Set an onClick listener for using the hint button
        hint = findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Set an onClick listener for the "Next" button
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next question if not already answered
                if (currentQuestion < 3) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Get the selected answers from RadioGroups
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup2.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();

                // Check if any option is selected
                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 && selectedDisabilityId != -1) {
                    // Retrieve selected answers
                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();

                    // Store the answers and updated question index in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();
                    // Update the progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);

                    Toast.makeText(SurveyPage1p3.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactWorkPage();
                } else {
                    Toast.makeText(SurveyPage1p3.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClick listener for the "Back" button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous page (SurveyPage1p2)
                Intent impactAcademicPage2 = new Intent(SurveyPage1p3.this, SurveyPage1p2.class);
                impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(impactAcademicPage2);
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    /**
     * Sets the text color for all TextViews in the specified ViewGroup and its children.
     *
     * @param viewGroup The root ViewGroup.
     * @param color     The color to set for TextViews.
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
     * Navigates to the next page (SurveyPage2).
     */
    public void goToImpactWorkPage() {
        Intent myIntent = new Intent(SurveyPage1p3.this, SurveyPage2.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage1p3.this.startActivity(myIntent);
    }

    /**
     * Navigates to the previous page (SurveyPage1p2).
     */
    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);
    }

    /**
     * Updates the progress bar based on the current question and total questions.
     */
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    /**
     * Retrieves survey answers from SharedPreferences and returns them in a list.
     *
     * @param selectedStudy      Answer to the impact study question.
     * @param selectedTime       Answer to the impact time question.
     * @param selectedPoorStudy  Answer to the impact poor study question.
     * @param selectedDisability Answer to the impact disability question.
     * @return A list containing the survey answers.
     */
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String[]> answersList = new ArrayList<>();
        // Retrieve survey answers from SharedPreferences
        String study = sharedPreferences.getString("impact_study", selectedStudy);
        String time = sharedPreferences.getString("impact_time", selectedTime);
        String poorStudy = sharedPreferences.getString("impact_poor_study", selectedPoorStudy);
        String disability = sharedPreferences.getString("impact_disability", selectedDisability);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {study, time, poorStudy, disability};
        answersList.add(surveyAnswers);

        return answersList;
    }

    /**
     * Retrieves user information from SharedPreferences and returns it as an array.
     *
     * @return An array containing user information (name and CCID).
     */
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");
        return new String[]{name, ccid};
    }

    /**
     * Generates and saves a PDF based on the provided survey answers.
     *
     * @param selectedStudy      Answer to the impact study question.
     * @param selectedTime       Answer to the impact time question.
     * @param selectedPoorStudy  Answer to the impact poor study question.
     * @param selectedDisability Answer to the impact disability question.
     */
    private void generateAndSavePdf(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential academic barriers have on your learning and grades last year?";
        // Add your question texts to the list here
        questionTexts.add("Online Courses?");
        questionTexts.add("Lack of proficiency using eClass, Bear Tracks, or required apps?");
        questionTexts.add("Lack of awareness of university policies and/or expectations");
        questionTexts.add("Challenges working in groups with classmates");
        // Retrieve user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();
        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];
        String output = name + ccid + "_output3.pdf";

        // Generate PDF using PdfGenerator class
        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);
        PdfGenerator.generatePdf(SurveyPage1p3.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    /**
     * Opens a hint activity to remind students about the purpose of the survey.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage1p3.this, Hint.class);
        startActivity(Hint);
    }
}
