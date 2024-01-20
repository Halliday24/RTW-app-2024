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

public class SurveyPage14p1 extends AppCompatActivity {
    private RadioGroup option1Group,option2Group,option3Group,option4Group,option5Group;
    private String userInfo;

    private SharedPreferences sharedPreferences;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page14p1);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        userInfo = getIntent().getStringExtra("userInfo");

        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        option3Group = findViewById(R.id.option3_answers);
        option4Group = findViewById(R.id.option4_answers);
        option5Group = findViewById(R.id.option5_answers);
        TextView textView = (TextView) findViewById(R.id.surveyPage14p1_Question);
        textView.setText("Time Management");

        //option1
        TextView textView1 = (TextView) findViewById(R.id.TimeManagement_Option1);
        textView1.setText("I make plans each week about " +
                "how I will spend my time");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.TimeManagement_Option2);
        textview2.setText("I follow through with the plans I " +
                "make around how I spend my " +
                "time");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.TimeManagement_Option3);
        textview3.setText("I complete major assignments " +
                "and hand them in on time ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.TimeManagement_Option4);
        textview4.setText("I study at least 2 hours for " +
                "every hour I spend in class");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.TimeManagement_Option5);
        textview5.setText("I can estimate how much time " +
                "a task is going to take");
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

                if (selectedOption1Id != -1 && selectedOption2Id != -1 &&
                        selectedOption3Id != -1 && selectedOption4Id != -1 && selectedOption5Id != -1) {

                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(selectedOption3Id)).getText().toString();
                    String selectedOption4 = ((RadioButton) findViewById(selectedOption4Id)).getText().toString();
                    String selectedOption5 = ((RadioButton) findViewById(selectedOption5Id)).getText().toString();

                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5);
                    goToNextPage();

                    // ... (rest of the existing code)
                } else {
                    Toast.makeText(SurveyPage14p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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

    private void generateAndSavePdf(String selectedOption1, String selectedOption2,
                                    String selectedOption3, String selectedOption4, String selectedOption5) {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Time Management";

        // Add your question texts to the list here
        questionTexts.add("I make plans each week about how I will spend my time");
        questionTexts.add("I follow through with the plans I make around how I spend my time");
        questionTexts.add("I complete major assignments and hand them in on time");
        questionTexts.add("I study at least 2 hours for every hour I spend in class");
        questionTexts.add("I can estimate how much time a task is going to take");

        String output = userInfo + "_output22.pdf";

        PdfGenerator.generatePdf(SurveyPage14p1.this, output,
                getSurveyAnswers(selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5),
                questionTexts, mainQuestion);
    }

    // Method to get survey answers in a list
    private List<String[]> getSurveyAnswers(String selectedOption1, String selectedOption2,
                                            String selectedOption3, String selectedOption4, String selectedOption5) {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {selectedOption1, selectedOption2, selectedOption3, selectedOption4, selectedOption5};
        answersList.add(surveyAnswers);

        return answersList;
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
        Intent nextPage = new Intent(this, SurveyPage14p2.class);
        nextPage.putExtra("userInfo", userInfo);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Education_And_Goals2 = new Intent(this, SurveyPage13p2.class);
        Education_And_Goals2.putExtra("userInfo", userInfo);
        Education_And_Goals2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(Education_And_Goals2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage14p1.this, Hint.class);
        startActivity(Hint);
    }
}