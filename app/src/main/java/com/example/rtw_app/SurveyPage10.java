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

public class SurveyPage10 extends AppCompatActivity {


    //Variables for the radiogroup buttons in the xml
    private RadioGroup option1RadioGroup2;
    private RadioGroup option2RadioGroup2;
    private RadioGroup option3RadioGroup2;
    private RadioGroup option4RadioGroup;
    private RadioGroup option5RadioGroup;

    //Variable for the hint button
    private Button hint;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_page10);

        //Get the progressbar and progress text.
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        //get the current number from the sharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        //update the progress bar
        updateProgress();

        //Set the text for the textview in the xml
        TextView textView = (TextView) findViewById(R.id.surveyPage9_Question);
        textView.setText("Attitude and Motivation");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage9_Option1);
        textView1.setText("Class lectures and discussion " +
                "stimulate me");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage9_Option2);
        textview2.setText("I enjoy and want to be at " +
                "university ");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage9_Option3);
        textview3.setText("There are one or two subjects " +
                "in school that I always enjoy  ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage9_Option4);
        textview4.setText(" I attend class ");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.surveyPage9_Option5);
        textview5.setText("I am engaged in class and in " +
                "small group discussions");

        // Initialize your RadioGroup instances
        option1RadioGroup2 = findViewById(R.id.option1_answers);
        option2RadioGroup2 = findViewById(R.id.option2_answers);
        option3RadioGroup2 = findViewById(R.id.option3_answers);
        option4RadioGroup = findViewById(R.id.option4_answers);
        option5RadioGroup = findViewById(R.id.option5_answers);
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

                if(currentQuestion<16){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }

                // Get selected RadioButton IDs from each RadioGroup
                int option1Id = option1RadioGroup2.getCheckedRadioButtonId();
                int option2Id = option2RadioGroup2.getCheckedRadioButtonId();
                int option3Id = option3RadioGroup2.getCheckedRadioButtonId();
                int option4Id = option4RadioGroup.getCheckedRadioButtonId();
                int option5Id = option5RadioGroup.getCheckedRadioButtonId();


                // Check if all the questions are answered
                if (option1Id != -1 && option2Id != -1 && option3Id != -1 &&
                        option4Id != -1) {


                    // Get the text of the selected RadioButton for each question
                    String selectedOption1 = ((RadioButton) findViewById(option1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(option2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(option3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(option4Id)).getText().toString();
                    String selectedOption5 = ((RadioButton) findViewById(option5Id)).getText().toString();


                    // Update SharedPreferences with selected answers
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("option1", selectedOption1);
                    editor.putString("option2", selectedOption2);
                    editor.putString("option3", selectedOption3);
                    editor.putString("option4", selectedOption4);
                    editor.putString("option4", selectedOption5);
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();

                    // Update progress bar
                    updateProgress();

                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedOption1,selectedOption2,selectedOption3,selectedOption4,selectedOption5);

                    // Display success message
                    Toast.makeText(SurveyPage10.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate to the next page
                    goToNextPage();
                } else {
                    // Display a message to answer all questions
                    Toast.makeText(SurveyPage10.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack=findViewById(R.id.BackButton);

        //set a click listener for the next Button
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
    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability,String option5Select) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String option1 = sharedPreferences.getString("option1",selectedStudy);
        String option2 = sharedPreferences.getString("option2",selectedTime);
        String option3 = sharedPreferences.getString("option3",selectedPoorStudy);
        String option4 = sharedPreferences.getString("option4",selectedDisability);
        String option5 = sharedPreferences.getString("option5",option5Select);

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {option1, option2, option3, option4,option5};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String option1,String option2,String option3,String option4,String option5) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential free-time barriers have on your ability to participate in your education?";
        // Add your question texts to the list here
        questionTexts.add("Class lectures and discussion\n" +
                "stimulate me");
        questionTexts.add("I enjoy and want to be at\n" +
                "university ");
        questionTexts.add("There are one or two subjects\n" +
                "in school that I always enjoy " +
                "extracurricular activities ");
        questionTexts.add("I attend class");
        questionTexts.add("I am engaged in class and in\n" +
                "small group discussions\n");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid+ "_output16.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(option1,option2,option3,option4,option5);
        PdfGenerator.generatePdf(SurveyPage10.this, output, surveyAnswers, questionTexts, mainQuestion);
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
     * This method links this page to the next page, surveyPage11
     */
    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage11.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    /**
     * This method links this page to the previous page, surveyPage9
     */
    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage9.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage10.this, Hint.class);
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