package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage14p2 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;


    private String userInfo;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page14p2);
        userInfo = getIntent().getStringExtra("userInfo");
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        checkBox11 = findViewById(R.id.checkBox11);
        checkBox12 = findViewById(R.id.checkBox12);
        checkBox13 = findViewById(R.id.checkBox13);
        checkBox14 = findViewById(R.id.checkBox14);
        checkBox15 = findViewById(R.id.checkBox15);
        checkBox16 = findViewById(R.id.checkBox16);
        checkBox17 = findViewById(R.id.checkBox17);


        //hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the generateAndSavePdf method
                generateAndSavePdf();
            }
        });
    }

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "What time Management tools have worked for you in the past?";

        // Add your question texts to the list here
        questionTexts.add("Planner/Agenda");
        questionTexts.add("Whiteboard");
        questionTexts.add("Phone Alert");
        questionTexts.add("eClass");
        questionTexts.add("Calendar");
        questionTexts.add("None");
        questionTexts.add("Other App");

        String output = userInfo + "_output23.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage14p2.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);
    }

    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                String.valueOf(checkBox11.isChecked()),
                String.valueOf(checkBox12.isChecked()),
                String.valueOf(checkBox13.isChecked()),
                String.valueOf(checkBox14.isChecked()),
                String.valueOf(checkBox15.isChecked()),
                String.valueOf(checkBox16.isChecked()),
                String.valueOf(checkBox17.isChecked())
        };
        answersList.add(surveyAnswers);

        return answersList;
    }


    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage15p1.class);
        nextPage.putExtra("userInfo", userInfo);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Education_And_Goals2 = new Intent(this, SurveyPage14p1.class);
        Education_And_Goals2.putExtra("userInfo", userInfo);
        startActivity(Education_And_Goals2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage14p2.this, Hint.class);
        startActivity(Hint);
    }
}