package com.example.rtw_app;

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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * SurveyPage5 represents the fifth survey page in an Android application.
 * It gathers information related to the impact of financial barriers on education participation.
 */
public class SurveyPage5 extends AppCompatActivity {
    private String userInfo;
    private SharedPreferences sharedPreferences;

    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions

    private static final String KEY_CURRENT_QUESTION = "current_question";

    private ProgressBar progressBar;
    private TextView progressText;

    private Button hint;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page5);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        hint = findViewById(R.id.hint);

        // Set an onClick listener for the hint button
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

                if (currentQuestion < 8) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
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
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);

                    Toast.makeText(SurveyPage5.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToNextPage();
                } else {
                    Toast.makeText(SurveyPage5.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClick listener for the "Back" button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous page (SurveyPage4)
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
     * Handles the "Next" button click event.
     */
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

    /**
     * Retrieves the text of the selected RadioButton in the given RadioGroup.
     *
     * @param radioGroup The RadioGroup.
     * @return The text of the selected RadioButton.
     */
    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    /**
     * Retrieves survey answers from SharedPreferences.
     *
     * @param selectedStudy      The selected answer for the financial impact on study.
     * @param selectedTime       The selected answer for the financial impact on time.
     * @param selectedPoorStudy  The selected answer for the financial impact on poor study.
     * @param selectedDisability The selected answer for the financial impact on disability.
     * @return A list containing survey answers.
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
     * Retrieves user information from SharedPreferences.
     *
     * @return An array containing user information (name, ccid).
     */
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * Generates and saves a PDF based on survey responses.
     *
     * @param selectedStudy      The selected answer for the financial impact on study.
     * @param selectedTime       The selected answer for the financial impact on time.
     * @param selectedPoorStudy  The selected answer for the financial impact on poor study.
     * @param selectedDisability The selected answer for the financial impact on disability.
     */
    private void generateAndSavePdf(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Please indicate how much of an impact each of these potential financial barriers\n" +
                "had on your ability to participate in your education?";
        // Add question texts to the list here
        questionTexts.add("Worried about money and\n" +
                "ability to pay for basic needs");
        questionTexts.add("Inadequate financial\n" +
                "aid/scholarship");
        questionTexts.add("Limited financial management\n" +
                "skills");
        questionTexts.add("Unable to purchase textbooks,\n" +
                "laptop, or other necessary\n" +
                "learning tools");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output8.pdf";

        // Generate PDF using PdfGenerator class
        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability);
        PdfGenerator.generatePdf(SurveyPage5.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    /**
     * Navigates to the next survey page (SurveyPage6p1).
     */
    private void goToNextPage() {
        Intent intent = new Intent(this, SurveyPage6p1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Navigates to the previous survey page (SurveyPage4).
     */
    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, SurveyPage4.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);
    }

    /**
     * Opens a hint activity to remind students about the purpose of the survey.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage5.this, Hint.class);
        startActivity(Hint);
    }

    /**
     * Updates the progress bar based on the current question and total questions.
     */
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
