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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage12p1 extends AppCompatActivity {

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
    private RadioGroup option1Group,option2Group,option3Group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page12p1);

        //Get the progressbar and progress text.
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        //get the current number from the sharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        //update the progress bar
        updateProgress();

        // Initialize your RadioGroup instances
        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        option3Group = findViewById(R.id.option3_answers);

        //set the text for the question and the options
        TextView textView = (TextView) findViewById(R.id.self_efficacy_Question2);
        textView.setText("Self-Efficacy and Growth Mindset");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.self_efficacy_Option1);
        textView1.setText("Overall, I’m a pretty positive " +
                "person and I believe in my " +
                "potential");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.self_efficacy_Option2);
        textview2.setText("I believe my academic ability is " +
                "something I can substantially " +
                "change");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.self_efficacy_Option3);
        textview3.setText("Hard work, focus, and " +
                "perseverance determine my " +
                "results");

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

                if(currentQuestion<18){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }
                // Update SharedPreferences with new currentQuestion number.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();

                // Retrieve values from radio buttons
                int selectedOption1Id = option1Group.getCheckedRadioButtonId();
                int selectedOption2Id = option2Group.getCheckedRadioButtonId();
                int selectedOption3Id = option3Group.getCheckedRadioButtonId();



                // Check if all the questions are answered
                if (selectedOption1Id != -1 && selectedOption2Id != -1 && selectedOption3Id != -1) {

                    // Get the text of the selected RadioButton for each question
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(selectedOption3Id)).getText().toString();


                    //update the progress bar
                    updateProgress();
                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3);

                    goToNextPage();

                } else {
                    // Display a message to answer all questions
                    Toast.makeText(SurveyPage12p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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
    private void generateAndSavePdf(String selectedOption1, String selectedOption2, String selectedOption3) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Self-Efficacy and Growth Mindset";

        // Add your question texts to the list here
        questionTexts.add("Overall, I’m a pretty positive\n" +
                "person and I believe in my\n" +
                "potential ");
        questionTexts.add("I believe my academic ability is\n" +
                "something I can substantially\n" +
                "change");
        questionTexts.add("Hard work, focus, and\n" +
                "perseverance determine my\n" +
                "results\n");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output18.pdf";
        List<String[]> surveyAnswers = getSurveyAnswers(selectedOption1, selectedOption2, selectedOption3);
        PdfGenerator.generatePdf(SurveyPage12p1.this, output, surveyAnswers,
                questionTexts, mainQuestion);
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2, String selectedOption3){
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2, selectedOption3};
        answersList.add(surveyAnswers);

        return answersList;
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
     * This method links this page to the next page, surveyPage12p2
     */
    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage12p2.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    /**
     * This method links this page to the previous page, surveyPage11
     */
    public void goBack(){
        Intent SurveyPage10 = new Intent(this, SurveyPage11.class);
        SurveyPage10.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage10);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage12p1.this, Hint.class);
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