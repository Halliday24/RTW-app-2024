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

public class SurveyPage13p2 extends AppCompatActivity {

    // Button for providing hints
    private Button hint;

    // RadioGroups for each option
    private RadioGroup option1Group, option2Group;

    // User information string
    private String userInfo;

    // SharedPreferences for storing/retrieving data
    private SharedPreferences sharedPreferences;

    // Current question number and total questions
    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";

    // Progress bar and text to display progress
    private ProgressBar progressBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page13p2);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize RadioGroups for each option
        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);

        // Set the question text for the current page
        TextView textView = findViewById(R.id.surveyPage13p2_Question);
        textView.setText("Education and Goals");

        // Set option texts for radio buttons
        setOptionTexts();

        // Initialize hint button
        hint = findViewById(R.id.hint);

        // Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Initialize "Next" button and set click listener
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment current question, but not beyond the total number of questions
                if (currentQuestion < 21) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }

                // Save current question to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();

                // Retrieve values from radio buttons
                int selectedOption1Id = option1Group.getCheckedRadioButtonId();
                int selectedOption2Id = option2Group.getCheckedRadioButtonId();

                // Check if all questions are answered
                if (selectedOption1Id != -1 && selectedOption2Id != -1) {
                    // Get selected options
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();

                    // Update progress and generate/save PDF
                    updateProgress();
                    generateAndSavePdf(selectedOption1, selectedOption2);

                    // Move to the next page
                    goToNextPage();
                } else {
                    // Show a toast if questions are not answered
                    Toast.makeText(SurveyPage13p2.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize "Back" button and set click listener
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

    private void setOptionTexts() {
        // Set option texts for radio buttons
        TextView textView1 = findViewById(R.id.Education_And_Goals2_Option1);
        textView1.setText("I know my strengths and how they will help me be successful");

        TextView textview2 = findViewById(R.id.Education_And_Goals2_Option2);
        textview2.setText("I have a faculty or staff mentor");
    }

    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        // Recursively set text color for all TextViews in the layout
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

    private void generateAndSavePdf(String selectedOption1, String selectedOption2) {
        // List to store question texts
        List<String> questionTexts = new ArrayList<>();

        // Main question for the page
        String mainQuestion = "Education and Goals (Part 2)";

        // Add question texts to the list
        questionTexts.add("I know my strengths and how they will help me be successful");
        questionTexts.add("I have a faculty or staff mentor");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access individual elements from user information
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Output PDF filename
        String output = name + ccid + "_output21.pdf";

        // Generate PDF using the PdfGenerator class
        PdfGenerator.generatePdf(SurveyPage13p2.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2),
                questionTexts, mainQuestion);
    }

    private String[] getUserInfoFromSharedPreferences() {
        // Retrieve user information from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2) {
        // List to store survey answers
        List<String[]> answersList = new ArrayList<>();

        // Array with the survey answers
        String[] surveyAnswers = {selectedOption1, selectedOption2};

        // Add array to the list
        answersList.add(surveyAnswers);

        return answersList;
    }

    private void goToNextPage() {
        // Move to the next activity
        Intent nextPage = new Intent(this, SurveyPage14p1.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);
    }

    private void goBack() {
        // Move to the previous activity
        Intent EducationAndGoals = new Intent(this, SurveyPage13p1.class);
        EducationAndGoals.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(EducationAndGoals);
    }

    // Method to provide a hint to students
    private void openHint() {
        Intent Hint = new Intent(SurveyPage13p2.this, Hint.class);
        startActivity(Hint);
    }

    // Method to update the progress bar and text
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
