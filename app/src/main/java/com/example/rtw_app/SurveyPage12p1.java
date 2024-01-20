package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

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

public class SurveyPage12p1 extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Button hint;

    private RadioGroup option1Group,option2Group,option3Group;
    private EditText FirstTextAnswer;
    private String userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page12p1);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        userInfo = getIntent().getStringExtra("userInfo");

        option1Group = findViewById(R.id.option1_answers);
        option2Group = findViewById(R.id.option2_answers);
        option3Group = findViewById(R.id.option3_answers);


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



                if (selectedOption1Id != -1 && selectedOption2Id != -1 && selectedOption3Id != -1) {
                    String selectedOption1 = ((RadioButton) findViewById(selectedOption1Id)).getText().toString();
                    String selectedOption2 = ((RadioButton) findViewById(selectedOption2Id)).getText().toString();
                    String selectedOption3 = ((RadioButton) findViewById(selectedOption3Id)).getText().toString();


                    // Call the generateAndSavePdf method
                    generateAndSavePdf(selectedOption1, selectedOption2, selectedOption3);
                    goToNextPage();

                } else {
                    Toast.makeText(SurveyPage12p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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

        String output = userInfo + "_output18.pdf";
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
        Intent nextPage = new Intent(this, SurveyPage12p2.class);
        nextPage.putExtra("userInfo", userInfo);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent SurveyPage10 = new Intent(this, SurveyPage11.class);
        SurveyPage10.putExtra("userInfo", userInfo);
        SurveyPage10.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage10);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage12p1.this, Hint.class);
        startActivity(Hint);
    }
}