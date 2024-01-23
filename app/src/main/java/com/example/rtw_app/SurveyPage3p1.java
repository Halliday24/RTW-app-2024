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
 * SurveyPage3p1 represents the first part of the third survey page in an Android application.
 * It gathers information related to major-related barriers impacting education.
 */
public class SurveyPage3p1 extends AppCompatActivity {

    private int currentQuestion;
    private int totalQuestions = 35; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private String userInfo;
    private SharedPreferences sharedPreferences;
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page3p1);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();
        hint = findViewById(R.id.hint);

        // Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Initialize RadioGroups
        final RadioGroup studyRadioGroup = findViewById(R.id.studyRadioGroup);
        final RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        final RadioGroup poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);

        // Set an onClick listener for the "Next" button
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the next question if not already answered
                if (currentQuestion < 5) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Get the selected answers from RadioGroups
                int selectedHoursId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedLateId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedUnemployedId = poorStudyRadioGroup2.getCheckedRadioButtonId();

                // Check if any option is selected
                if (selectedHoursId != -1 && selectedLateId != -1 && selectedUnemployedId != -1) {
                    // Retrieve selected answers
                    String selectedHours = ((RadioButton) findViewById(selectedHoursId)).getText().toString();
                    String selectedLate = ((RadioButton) findViewById(selectedLateId)).getText().toString();
                    String selectedUnemployed = ((RadioButton) findViewById(selectedUnemployedId)).getText().toString();
                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Work Too many hours", selectedHours);
                    editor.putString("Work late hours", selectedLate);
                    editor.putString("Unemployed", selectedUnemployed);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update the progress bar
                    updateProgress();
                    generateAndSavePdf(selectedHours, selectedLate, selectedUnemployed);

                    // Display a success message
                    Toast.makeText(SurveyPage3p1.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Go to the next page (SurveyPage3p2)
                    goToSurveyPage3p2();

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage3p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClick listener for the "Back" button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous page (SurveyPage2)
                goBack();
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
     * Retrieves survey answers from SharedPreferences and returns them in a list.
     *
     * @param selectedHours      Answer to the work hours question.
     * @param selectedLate       Answer to the late hours question.
     * @param selectedUnemployed Answer to the unemployment question.
     * @return A list containing the survey answers.
     */
    private List<String[]> getSurveyAnswers(String selectedHours, String selectedLate, String selectedUnemployed) {
        List<String[]> answersList = new ArrayList<>();
        // Retrieve survey answers from SharedPreferences
        String hours = sharedPreferences.getString("Work Too many hours", selectedHours);
        String late = sharedPreferences.getString("Work late hours", selectedLate);
        String unemployed = sharedPreferences.getString("Unemployed", selectedUnemployed);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {hours, late, unemployed};
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
     * @param selectedHours      Answer to the work hours question.
     * @param selectedLate       Answer to the late hours question.
     * @param selectedUnemployed Answer to the unemployment question.
     */
    private void generateAndSavePdf(String selectedHours, String selectedLate, String selectedUnemployed) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How did each of these potential major-related barriers impact your education?";
        // Add your question texts to the list here
        questionTexts.add("I was not admitted to my first choice program");
        questionTexts.add("Unclear education or career goals");
        questionTexts.add("Pressure to choose a path that was not a good fit for me");

        // Retrieve user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();
        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];
        String output = name + ccid + "_output5.pdf";

        // Generate PDF using PdfGenerator class
        List<String[]> surveyAnswers = getSurveyAnswers(selectedHours, selectedLate, selectedUnemployed);
        PdfGenerator.generatePdf(SurveyPage3p1.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    /**
     * Navigates to the next survey page (SurveyPage3p2).
     */
    public void goToSurveyPage3p2() {
        Intent SurveyPage3p2 = new Intent(this, SurveyPage3p2.class);
        SurveyPage3p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage3p2.putExtra("userInfo", userInfo);
        startActivity(SurveyPage3p2);
    }

    /**
     * Navigates to the previous survey page (SurveyPage2).
     */
    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, SurveyPage2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        impactAcademicPage2.putExtra("userInfo", userInfo);
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
     * Opens a hint activity to remind students about the purpose of the survey.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage3p1.this, Hint.class);
        startActivity(Hint);
    }
}
