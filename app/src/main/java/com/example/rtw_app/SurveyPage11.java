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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class SurveyPage11 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ImageView TopGreenBar;
    private ImageView BottomGreenBar;
    private CheckBox degreeCheckBox;
    private CheckBox helpOthersCheckBox;
    private CheckBox getJobCheckBox;
    private CheckBox supportFamilyCheckBox;
    private CheckBox lifeGoalCheckBox;
    private CheckBox differenceCheckBox;
    private CheckBox learnCheckBox;
    private CheckBox unsureCheckBox;
    private CheckBox otherCheckBox;
    private EditText otherReasonEditText;
    private EditText gainEditText;
    private Button backButton;
    private Button nextButton;
    private Button hint;

    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions
    private static final String KEY_CURRENT_QUESTION = "current_question";

    private ProgressBar progressBar;
    private TextView progressText;
    private String userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page11);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        updateProgress();




        TopGreenBar = findViewById(R.id.TopGreenBar);
        BottomGreenBar = findViewById(R.id.BottomGreenBar);
        degreeCheckBox = findViewById(R.id.degreeCheckBox);
        helpOthersCheckBox = findViewById(R.id.helpOthersCheckBox);
        getJobCheckBox = findViewById(R.id.getJobCheckBox);
        supportFamilyCheckBox = findViewById(R.id.supportFamilyCheckBox);
        lifeGoalCheckBox = findViewById(R.id.lifeGoalCheckBox);
        differenceCheckBox = findViewById(R.id.differenceCheckBox);
        learnCheckBox = findViewById(R.id.learnCheckBox);
        unsureCheckBox = findViewById(R.id.unsureCheckBox);
        otherCheckBox = findViewById(R.id.otherCheckBox);
        otherReasonEditText = findViewById(R.id.otherReasonEditText);
        gainEditText = findViewById(R.id.gainEditText);
        backButton = findViewById(R.id.BackButton);
        nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentQuestion<17){
                    currentQuestion++;
                }
                else{
                    currentQuestion=currentQuestion;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();

                int optionOtherId = otherReasonEditText.getId();
                int degreeID = degreeCheckBox.getId();
                int helpOthersID = helpOthersCheckBox.getId();
                int getJobID = getJobCheckBox.getId();
                int supportFamilyID = supportFamilyCheckBox.getId();
                int lifeGoalID = lifeGoalCheckBox.getId();
                int differenceID = differenceCheckBox.getId();
                int learnID = learnCheckBox.getId();
                int unsureID = unsureCheckBox.getId();
                int gainId =  gainEditText.getId();
                if (optionOtherId != -1 && degreeID != -1 && helpOthersID !=-1 && getJobID != -1
                && supportFamilyID != -1 && lifeGoalID != -1 && differenceID != -1
                && learnID !=-1 && unsureID != -1) {





                    // Retrieve values from checkboxes and EditText
                    boolean selectedDegree = degreeCheckBox.isChecked();
                    boolean selectedHelp = helpOthersCheckBox.isChecked();
                    boolean selectedJob = getJobCheckBox.isChecked();
                    boolean selectedFamily = supportFamilyCheckBox.isChecked();
                    boolean selectedGoal = lifeGoalCheckBox.isChecked();
                    boolean selectedDifference = differenceCheckBox.isChecked();
                    boolean selectedLearn = learnCheckBox.isChecked();
                    boolean selectedUnsure = unsureCheckBox.isChecked();
                    String selectedOther = otherReasonEditText.getText().toString();
                    String selectedGain = gainEditText.getText().toString();

                    updateProgress();

                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedDegree, selectedHelp, selectedJob, selectedFamily,
                            selectedGoal, selectedDifference, selectedLearn, selectedUnsure, selectedOther,selectedGain);



                    Toast.makeText(SurveyPage11.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToNextPage();
                } else {
                    Toast.makeText(SurveyPage11.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    private void generateAndSavePdf(boolean selectedDegree, boolean selectedHelp, boolean selectedJob,
                                    boolean selectedFamily, boolean selectedGoal, boolean selectedDifference,
                                    boolean selectedLearn, boolean selectedUnsure, String selectedOther,String selectedGain) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Select the reasons that influenced your decision to return to school (check all that apply):";

        // Add your question texts to the list here
        questionTexts.add("Degree or certification pursuit");
        questionTexts.add("Desire to help others");
        questionTexts.add("Desire to get a job or improve job prospects");
        questionTexts.add("Supporting family or dependents");
        questionTexts.add("Pursuing a life goal");
        questionTexts.add("Making a difference in the community");
        questionTexts.add("Learning for personal growth");
        questionTexts.add("Unsure");
        questionTexts.add("Other (please specify):");
        questionTexts.add("What do you want to gain from or contribute to society during your lifetime?");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output17.pdf";
       List<String[]> surveyAnswers =  getSurveyAnswers(selectedDegree, selectedHelp, selectedJob, selectedFamily, selectedGoal,
                selectedDifference, selectedLearn, selectedUnsure, selectedOther,selectedGain);
        PdfGenerator.generatePdf(SurveyPage11.this, output,surveyAnswers, questionTexts, mainQuestion);


    }
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }
    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(boolean selectedDegree, boolean selectedHelp, boolean selectedJob,
                                            boolean selectedFamily, boolean selectedGoal, boolean selectedDifference,
                                            boolean selectedLearn, boolean selectedUnsure, String selectedOther,String selectedGain) {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                String.valueOf(selectedDegree),
                String.valueOf(selectedHelp),
                String.valueOf(selectedJob),
                String.valueOf(selectedFamily),
                String.valueOf(selectedGoal),
                String.valueOf(selectedDifference),
                String.valueOf(selectedLearn),
                String.valueOf(selectedUnsure),
                selectedOther,
                selectedGain
        };
        answersList.add(surveyAnswers);

        return answersList;
    }

    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage12p1.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }
    private void goBack() {
        Intent SurveyPage10 = new Intent(this, SurveyPage10.class);
        SurveyPage10.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage10);
    }




    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof android.widget.TextView) {
                // Check if the view is a TextView
                android.widget.TextView textView = (android.widget.TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                // If the view is a ViewGroup, recursively call the method
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage11.this, Hint.class);
        startActivity(Hint);
    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }

}
