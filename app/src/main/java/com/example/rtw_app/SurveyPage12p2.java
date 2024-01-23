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

public class SurveyPage12p2 extends AppCompatActivity {
    // RadioGroups for each option
    private RadioGroup option1Group, option2Group, option3Group, option4Group, option5Group;

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

    // Button for providing hints
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page12p2);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize RadioGroups for each option
        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        option3Group = findViewById(R.id.option3_answers);
        option4Group = findViewById(R.id.option4_answers);
        option5Group = findViewById(R.id.option5_answers);

        // Set the question text for the current page
        TextView textView = findViewById(R.id.surveyPage12_Question);
        textView.setText("Self-Efficacy and Growth Mindset");

        // Set option texts for radio buttons
        setOptionTexts();

        // Initialize hint button
        hint = findViewById(R.id.hint);
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
                if (currentQuestion < 19) {
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
                int selectedOption3Id = option3Group.getCheckedRadioButtonId();
                int selectedOption4Id = option4Group.getCheckedRadioButtonId();
                int selectedOption5Id = option5Group.getCheckedRadioButtonId();

                // Check if all questions are answered
                if (selectedOption1Id != -1 && selectedOption2Id != -1 && selectedOption3Id != -1
                        && selectedOption4Id != -1 && selectedOption5Id != -1) {
                    // Get selected options
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(selectedOption3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(selectedOption4Id)).getText().toString();
                    String selectedOption5 = ((RadioButton) findViewById(selectedOption5Id)).getText().toString();

                    // Update progress and generate/save PDF
                    updateProgress();
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5);

                    // Move to the next page
                    goToEducationAndGoals();
                } else {
                    // Show a toast if questions are not answered
                    Toast.makeText(SurveyPage12p2.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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
        TextView textView1 = findViewById(R.id.self_efficacy2_Option1);
        textView1.setText("I don’t believe in limitations and I can create opportunities for myself");

        TextView textview2 = findViewById(R.id.self_efficacy2_Option2);
        textview2.setText("I love trying new things and taking on new challenges");

        TextView textview3 = findViewById(R.id.self_efficacy2_Option3);
        textview3.setText("Failure doesn’t define me and I can learn from my mistakes");

        TextView textview4 = findViewById(R.id.self_efficacy2_Option4);
        textview4.setText("I avoid negative self-talk and speak constructively about myself and others");

        TextView textview5 = findViewById(R.id.self_efficacy2_Option5);
        textview5.setText("Challenges are opportunities for growth");
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

    private void generateAndSavePdf(String selectedOption1, String selectedOption2,
                                    String selectedOption3, String selectedOption4,
                                    String selectedOption5) {
        // List to store question texts
        List<String> questionTexts = new ArrayList<>();

        // Main question for the page
        String mainQuestion = "Self-Efficacy and Growth Mindset (Part 2)";

        // Add question texts to the list
        questionTexts.add("I don’t believe in limitations and I can create opportunities for myself");
        questionTexts.add("I love trying new things and taking on new challenges");
        questionTexts.add("Failure doesn’t define me and I can learn from my mistakes");
        questionTexts.add("I avoid negative self-talk and speak constructively about myself and others");
        questionTexts.add("Challenges are opportunities for growth");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Output PDF filename
        String output = name + ccid + "_output19.pdf";

        // Generate PDF using the PdfGenerator class
        PdfGenerator.generatePdf(SurveyPage12p2.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5),
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

    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2,
                                            String selectedOption3, String selectedOption4,
                                            String selectedOption5) {
        // Create a list to store survey answers
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5};
        answersList.add(surveyAnswers);

        return answersList;
    }

    private void goToEducationAndGoals() {
        // Move to the next activity
        Intent EducationAndGoals = new Intent(this, SurveyPage13p1.class);
        EducationAndGoals.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(EducationAndGoals);
    }

    private void goBack() {
        // Move to the previous activity
        Intent SelfEfficacy = new Intent(this, SurveyPage12p1.class);
        SelfEfficacy.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SelfEfficacy);
    }

    // Method to provide a hint to the user
    private void openHint() {
        // Start Hint activity
        Intent Hint = new Intent(SurveyPage12p2.this, Hint.class);
        startActivity(Hint);
    }

    // Method to update progress bar and text
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
