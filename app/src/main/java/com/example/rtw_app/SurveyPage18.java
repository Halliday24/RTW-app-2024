/**
 * The SurveyPage18 class represents the another section in our application.
 * The class includes functionality to navigate through survey questions and get user responses
 */
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

public class SurveyPage18 extends AppCompatActivity {

    private Button hint;
    private String userInfo;
    private SharedPreferences sharedPreferences;
    private int currentQuestion;
    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private ProgressBar progressBar;
    private TextView progressText;
    private RadioGroup colorRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    /**
     * Called when the activity is first created. This is where the UI is initialized along with
     * where event listeners are made.
     *
     * @param savedInstanceState contains the previously saved state of the activitity if existing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page18);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        // Initialize your RadioGroup instances
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHint();
            }
        });

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion < 29) {
                    currentQuestion++;
                } else {
                    currentQuestion = currentQuestion;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                updateProgress();
                // Call the generateAndSavePdf method
                generateAndSavePdf();
            }
        });

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the goBack method
                goBack();

            }
        });
    }

    /**
     * This method generates and saves a PDF with survey responses and displays the users responses
     * on a new blank text page.
     */
    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Test Preparation Skills";

        // Add your question texts to the list here
        questionTexts.add("My study time for tests is\n" +
                "organized and thorough ");
        questionTexts.add("I begin studying for tests\n" +
                "several days before the test");
        questionTexts.add("I tend to do well on tests\n" +
                "without a lot of preparation ");
        questionTexts.add("I do well on tests when I am well prepared");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output29.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage18.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Call the goTo method after generating and saving the PDF
        goTo();
    }

    /**
     * This method gets the users information by using SharedPreferences.
     *
     * @return An array containing the users information
     */
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * This method gets survey answers from EditTexts and returns them as a list.
     *
     * @return A list containing survey answers
     */
    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                getSelectedRadioButtonText(colorRadioGroup),
                getSelectedRadioButtonText(timeRadioGroup),
                getSelectedRadioButtonText(poorStudyRadioGroup),
                getSelectedRadioButtonText(disabilityRadioGroup),
        };
        answersList.add(surveyAnswers);

        return answersList;

    }

    /**
     * This method gets the text of the selected radio button inside a RadioGroup.
     *
     * @param radioGroup The RadioGroup with the radio buttons
     * @return the text of the selected radio button
     */
    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    /**
     * This method sends the user to the next page.
     */
    public void goTo() {
        Intent SurveyPage19 = new Intent(this, SurveyPage19.class);
        SurveyPage19.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage19);

    }

    /**
     * This method sends the user to the previous page.
     */
    public void goBack() {
        Intent SurveyPage17 = new Intent(this, SurveyPage17.class);
        SurveyPage17.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage17);

    }

    /**
     * This method opens the hint page.
     */
    private void openHint() {
        Intent Hint = new Intent(SurveyPage18.this, Hint.class);
        startActivity(Hint);
    }

    /**
     * Updates the progress bar and text based on the current question and amount of questions
     */
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }


}