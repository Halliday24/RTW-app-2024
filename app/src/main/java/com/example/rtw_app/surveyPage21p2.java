/**
 * The SurveyPage21p2 class represents the second part of a section in our application.
 * The class includes functionality to navigate through survey questions, save user responses,
 * generate PDF reports, and display progress indicators.
 */
package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class surveyPage21p2 extends AppCompatActivity {

    private Button hint;
    private String userInfo;
    private SharedPreferences sharedPreferences;
    private int currentQuestion;
    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private ProgressBar progressBar;
    private TextView progressText;
    private RadioGroup colorRadioGroup, timeRadioGroup;
    private EditText answerbox, answerbox2;

    /**
     * Called when the activity is first created. This is where the UI is initialized along with
     * where event listeners are made.
     *
     * @param savedInstanceState contains the previously saved state of the activitity if existing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p2);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        updateProgress();

        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        answerbox = findViewById(R.id.answerbox);
        answerbox2 = findViewById(R.id.answerbox2);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
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
        Button generatePdfButton = findViewById(R.id.nextButton);
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion < 34) {
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
     * This method generates and saves a PDF with survey responses and displays the users responses
     * on a new blank text page.
     */
    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Social Engagement";

        // Add your question texts to the list here
        questionTexts.add("I experience social anxiety in\n" +
                "academic settings ");
        questionTexts.add("I know how to get involved on\n" +
                "campus ");
        questionTexts.add("In which of the above areas are things working well for you?");
        questionTexts.add("What challenges are most important for you to address right now?");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output34.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(surveyPage21p2.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Call the goTo method after generating and saving the PDF
        goTo();
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
                answerbox.getText().toString(),
                answerbox2.getText().toString()
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
        Intent EndPage = new Intent(this, EndPage.class);
        EndPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(EndPage);

    }

    /**
     * This method sends the user to the previous page.
     */
    public void goBack() {
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        SurveyPage21p1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage21p1);

    }

    /**
     * This method opens the hint page.
     */
    private void openHint() {
        Intent Hint = new Intent(surveyPage21p2.this, Hint.class);
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