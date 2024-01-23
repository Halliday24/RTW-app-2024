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

public class SurveyPage13p2 extends AppCompatActivity {

    private Button hint;

    private RadioGroup option1Group,option2Group;
    private String userInfo;

    private SharedPreferences sharedPreferences;

    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";

    private ProgressBar progressBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page13p2);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        updateProgress();

        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        TextView textView = (TextView) findViewById(R.id.surveyPage13p2_Question);
        textView.setText("Education and Goals");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.Education_And_Goals2_Option1);
        textView1.setText("I know my strengths and how " +
                "they will help me be successful");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.Education_And_Goals2_Option2);
        textview2.setText("I have a faculty or staff mentor");

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentQuestion<21){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                // Retrieve values from radio buttons
                int selectedOption1Id = option1Group.getCheckedRadioButtonId();
                int selectedOption2Id = option2Group.getCheckedRadioButtonId();

                if (selectedOption1Id != -1 && selectedOption2Id != -1) {
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();

                    updateProgress();
                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedOption1, selectedOption2);
                    goToNextPage();


                } else {
                    Toast.makeText(SurveyPage13p2.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack=findViewById(R.id.BackButton);

        //set a click listener for the next Button
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }



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

    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage14p1.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Education_And_Goals = new Intent(this, SurveyPage13p1.class);
        Education_And_Goals.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Education_And_Goals);

    }
    private void generateAndSavePdf(String selectedOption1, String selectedOption2) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Education and Goals (Part 2)";

        // Add your question texts to the list here
        questionTexts.add("I know my strengths and how they will help me be successful");
        questionTexts.add("I have a faculty or staff mentor");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output21.pdf";

        PdfGenerator.generatePdf(SurveyPage13p2.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2),
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
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2) {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2};
        answersList.add(surveyAnswers);

        return answersList;
    }


    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage13p2.this, Hint.class);
        startActivity(Hint);
    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}