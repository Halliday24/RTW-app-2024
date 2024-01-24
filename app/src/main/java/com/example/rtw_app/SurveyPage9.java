package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class SurveyPage9 extends AppCompatActivity {
//XML Should have Values and Goals on top but is fine regardless

    // SharedPreferences to store and retrieve data
    private SharedPreferences sharedPreferences;

    // Current question number
    private int currentQuestion;

    //total number of questions
    private int totalQuestions = 35; // Set the total number of questions

    //String holder for the current questions integer in Shared Preferences
    private static final String KEY_CURRENT_QUESTION = "current_question";

    // Variable for the progressbar Widget in the xml
    private ProgressBar progressBar;
    //Variable for the progressText Widget in the xml
    private TextView progressText;

    //Variable for the hint button
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page9);

        //Get the progressbar and progress text.
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        //get the current number from the sharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        //update the progress bar
        updateProgress();

        // Initialize your RadioGroup instances
        final RadioGroup purposeRadioGroup = findViewById(R.id.purposeRadioGroup);
        final RadioGroup confidenceRadioGroup = findViewById(R.id.confidenceRadioGroup);
        final RadioGroup goalsRadioGroup = findViewById(R.id.goalsRadioGroup);
        final RadioGroup valuesRadioGroup = findViewById(R.id.valuesRadioGroup);

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            /**
             * This method displays the hint popup when the hint button is pressed.
             * @param view
             */
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method increments the current question integer if this page hasn't been visited
             * before, checks if all the options are answered.
             * If they are then it saves the user's answers to the shared preferences and moves to the next page.
             * @param view
             */
            @Override
            public void onClick(View view) {
                if(currentQuestion<15){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }

                // Get selected RadioButton IDs from each RadioGroup
                int selectedPurposeId = purposeRadioGroup.getCheckedRadioButtonId();
                int selectedConfidenceId = confidenceRadioGroup.getCheckedRadioButtonId();
                int selectedGoalsId = goalsRadioGroup.getCheckedRadioButtonId();
                int selectedValuesId = valuesRadioGroup.getCheckedRadioButtonId();
                //log to assist debugging
                Log.d("Debug", "selectedStudy: " + selectedPurposeId);
                Log.d("Debug", "selectedTime: " + selectedConfidenceId);
                Log.d("Debug", "selectedPoorStudy: " + selectedGoalsId);
                Log.d("Debug", "selectedDisability: " + selectedValuesId);

                // Check if all the questions are answered
                if (selectedPurposeId != -1 && selectedConfidenceId != -1
                        && selectedGoalsId != -1 && selectedValuesId != -1) {
                    // Get selected answers
                    String selectedPurpose = ((RadioButton) findViewById(selectedPurposeId)).getText().toString();
                    String selectedConfidence = ((RadioButton) findViewById(selectedConfidenceId)).getText().toString();
                    String selectedGoals = ((RadioButton) findViewById(selectedGoalsId)).getText().toString();
                    String selectedValues = ((RadioButton) findViewById(selectedValuesId)).getText().toString();
                    Log.d("Debug", "selectedStudy: " + selectedPurpose);
                    Log.d("Debug", "selectedTime: " + selectedConfidence);
                    Log.d("Debug", "selectedPoorStudy: " + selectedGoals);
                    Log.d("Debug", "selectedDisability: " + selectedValues);
                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Purpose", selectedPurpose);
                    editor.putString("Confidence", selectedConfidence);
                    editor.putString("Goals", selectedGoals);
                    editor.putString("Values", selectedValues);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedPurpose,selectedConfidence,selectedGoals,selectedValues);

                    // Display a success message
                    Toast.makeText(SurveyPage9.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Go to Next page
                    goToSurveyPage10();

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage9.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method takes the user back to the previous page once the back button has been clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                // Handle Back button click
                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String option1 = sharedPreferences.getString("Purpose",selectedStudy);
        String option2 = sharedPreferences.getString("Confidence",selectedTime);
        String option3 = sharedPreferences.getString("Goals",selectedPoorStudy);
        String option4 = sharedPreferences.getString("Values",selectedDisability);


        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {option1, option2, option3, option4};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String option1,String option2,String option3,String option4) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Values and Goals";
        // Add your question texts to the list here
        questionTexts.add("My purpose for getting a university education is clear");
        questionTexts.add("I feel confident I can reach my\n" +
                "goal to graduate from university");
        questionTexts.add("I set specific goals which lead\n" +
                "to success in my life ");
        questionTexts.add("I am clear about my values and\n" +
                "what is important to me ");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output15.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(option1,option2,option3,option4);
        PdfGenerator.generatePdf(SurveyPage9.this, output, surveyAnswers, questionTexts, mainQuestion);
    }
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * This method sets the color for all the members in a specific viewGroup.
     * @param viewGroup
     * @param color
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
     * This method links this page to the next page, surveyPage10
     */
    public void goToSurveyPage10() {
        Intent surveyPage10 = new Intent(this, SurveyPage10.class);
        surveyPage10.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(surveyPage10);
    }

    /**
     * This method links this page to the previous page, MindsetPage
     */
    public void goBack() {
        Intent Mindset = new Intent(this, MindsetPage.class);
        Mindset.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Mindset);
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage9.this, Hint.class);
        startActivity(Hint);
    }

    /**
     * This method changes the text on the progress bar based on where in the app the user is.
     */
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}
