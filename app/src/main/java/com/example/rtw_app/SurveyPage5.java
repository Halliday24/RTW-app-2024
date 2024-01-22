package com.example.rtw_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//5 should be Please indicate how much of an impact each of these potential financial barriers had
// on your ability to participate in your education but the s=xml is still correct just missing words
public class   SurveyPage5 extends AppCompatActivity {
    private String userInfo;
    private SharedPreferences sharedPreferences;
    private Button hint;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page5);

        // Initialize your RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Initialize UI elements
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();






                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1) {


                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();



                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.apply();


                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability);

                    Toast.makeText(SurveyPage5.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToNextPage();
                } else {
                    Toast.makeText(SurveyPage5.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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

    private void handleNextButtonClick() {
        RadioGroup studyRadioGroup = findViewById(R.id.studyRadioGroup);
        RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        RadioGroup poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);
        RadioGroup disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        // Get selected answers
        String selectedMoney = getSelectedRadioButtonText(studyRadioGroup);
        String selectedAid = getSelectedRadioButtonText(timeRadioGroup);
        String selectedManagement = getSelectedRadioButtonText(poorStudyRadioGroup2);
        String selectedTools = getSelectedRadioButtonText(disabilityRadioGroup);

        // Store responses in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Worried about money", selectedMoney);
        editor.putString("Inadequate financial aid/scholarship", selectedAid);
        editor.putString("Limited financial management skills", selectedManagement);
        editor.putString("Unable to purchase textbooks, laptop, or other necessary learning tools", selectedTools);
        editor.apply();

        // Display a success message
        Toast.makeText(SurveyPage5.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to the next page
        goToNextPage();


    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }
    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedStudy, String selectedTime, String selectedPoorStudy, String selectedDisability) {
        List<String[]> answersList = new ArrayList<>();
        // Add your survey answers to the list here
        // For example, you can retrieve answers from SharedPreferences
        String study = sharedPreferences.getString("impact_study",selectedStudy);
        String time = sharedPreferences.getString("impact_time", selectedTime);
        String poorStudy = sharedPreferences.getString("impact_poor_study", selectedPoorStudy);
        String disability = sharedPreferences.getString("impact_disability", selectedDisability);


        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {study, time, poorStudy, disability};
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
    // Method to generate and save PDF
    private void generateAndSavePdf(String selectedStudy,String selectedTime,String selectedPoorStudy,String selectedDisability) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Please indicate how much of an impact each of these potential financial barriers\n" +
                "had on your ability to participate in your education?";
        // Add your question texts to the list here
        questionTexts.add("Worried about money and\n" +
                "ability to pay for basic needs");
        questionTexts.add("Inadequate financial\n" +
                "aid/scholarship");
        questionTexts.add("Limited financial management\n" +
                "skills");
        questionTexts.add("Unable to purchase textbooks,\n" +
                "laptop, or other necessary\n" +
                "learning tools");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output8.pdf";


        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability);
        PdfGenerator.generatePdf(SurveyPage5.this, output, surveyAnswers, questionTexts, mainQuestion);
    }
    private void goToNextPage() {
        Intent intent = new Intent(this, SurveyPage6p1.class); // Replace with the actual next activity
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }

    public void goBack(){
        Intent impactAcademicPage2 = new Intent(this, SurveyPage4.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        impactAcademicPage2.putExtra("userInfo", userInfo);
        startActivity(impactAcademicPage2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage5.this, Hint.class);
        startActivity(Hint);
    }
}
