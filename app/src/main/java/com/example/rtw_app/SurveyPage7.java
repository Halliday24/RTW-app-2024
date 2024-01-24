package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage7 extends AppCompatActivity {

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
    //Variables for the radiogroup buttons in the xml
    private RadioGroup option1RadioGroup;
    private RadioGroup option2RadioGroup;
    private RadioGroup option3RadioGroup2;
    private RadioGroup option4RadioGroup;
    //Variable for the hint button
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page7);

        //Get the progressbar and progress text.
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        //get the current number from the sharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        //update the progress bar
        updateProgress();

        // Initialize your RadioGroup instances
        option1RadioGroup = findViewById(R.id.option1_answers);
        option2RadioGroup = findViewById(R.id.option2_answers);
        option3RadioGroup2 = findViewById(R.id.option3_answers);
        option4RadioGroup = findViewById(R.id.option4_answers);
        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        //set the text for the question and the options
        TextView textView = (TextView) findViewById(R.id.surveyPage7_Question);
        textView.setText("How much of an impact did each of these potential transportation barriers have on your " +
                "ability to participate in your education:");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage7_Option1);
        textView1.setText("Long commute times to " +
                "campus");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage7_Option2);
        textview2.setText("Lack of personal vehicle to get " +
                "to campus");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage7_Option3);
        textview3.setText("Challenges getting to shops to " +
                "purchase required supplies ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage7_Option4);
        textview4.setText("Transportation costs");


        buttonNext=findViewById(R.id.nextButton);
        hint = findViewById(R.id.hint);
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

        //Set an onClick listener for using the next button
        buttonNext=findViewById(R.id.nextButton);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            /**
             * This method increments the current question integer if this page hasn't been visited
             * before, checks if all the options are answered.
             * If they are then it saves the user's answers to the shared preferences and moves to the next page.
             * @param view
             */
            @Override
            public void onClick(View view) {

                if(currentQuestion<13){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }

                // Get selected RadioButton IDs from each RadioGroup
                int option1Id = option1RadioGroup.getCheckedRadioButtonId();
                int option2Id = option2RadioGroup.getCheckedRadioButtonId();
                int option3Id = option3RadioGroup2.getCheckedRadioButtonId();
                int option4Id = option4RadioGroup.getCheckedRadioButtonId();



                // Check if all the questions are answered
                if (option1Id != -1 && option2Id != -1 && option3Id != -1 &&
                        option4Id != -1) {


                    // Get the text of the selected RadioButton for each question
                    String selectedOption1 = ((RadioButton) findViewById(option1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(option2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(option3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(option4Id)).getText().toString();



                    // Update SharedPreferences with selected answers
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("option1", selectedOption1);
                    editor.putString("option2", selectedOption2);
                    editor.putString("option3", selectedOption3);
                    editor.putString("option4", selectedOption4);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();



                    // Update progress bar
                    updateProgress();

                    // Display success message
                    Toast.makeText(SurveyPage7.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedOption1,selectedOption2,selectedOption3,selectedOption4);

                } else {
                    // Display a message to answer all questions
                    Toast.makeText(SurveyPage7.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack=findViewById(R.id.BackButton);

        //set a click listener for the back Button
        buttonBack.setOnClickListener(new View.OnClickListener(){
            /**
             * This method takes the user back to the previous page once the back button has been clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {

                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
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

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String option1 = sharedPreferences.getString("option1",selectedStudy);
        String option2 = sharedPreferences.getString("option2",selectedTime);
        String option3 = sharedPreferences.getString("option3",selectedPoorStudy);
        String option4 = sharedPreferences.getString("option4",selectedDisability);


        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {option1, option2, option3, option4};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String option1, String option2, String option3, String option4) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential transportation barriers have\n" +
                "on your ability to participate in your education?";
        // Add your question texts to the list here
        questionTexts.add("Long commute times to\n" +
                "campus");
        questionTexts.add("Lack of personal vehicle to get\n" +
                "to campus");
        questionTexts.add("Challenges getting to shops to\n" +
                "purchase required supplies");
        questionTexts.add("Transportation costs");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output13.pdf";


        List<String[]> surveyAnswers = getSurveyAnswers(option1, option2, option3, option4);
        PdfGenerator.generatePdf(SurveyPage7.this, output, surveyAnswers, questionTexts, mainQuestion);
        goToSurveyPage8();
    }

    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * This method links this page to the next page, surveyPage8
     */
    public void goToSurveyPage8(){
        Intent SurveyPage8 = new Intent(this, SurveyPage8.class);
        SurveyPage8.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage8);

    }

    /**
     * This method links this page to the previous page, surveyPage6p4
     */
    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage6p4.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage7.this, Hint.class);
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