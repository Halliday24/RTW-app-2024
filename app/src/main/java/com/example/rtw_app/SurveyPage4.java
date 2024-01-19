package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage4 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private RadioGroup option1RadioGroup;
    private RadioGroup option2RadioGroup;
    private RadioGroup option3RadioGroup2;
    private RadioGroup option4RadioGroup;

    private String userInfo;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page4);
        userInfo = getIntent().getStringExtra("userInfo");
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage4_Question);
        textView.setText("How much of an impact did each of these potential free-time barriers" +
                " have on your ability to participate in your education:");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage4_Option1);
        textView1.setText("Social Media (Facebook, " +
                "Instagram, YouTube, " +
                "Snapchat, TikTok, Reddit, etc)");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage4_Option2);
        textview2.setText("Too much screen time (video " +
                "games, streaming, internet, " +
                "etc)");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage4_Option3);
        textview3.setText("A very active social life ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage4_Option4);
        textview4.setText("Overextended in my " +
                "extracurricular activities");

        option1RadioGroup = findViewById(R.id.option1_answers);
        option2RadioGroup = findViewById(R.id.option2_answers);
        option3RadioGroup2 = findViewById(R.id.option3_answers);
        option4RadioGroup = findViewById(R.id.option4_answers);

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int option1Id = option1RadioGroup.getCheckedRadioButtonId();
                int option2Id = option2RadioGroup.getCheckedRadioButtonId();
                int option3Id = option3RadioGroup2.getCheckedRadioButtonId();
                int option4Id = option4RadioGroup.getCheckedRadioButtonId();



                if (option1Id != -1 && option2Id != -1 && option3Id != -1 &&
                        option4Id != -1) {


                    String selectedOption1 = ((RadioButton) findViewById(option1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(option2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(option3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(option4Id)).getText().toString();



                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("option1", selectedOption1);
                    editor.putString("option2", selectedOption2);
                    editor.putString("option3", selectedOption3);
                    editor.putString("option4", selectedOption4);
                    editor.apply();


                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedOption1,selectedOption2,selectedOption3,selectedOption4);

                    Toast.makeText(SurveyPage4.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactFinancial();
                } else {
                    Toast.makeText(SurveyPage4.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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
    private void generateAndSavePdf(String option1,String option2,String option3,String option4) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential free-time barriers have on your ability to participate in your education?";
        // Add your question texts to the list here
        questionTexts.add("Social Media (Facebook,\n" +
                "Instagram, YouTube,\n" +
                "Snapchat, TikTok, Reddit, etc)?");
        questionTexts.add("Too much screen time (video\n" +
                "games, streaming, internet,\n" +
                "etc)");
        questionTexts.add("A very active social life");
        questionTexts.add("Overextended in my\n" +
                "extracurricular activities ");
        String output = userInfo + "_output7.pdf";

        List<String[]> surveyAnswers = getSurveyAnswers(option1,option2,option3,option4);
        PdfGenerator.generatePdf(SurveyPage4.this, output, surveyAnswers, questionTexts, mainQuestion);
    }
    public void goToImpactFinancial(){
        Intent SurveyPage5 = new Intent(this, SurveyPage5.class);
        SurveyPage5.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SurveyPage5.putExtra("userInfo", userInfo);
        startActivity(SurveyPage5);

    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage3p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        impactAcademicPage2.putExtra("userInfo", userInfo);
        startActivity(impactAcademicPage2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage4.this, Hint.class);
        startActivity(Hint);
    }
}