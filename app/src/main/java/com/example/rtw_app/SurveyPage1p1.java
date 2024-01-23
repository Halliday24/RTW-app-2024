package com.example.rtw_app;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class SurveyPage1p1 extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private String userInfo;

    private static final String KEY_CURRENT_QUESTION = "current_question";

    private int currentQuestion;
    private int totalQuestions = 35; // Set the total number of questions
    private ProgressBar progressBar;
    private TextView progressText;
    private RadioGroup studyRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup, preparationRadioGroup;
    private Button hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p1);


        // get the progressBar and progressText
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        // Initialize your RadioGroup instances
        studyRadioGroup = findViewById(R.id.studyRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        preparationRadioGroup = findViewById(R.id.preparationRadioGroup);


        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        // Load the saved currentQuestion value from SharedPreferences
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION, currentQuestion);
        //update the information on the progress bar
        updateProgress();

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        //Next button
        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Move to the next question
                //if this question hasn't been answered, then increment
                if(currentQuestion<1){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }

                //go to the next question
                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
                int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();

                Log.d("Debug", "selectedStudy: " + selectedColorId);
                Log.d("Debug", "selectedTime: " + selectedTimeId);
                Log.d("Debug", "selectedPoorStudy: " + selectedPoorStudyId);
                Log.d("Debug", "selectedDisability: " + selectedDisabilityId);
                Log.d("Debug", "selectedPreparation: " + selectedPreparationId);




                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1 && selectedPreparationId != -1) {


                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();
                    String selectedPreparation = ((RadioButton) findViewById(selectedPreparationId)).getText().toString();


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putString("impact_color5", selectedPreparation);
                    //storing the increased current Question
                    editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                    editor.putInt("Total_questions", totalQuestions);
                    editor.apply();


                    //update the progress bar
                    updateProgress();
                    // Generate PDF after submitting survey
                    generateAndSavePdf(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability,selectedPreparation);

                    Toast.makeText(SurveyPage1p1.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactAcademicPage2();
                }

                else {
                    Toast.makeText(SurveyPage1p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });


        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }


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


    public void goToImpactAcademicPage2() {
        //updateProgress();
        Intent impactAcademicPage2 = new Intent(this, SurveyPage1p2.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);

    }


    public void goBack() {
        Intent impactAcademicPage2 = new Intent(this, InstructionPage.class);
        impactAcademicPage2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage2);


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


    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    // Method to generate and save PDF
    private void generateAndSavePdf(String selectedStudy,String selectedTime,String selectedPoorStudy,String selectedDisability,String selectedPreparation) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "How much of an impact did each of these potential academic barriers have on your learning and grades last year?";
        // Add your question texts to the list here
        questionTexts.add("Ineffective study habits?");
        questionTexts.add("Poor time management?");
        questionTexts.add("Poor study environment?");
        questionTexts.add("Learning disability?");
        questionTexts.add("Ineffective academic preparation?");
        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

        // Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output1.pdf";

        Log.d("Debug", "selectedStudy: " + selectedStudy);
        Log.d("Debug", "selectedTime: " + selectedTime);
        Log.d("Debug", "selectedPoorStudy: " + selectedPoorStudy);
        Log.d("Debug", "selectedDisability: " + selectedDisability);
        Log.d("Debug", "selectedPreparation: " + selectedPreparation);

        List<String[]> surveyAnswers = getSurveyAnswers(selectedStudy,selectedTime,selectedPoorStudy,selectedDisability,selectedPreparation);
        PdfGenerator.generatePdf(SurveyPage1p1.this, output, surveyAnswers, questionTexts, mainQuestion);
    }



    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage1p1.this, Hint.class);
        startActivity(Hint);
    }

}

