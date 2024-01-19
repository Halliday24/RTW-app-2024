package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage21p1 extends AppCompatActivity {

    private Button hint;

    private String userInfo;


    private SharedPreferences sharedPreferences;
    private RadioGroup colorRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p1);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        userInfo = getIntent().getStringExtra("userInfo");

        // Initialize your RadioGroup instances
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);


        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the generateAndSavePdf method
                generateAndSavePdf();
            }
        });
    }

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Social Engagement";

        // Add your question texts to the list here
        questionTexts.add("I am able to say “no” to\n" +
                "activities when I should study ");
        questionTexts.add("My friends are serious about\n" +
                "education ");
        questionTexts.add("I have a support system which\n" +
                "encourages my success in\n" +
                "school ");
        questionTexts.add("I balance time for study and\n" +
                "play ");

        String output = userInfo + "_output33.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage21p1.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Call the goTo method after generating and saving the PDF
        goTo();
    }

    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                getSelectedRadioButtonText(colorRadioGroup),
                getSelectedRadioButtonText(timeRadioGroup),
                getSelectedRadioButtonText(poorStudyRadioGroup),
                getSelectedRadioButtonText(disabilityRadioGroup)
        };
        answersList.add(surveyAnswers);

        return answersList;
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    public void goTo(){
        Intent SurveyPage21p2 = new Intent(this, surveyPage21p2.class);
        SurveyPage21p2.putExtra("userInfo", userInfo);
        startActivity(SurveyPage21p2);

    }

    public void goBack(){
        Intent SurveyPage20p2 = new Intent(this, SurveyPage20p2.class);
        SurveyPage20p2.putExtra("userInfo", userInfo);
        startActivity(SurveyPage20p2);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage21p1.this, Hint.class);
        startActivity(Hint);
    }

}