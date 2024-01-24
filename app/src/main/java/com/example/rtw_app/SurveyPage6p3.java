package com.example.rtw_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * SurveyPage6p3 Activity class represents survey 6 of the RTW-workbook.
 */
public class SurveyPage6p3 extends AppCompatActivity {


    // SharedPreferences to store and retrieve data
    private SharedPreferences sharedPreferences;

    // Current question number
    private int currentQuestion;

    //total number of questions
    private final int totalQuestions = 35;

    //String holder for the current questions integer in Shared Preferences
    private static final String KEY_CURRENT_QUESTION = "current_question";
    // Variable for the progressbar Widget in the xml
    private ProgressBar progressBar;
    //Variable for the progressText Widget in the xml
    private TextView progressText;
    //Variables for the radiogroup buttons in the xml
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup, preparationRadioGroup;
    //Variable for the hint button
    private Button hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page6p3);
        //Get the progressbar and progress text.
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        //get the current number from the sharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        //update the progress bar
        updateProgress();


        // Initialize your RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        preparationRadioGroup = findViewById(R.id.preparationRadioGroup);

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

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method increments the current question integer if this page hasn't been visited
             * before, checks if all the options are answered.
             * If they are then it saves the user's answers to the shared preferences and moves to the next page.
             * @param view
             */
            @Override
            public void onClick(View view) {
                //if this page hasn't been visited before then increment
                if(currentQuestion<11){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }

                // Get selected RadioButton IDs from each RadioGroup
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
                int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();


                // Check if all the questions are answered
                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1 && selectedPreparationId != -1) {


                    // Get the text of the selected RadioButton for each question
                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();
                    String selectedPreparation = ((RadioButton) findViewById(selectedPreparationId)).getText().toString();


                    // Update SharedPreferences with selected answers
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putString("impact_color5", selectedPreparation);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability,selectedPreparation);

                    // Display success message
                    Toast.makeText(SurveyPage6p3.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Navigate to the next page
                    goToNextPage();
                } else {
                    // Display a message to answer all questions
                    Toast.makeText(SurveyPage6p3.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
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


    // Save the state of RadioButton selections
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        // Get selected RadioButton IDs from each RadioGroup
        int selectedStudyId = studyRadioGroup.getCheckedRadioButtonId();
        int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
        int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
        int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
        int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();


        // Save the IDs to the bundle
        outState.putInt("selectedStudyId", selectedStudyId);
        outState.putInt("selectedTimeId", selectedTimeId);
        outState.putInt("selectedPoorStudyId", selectedPoorStudyId);
        outState.putInt("selectedDisabilityId", selectedDisabilityId);
        outState.putInt("selectedPreparationId", selectedPreparationId);
    }


    // Restore the state of RadioButton selections
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        // Retrieve the selected RadioButton IDs from the bundle
        int selectedStudyId = savedInstanceState.getInt("selectedStudyId");
        int selectedTimeId = savedInstanceState.getInt("selectedTimeId");
        int selectedPoorStudyId = savedInstanceState.getInt("selectedPoorStudyId");
        int selectedDisabilityId = savedInstanceState.getInt("selectedDisabilityId");
        int selectedPreparationId = savedInstanceState.getInt("selectedPreparationId");


        // Check the corresponding RadioButtons based on the saved IDs
        studyRadioGroup.check(selectedStudyId);
        timeRadioGroup.check(selectedTimeId);
        poorStudyRadioGroup.check(selectedPoorStudyId);
        disabilityRadioGroup.check(selectedDisabilityId);
        preparationRadioGroup.check(selectedPreparationId);
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
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }

    /**
     * This method links this page to the next page, surveyPage6p4
     */
    public void goToNextPage() {

        Intent nextPage = new Intent(this, SurveyPage6p4.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);


    }


    /**
     * This method links this page to the previous page, surveyPage6p2
     */
    public void goBack() {

        Intent myIntent = new Intent(SurveyPage6p3.this, SurveyPage6p2.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage6p3.this.startActivity(myIntent);
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy,String selectedTime,String selectedPoorStudy,String selectedDisability,String selectedPreparation) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String study = sharedPreferences.getString("impact_study",selectedStudy);
        String time = sharedPreferences.getString("impact_time", selectedTime);
        String poorStudy = sharedPreferences.getString("impact_poor_study", selectedPoorStudy);
        String disability = sharedPreferences.getString("impact_disability", selectedDisability);
        String preparation = sharedPreferences.getString("impact_color5", selectedPreparation);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {study, time, poorStudy, disability, preparation};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String selectedStudy,String selectedTime,String selectedPoorStudy,String selectedDisability,String selectedPreparation) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential social barriers have on your\n" +
                "experience last year?";
        // Add your question texts to the list here
        questionTexts.add("Lack of Sleep");
        questionTexts.add("Major illness or injury");
        questionTexts.add("Challenges with mental health\n" +
                "(Depression, Anxiety, etc)");
        questionTexts.add("Fear of not being perfect");
        questionTexts.add("Learning disability?");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output11.pdf";
        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability,selectedPreparation);
        PdfGenerator.generatePdf(SurveyPage6p3.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * This method changes the text on the progress bar based on where in the app the user is.
     */
    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage6p3.this, Hint.class);
        startActivity(Hint);
    }

}

