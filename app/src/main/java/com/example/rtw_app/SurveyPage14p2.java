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
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage14p2 extends AppCompatActivity {

    // SharedPreferences for storing/retrieving data
    private SharedPreferences sharedPreferences;

    // Current question number and total questions
    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";

    // Progress bar and text to display progress
    private ProgressBar progressBar;
    private TextView progressText;

    // CheckBoxes for each option
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;

    // User information string
    private String userInfo;

    // Button for providing hints
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page14p2);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize CheckBoxes for each option
        checkBox11 = findViewById(R.id.checkBox11);
        checkBox12 = findViewById(R.id.checkBox12);
        checkBox13 = findViewById(R.id.checkBox13);
        checkBox14 = findViewById(R.id.checkBox14);
        checkBox15 = findViewById(R.id.checkBox15);
        checkBox16 = findViewById(R.id.checkBox16);
        checkBox17 = findViewById(R.id.checkBox17);

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
                // Call the generateAndSavePdf method
                if (currentQuestion < 23) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();

                // Update progress, generate and save PDF, and move to the next page
                updateProgress();
                generateAndSavePdf();
                goToNextPage();
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

    // Method to generate and save PDF based on user responses
    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "What time Management tools have worked for you in the past?";

        // Add your question texts to the list here
        questionTexts.add("Planner/Agenda");
        questionTexts.add("Whiteboard");
        questionTexts.add("Phone Alert");
        questionTexts.add("eClass");
        questionTexts.add("Calendar");
        questionTexts.add("None");
        questionTexts.add("Other App");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Generate output file name
        String output = name + ccid + "_output23.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage14p2.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);
    }

    // Method to retrieve user information from SharedPreferences
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    // Method to set text color for all TextViews in a ViewGroup
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

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                String.valueOf(checkBox11.isChecked()),
                String.valueOf(checkBox12.isChecked()),
                String.valueOf(checkBox13.isChecked()),
                String.valueOf(checkBox14.isChecked()),
                String.valueOf(checkBox15.isChecked()),
                String.valueOf(checkBox16.isChecked()),
                String.valueOf(checkBox17.isChecked())
        };
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to navigate to the next page
    public void goToNextPage() {
        Intent nextPage = new Intent(this, SurveyPage15p1.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);
    }

    // Method to navigate back to the previous page
    public void goBack() {
        Intent Education_And_Goals2 = new Intent(this, SurveyPage14p1.class);
        Education_And_Goals2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Education_And_Goals2);
    }

    // Method to open the hint page
    private void openHint() {
        Intent Hint = new Intent(SurveyPage14p2.this, Hint.class);
        startActivity(Hint);
    }

    // Method to update the progress bar and text
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
