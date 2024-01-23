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
 * SurveyPage1p2 represents the second part of the academic impact survey page in an Android application.
 * It gathers information related to additional academic barriers.
 */
public class SurveyPage1p2 extends AppCompatActivity {

    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup2, disabilityRadioGroup;
    private int currentQuestion;
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private SharedPreferences sharedPreferences;
    private int totalQuestions = 35; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p2);

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
        // Update the information on the progress bar
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
            public void onClick(View view) {
                // Move to the next question if not already answered
                if (currentQuestion < 2) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Get the selected answers from RadioGroups
                int selectedStudyId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup2.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();

                // Check if any option is selected
                if (selectedStudyId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 && selectedDisabilityId != -1) {
                    // Retrieve selected answers
                    String selectedStudy = ((RadioButton) findViewById(selectedStudyId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();

                    // Store the increased current Question
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update the progress bar
                    updateProgress();

                    // Generate and save PDF after submitting survey
                    generateAndSavePdf(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);

                    // Rest of your code
                } else {
                    // Show a message or handle the case where no option is selected
                    Toast.makeText(SurveyPage1p2.this, "Please select answers for all questions.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClick listener for the "Back" button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous page (SurveyPage1p1)
                Intent impactAcademicPage3 = new Intent(SurveyPage1p2.this, SurveyPage1p1.class);
                impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(impactAcademicPage3);
            }
        });

        // Set text color for all TextViews
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
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }

    /**
     * Navigates to the next page (SurveyPage1p3).
     */
    public void goToImpactAcademicPage3() {
        Intent impactAcademicPage3 = new Intent(this, SurveyPage1p3.class);
        impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage3);
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
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
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
     * @return An array containing user information.
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
        questionTexts.add("Inadequate Reading/Writing Skills?");
        questionTexts.add("Inadequate Math Skills?");
        questionTexts.add("Easily Distracted?");
        questionTexts.add("Unhappy with instructor?");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output2.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);
        PdfGenerator.generatePdf(SurveyPage1p2.this, output, surveyAnswers, questionTexts, mainQuestion);
        goToImpactAcademicPage3();
    }

    /**
     * Navigates to the previous page (SurveyPage1p1).
     */
    public void goBack() {
        Intent impactAcademicPage3 = new Intent(this, SurveyPage1p1.class);
        impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage3);
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
     * Opens a hint activity to remind students about the purpose of the survey.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage1p2.this, Hint.class);
        startActivity(Hint);
    }
}
