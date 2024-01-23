package com.example.rtw_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * SurveyPage6p1 represents the first part of the sixth survey page in an Android application.
 * It gathers information related to the impact of social barriers on the user's experience.
 */
public class SurveyPage6p1 extends AppCompatActivity {

    // SharedPreferences to store and retrieve data
    private SharedPreferences sharedPreferences;

    // Current question number and total number of questions
    private int currentQuestion;
    private final int totalQuestions = 35; // Set the total number of questions

    private static final String KEY_CURRENT_QUESTION = "current_question";

    // UI elements
    private ProgressBar progressBar;
    private TextView progressText;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup, preparationRadioGroup;
    private Button hint;

    // User information
    private String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page6p1);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);

        // Retrieve the current question number from SharedPreferences
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize your RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        preparationRadioGroup = findViewById(R.id.preparationRadioGroup);

        // Hint button
        hint = findViewById(R.id.hint);

        // Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Next button
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the current question is within the valid range
                if (currentQuestion < 9) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Get selected RadioButton IDs from each RadioGroup
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
                int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();

                // Check if all questions are answered
                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1 && selectedPreparationId != -1) {

                    // Get the text of the selected RadioButton for each question
                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();
                    String selectedPreparation = ((RadioButton) findViewById(selectedPreparationId)).getText().toString();

                    // Update SharedPreferences with selected answers
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putString("impact_color5", selectedPreparation);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability, selectedPreparation);

                    // Display success message
                    Toast.makeText(SurveyPage6p1.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Navigate to the next page
                    goToNextPage();
                } else {
                    // Display a message to answer all questions
                    Toast.makeText(SurveyPage6p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Back button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    // Save the state of RadioButton selections
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Get selected RadioButton IDs from each RadioGroup
        int selectedStudyId = studyRadioGroup.getCheckedRadioButtonId();
        int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
        int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
        int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
        int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();

        // Save the IDs to the bundle
        outState.putInt("selectedStudyId", selectedStudyId);
        outState.putInt("selectedTimeId", selectedTimeId);
        outState.putInt("selectedPoorStudyId", selectedPoorStudyId);
        outState.putInt("selectedDisabilityId", selectedDisabilityId);
        outState.putInt("selectedPreparationId", selectedPreparationId);
    }

    // Restore the state of RadioButton selections
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Retrieve the selected RadioButton IDs from the bundle
        int selectedStudyId = savedInstanceState.getInt("selectedStudyId");
        int selectedTimeId = savedInstanceState.getInt("selectedTimeId");
        int selectedPoorStudyId = savedInstanceState.getInt("selectedPoorStudyId");
        int selectedDisabilityId = savedInstanceState.getInt("selectedDisabilityId");
        int selectedPreparationId = savedInstanceState.getInt("selectedPreparationId");

        // Check the corresponding RadioButtons based on the saved IDs
        studyRadioGroup.check(selectedStudyId);
        timeRadioGroup.check(selectedTimeId);
        poorStudyRadioGroup.check(selectedPoorStudyId);
        disabilityRadioGroup.check(selectedDisabilityId);
        preparationRadioGroup.check(selectedPreparationId);
    }

    // Set text color for all TextViews in the layout
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

    // Navigate to the next page
    public void goToNextPage() {
        Intent nextPage = new Intent(this, SurveyPage6p2.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);
    }

    // Navigate to the previous page
    public void goBack() {
        Intent myIntent = new Intent(SurveyPage6p1.this, SurveyPage5.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage6p1.this.startActivity(myIntent);
    }

    // Retrieve survey answers from SharedPreferences and return them as a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability, String selectedPreparation) {
        List<String[]> answersList = new ArrayList<>();
        // Retrieve survey answers from SharedPreferences
        String study = sharedPreferences.getString("impact_study", selectedStudy);
        String time = sharedPreferences.getString("impact_time", selectedTime);
        String poorStudy = sharedPreferences.getString("impact_poor_study", selectedPoorStudy);
        String disability = sharedPreferences.getString("impact_disability", selectedDisability);
        String preparation = sharedPreferences.getString("impact_color5", selectedPreparation);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {study, time, poorStudy, disability, preparation};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Generate and save PDF based on the survey answers
    private void generateAndSavePdf(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability, String selectedPreparation) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential social barriers have on your\n" +
                "experience last year?";
        // Add question texts to the list here
        questionTexts.add("New independent status/living\n" +
                "away from home for the first\n" +
                "time");
        questionTexts.add("Roommate problems");
        questionTexts.add("Relationship worries");
        questionTexts.add("Loneliness");
        questionTexts.add("Socially uncomfortable/shy");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Define the output filename
        String output = name + ccid + "_output9.pdf";

        // Generate PDF using PdfGenerator class
        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy, selectedTime, selectedPoorStudy, selectedDisability, selectedPreparation);
        PdfGenerator.generatePdf(SurveyPage6p1.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    // Retrieve user information from SharedPreferences
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    // Update the progress bar based on the current question
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    // Open the hint activity
    private void openHint() {
        Intent Hint = new Intent(SurveyPage6p1.this, Hint.class);
        startActivity(Hint);
    }
}
