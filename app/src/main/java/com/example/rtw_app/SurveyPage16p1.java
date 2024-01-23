package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage16p1 extends AppCompatActivity {

    // Button for providing hints
    private Button hint;

    // SharedPreferences for storing/retrieving data
    private SharedPreferences sharedPreferences;

    // Current question number and total questions
    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";

    // Progress bar and text to display progress
    private ProgressBar progressBar;
    private TextView progressText;

    // RadioGroups for each set of questions
    private RadioGroup colorRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page16p1);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize hint button and set click listener
        hint = findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Initialize RadioGroup instances
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        // Initialize "Next" button and set click listener
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the current question if not at the last question
                if (currentQuestion < 26) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }
                // Update SharedPreferences with current question
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                // Call the generateAndSavePdf method
                updateProgress();
                generateAndSavePdf();
                // Call the goTo method to navigate to the next page
                goTo();
            }
        });

        // Initialize "Back" button and set click listener
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the goBack method to navigate back to the previous page
                goBack();
            }
        });
    }

    // Method to generate and save PDF based on user responses
    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Study Habits";

        // Add your question texts to the list here
        questionTexts.add("I study at a regularly scheduled time each day");
        questionTexts.add("I have a good understanding of what is required to be successful in each course");
        questionTexts.add("I know how to break down large assignments to make them more manageable");
        questionTexts.add("Starting an assignment is easy for me");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements (not being used in this activity)
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Generate output file name
        String output = name + ccid + "_output26.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage16p1.this, output,
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

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                getSelectedRadioButtonText(colorRadioGroup),
                getSelectedRadioButtonText(timeRadioGroup),
                getSelectedRadioButtonText(poorStudyRadioGroup),
                getSelectedRadioButtonText(disabilityRadioGroup)
        };
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to get the text of the selected RadioButton in a RadioGroup
    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    // Method to navigate to the next page
    public void goTo() {
        Intent SurveyPage16p2 = new Intent(this, SurveyPage16p2.class);
        SurveyPage16p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage16p2);
    }

    // Method to navigate back to the previous page
    public void goBack() {
        Intent SurveyPage15p2 = new Intent(this, SurveyPage15p2.class);
        SurveyPage15p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage15p2);
    }

    // Method to open the hint page
    private void openHint() {
        Intent Hint = new Intent(SurveyPage16p1.this, Hint.class);
        startActivity(Hint);
    }

    // Method to update the progress bar and text
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
