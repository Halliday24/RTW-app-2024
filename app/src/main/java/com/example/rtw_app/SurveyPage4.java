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
 * SurveyPage4 represents the fourth survey page in an Android application.
 * It gathers information related to the impact of free-time barriers on education participation.
 */
public class SurveyPage4 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private int totalQuestions = 35; // Set the total number of questions
    private int currentQuestion;
    private RadioGroup option1RadioGroup;
    private RadioGroup option2RadioGroup;
    private RadioGroup option3RadioGroup2;
    private RadioGroup option4RadioGroup;
    private ProgressBar progressBar;
    private TextView progressText;

    private String userInfo;
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page4);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize RadioGroups and set question texts
        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        TextView textView = (TextView) findViewById(R.id.surveyPage4_Question);
        textView.setText("How much of an impact did each of these potential free-time barriers have on your ability to participate in your education:");

        // Option 1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage4_Option1);
        textView1.setText("Social Media (Facebook, Instagram, YouTube, Snapchat, TikTok, Reddit, etc)");

        // Option 2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage4_Option2);
        textview2.setText("Too much screen time (video games, streaming, internet, etc)");

        // Option 3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage4_Option3);
        textview3.setText("A very active social life");

        // Option 4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage4_Option4);
        textview4.setText("Overextended in my extracurricular activities");

        // Initialize RadioGroups
        option1RadioGroup = findViewById(R.id.option1_answers);
        option2RadioGroup = findViewById(R.id.option2_answers);
        option3RadioGroup2 = findViewById(R.id.option3_answers);
        option4RadioGroup = findViewById(R.id.option4_answers);

        hint = findViewById(R.id.hint);

        // Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);

        // Set an onClick listener for the "Next" button
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next question if not already answered
                if (currentQuestion < 7) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Get the selected answers from RadioGroups
                int option1Id = option1RadioGroup.getCheckedRadioButtonId();
                int option2Id = option2RadioGroup.getCheckedRadioButtonId();
                int option3Id = option3RadioGroup2.getCheckedRadioButtonId();
                int option4Id = option4RadioGroup.getCheckedRadioButtonId();

                // Check if any option is selected
                if (option1Id != -1 && option2Id != -1 && option3Id != -1 && option4Id != -1) {
                    // Retrieve selected answers
                    String selectedOption1 = ((RadioButton) findViewById(option1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(option2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(option3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(option4Id)).getText().toString();

                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("option1", selectedOption1);
                    editor.putString("option2", selectedOption2);
                    editor.putString("option3", selectedOption3);
                    editor.putString("option4", selectedOption4);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update the progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3, selectedOption4);

                    // Display a success message
                    Toast.makeText(SurveyPage4.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Go to the next survey page (SurveyPage5)
                    goToImpactFinancial();
                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage4.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set an onClick listener for the "Back" button
        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous page (SurveyPage3p2)
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
     * Retrieves survey answers from SharedPreferences.
     *
     * @param selectedOption1 The selected answer for Option 1.
     * @param selectedOption2 The selected answer for Option 2.
     * @param selectedOption3 The selected answer for Option 3.
     * @param selectedOption4 The selected answer for Option 4.
     * @return A list containing survey answers.
     */
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2, String selectedOption3, String selectedOption4) {
        List<String[]> answersList = new ArrayList<>();
        // Retrieve survey answers from SharedPreferences
        String option1 = sharedPreferences.getString("option1", selectedOption1);
        String option2 = sharedPreferences.getString("option2", selectedOption2);
        String option3 = sharedPreferences.getString("option3", selectedOption3);
        String option4 = sharedPreferences.getString("option4", selectedOption4);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {option1, option2, option3, option4};
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
     * @param option1 The selected answer for Option 1.
     * @param option2 The selected answer for Option 2.
     * @param option3 The selected answer for Option 3.
     * @param option4 The selected answer for Option 4.
     */
    private void generateAndSavePdf(String option1, String option2, String option3, String option4) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential free-time barriers have on your ability to participate in your education?";
        // Add question texts to the list
        questionTexts.add("Social Media (Facebook, Instagram, YouTube, Snapchat, TikTok, Reddit, etc)?");
        questionTexts.add("Too much screen time (video games, streaming, internet, etc)");
        questionTexts.add("A very active social life");
        questionTexts.add("Overextended in my extracurricular activities");

        // Retrieve user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();
        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];
        String output = name + ccid + "_output7.pdf";

        // Generate PDF using PdfGenerator class
        List<String[]> surveyAnswers = getSurveyAnswers(option1, option2, option3, option4);
        PdfGenerator.generatePdf(SurveyPage4.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    /**
     * Navigates to the next survey page (SurveyPage5).
     */
    public void goToImpactFinancial() {
        Intent SurveyPage5 = new Intent(this, SurveyPage5.class);
        SurveyPage5.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage5);
    }

    /**
     * Navigates to the previous survey page (SurveyPage3p2).
     */
    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, SurveyPage3p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);
    }

    /**
     * Opens a hint activity to remind students about the purpose of the survey.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage4.this, Hint.class);
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
