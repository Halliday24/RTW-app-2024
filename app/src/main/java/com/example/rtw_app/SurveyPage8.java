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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//written response section under survey page 8
public class SurveyPage8 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private EditText FirstAnswer;
    private  EditText SecondAnswer;

    private String userInfo;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page8);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        TextView textView = (TextView) findViewById(R.id.surveyPage8_Question);
        textView.setText("Please describe any other barriers, including any extenuating circumstances, you " +
                "may have encountered. For example: family emergencies or child care issues. ");

        TextView textView2 = (TextView) findViewById(R.id.surveyPage8_Question2);
        textView2.setText("What are the primary sources of stress in your life?");

        FirstAnswer = findViewById(R.id.FirstAnswer);
        SecondAnswer = findViewById(R.id.SecondAnswer);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button buttonNext=findViewById(R.id.nextButton);

        //set a click listener for the next Button
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int option1Id = FirstAnswer.getId();
                int option2Id = SecondAnswer.getId();


                if (option1Id != -1 && option2Id != -1) {


                    String selectedOption1 = ((EditText) findViewById(option1Id)).getText().toString();
                    String selectedOption2 = ((EditText) findViewById(option2Id)).getText().toString();




                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("option1", selectedOption1);
                    editor.putString("option2", selectedOption2);

                    editor.apply();


                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedOption1,selectedOption2);

                    Toast.makeText(SurveyPage8.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToMindsetPage();
                } else {
                    Toast.makeText(SurveyPage8.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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
    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime ){
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String option1 = sharedPreferences.getString("option1",selectedStudy);
        String option2 = sharedPreferences.getString("option2",selectedTime);



        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {option1, option2};
        answersList.add(surveyAnswers);

        return answersList;
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String option1,String option2) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "";
        // Add your question texts to the list here
        questionTexts.add("Please describe any other barriers, including any extenuating circumstances, you \" +\n" +
                "                \"may have encountered. For example: family emergencies or child care issues.?");
        questionTexts.add("What are the primary sources of stress in your life");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output14.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(option1,option2);
        PdfGenerator.generatePdf(SurveyPage8.this, output, surveyAnswers, questionTexts, mainQuestion);
    }

    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }
    public void goToMindsetPage(){
        Intent MindsetPage = new Intent(this, MindsetPage.class);
        MindsetPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        MindsetPage.putExtra("userInfo", userInfo);
        startActivity(MindsetPage);

    }

    public void goBack(){
        Intent SurveyPage7 = new Intent(this, SurveyPage7.class);
        SurveyPage7.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage7.putExtra("userInfo", userInfo);
        startActivity(SurveyPage7);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage8.this, Hint.class);
        startActivity(Hint);
    }
}