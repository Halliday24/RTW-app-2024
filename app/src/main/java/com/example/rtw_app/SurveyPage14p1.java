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

public class SurveyPage14p1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_survey_page14p1);

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
        TextView textView = findViewById(R.id.surveyPage14p1_Question);
        textView.setText("Time Management");

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
                if (currentQuestion < 22) {
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
                if (selectedOption1Id != -1 && selectedOption2Id != -1 &&
                        selectedOption3Id != -1 && selectedOption4Id != -1 && selectedOption5Id != -1) {

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
                    goToNextPage();
                } else {
                    // Show a toast if questions are not answered
                    Toast.makeText(SurveyPage14p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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
        TextView textView1 = findViewById(R.id.TimeManagement_Option1);
        textView1.setText("I make plans each week about how I will spend my time");

        TextView textview2 = findViewById(R.id.TimeManagement_Option2);
        textview2.setText("I follow through with the plans I make around how I spend my time");

        TextView textview3 = findViewById(R.id.TimeManagement_Option3);
        textview3.setText("I complete major assignments and hand them in on time");

        TextView textview4 = findViewById(R.id.TimeManagement_Option4);
        textview4.setText("I study at least 2 hours for every hour I spend in class");

        TextView textview5 = findViewById(R.id.TimeManagement_Option5);
        textview5.setText("I can estimate how much time a task is going to take");
    }

    private void generateAndSavePdf(String selectedOption1, String selectedOption2,
                                    String selectedOption3, String selectedOption4, String selectedOption5) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Time Management";

        // Add your question texts to the list here
        questionTexts.add("I make plans each week about how I will spend my time");
        questionTexts.add("I follow through with the plans I make around how I spend my time");
        questionTexts.add("I complete major assignments and hand them in on time");
        questionTexts.add("I study at least 2 hours for every hour I spend in class");
        questionTexts.add("I can estimate how much time a task is going to take");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        // Generate output file name
        String output = name + ccid + "_output22.pdf";

        // Generate and save PDF
        PdfGenerator.generatePdf(SurveyPage14p1.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5),
                questionTexts, mainQuestion);
    }

    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2,
                                            String selectedOption3, String selectedOption4, String selectedOption5) {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5};
        answersList.add(surveyAnswers);

        return answersList;
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

    // Method to navigate to the next page
    public void goToNextPage() {
        Intent nextPage = new Intent(this, SurveyPage14p2.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);
    }

    // Method to navigate back to the previous page
    public void goBack() {
        Intent Education_And_Goals2 = new Intent(this, SurveyPage13p2.class);
        Education_And_Goals2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Education_And_Goals2);
    }

    // Method to open the hint page
    private void openHint() {
        Intent Hint = new Intent(SurveyPage14p1.this, Hint.class);
        startActivity(Hint);
    }

    // Method to update the progress bar and text
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
