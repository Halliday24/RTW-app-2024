package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage13p1 extends AppCompatActivity {
//need to add Education and Goals to top header
    private Button hint;

    private RadioGroup option1Group,option2Group,option3Group,option4Group,option5Group;
    private String userInfo;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page13p1);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        userInfo = getIntent().getStringExtra("userInfo");

        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        option3Group = findViewById(R.id.option3_answers);
        option4Group = findViewById(R.id.option4_answers);
        option5Group = findViewById(R.id.option5_answers);
        TextView textView = (TextView) findViewById(R.id.surveyPage13p1_Question);
        textView.setText("Education and Goals");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.Education_And_Goals_Option1);
        textView1.setText("I want to complete a degree");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.Education_And_Goals_Option2);
        textview2.setText("I feel confident in my major " +
                "choice  ");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.Education_And_Goals_Option3);
        textview3.setText("I am considering graduate " +
                "school");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.Education_And_Goals_Option4);
        textview4.setText("I’m actively exploring career " +
                "options and pursuing " +
                "opportunities to gain relevant " +
                "experience ");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.Education_And_Goals_Option5);
        textview5.setText("I know what it takes to be in the " +
                "career I have chosen ");

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
                // Retrieve values from radio buttons
                int selectedOption1Id = option1Group.getCheckedRadioButtonId();
                int selectedOption2Id = option2Group.getCheckedRadioButtonId();
                int selectedOption3Id = option3Group.getCheckedRadioButtonId();
                int selectedOption4Id = option4Group.getCheckedRadioButtonId();
                int selectedOption5Id = option5Group.getCheckedRadioButtonId();

                if (selectedOption1Id != -1 && selectedOption2Id != -1 && selectedOption3Id != -1
                        && selectedOption4Id != -1 && selectedOption5Id != -1) {
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(selectedOption3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(selectedOption4Id)).getText().toString();
                    String selectedOption5 = ((RadioButton) findViewById(selectedOption5Id)).getText().toString();

                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5);
                    goToNextPage();


                } else {
                    Toast.makeText(SurveyPage13p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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

    private void generateAndSavePdf(String selectedOption1, String selectedOption2,
                                    String selectedOption3, String selectedOption4,
                                    String selectedOption5) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Education and Goals";

        // Add your question texts to the list here
        questionTexts.add("I want to complete a degree");
        questionTexts.add("I feel confident in my major choice");
        questionTexts.add("I am considering graduate school");
        questionTexts.add("I’m actively exploring career options and pursuing opportunities to gain relevant experience");
        questionTexts.add("I know what it takes to be in the career I have chosen");

        String output = userInfo + "_output20.pdf";

        PdfGenerator.generatePdf(SurveyPage13p1.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5),
                questionTexts, mainQuestion);
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2,
                                            String selectedOption3, String selectedOption4,
                                            String selectedOption5) {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5};
        answersList.add(surveyAnswers);

        return answersList;
    }

    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage13p2.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        nextPage.putExtra("userInfo", userInfo);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Self_efficacy2 = new Intent(this, SurveyPage12p2.class);
        Self_efficacy2.putExtra("userInfo", userInfo);
        startActivity(Self_efficacy2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage13p1.this, Hint.class);
        startActivity(Hint);
    }
}