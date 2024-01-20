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

public class SurveyPage19 extends AppCompatActivity {

    private Button hint;

    private String userInfo;


    private SharedPreferences sharedPreferences;
    private RadioGroup colorRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page19);
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

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the goBack method
                goBack();

            }
        });
    }

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Test Taking Skills";

        // Add your question texts to the list here
        questionTexts.add("I complete my tests with time to\n" +
                "review ");
        questionTexts.add("When I study hard, my test\n" +
                "scores are good ");
        questionTexts.add("I feel confident about my test\n" +
                "and have low anxiety ");
        questionTexts.add("My test scores accurately\n" +
                "reflect my understanding of the\n" +
                "course");

        String output = userInfo + "_output30.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage19.this, output,
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
        Intent SurveyPage20 = new Intent(this, SurveyPage20p1.class);
        SurveyPage20.putExtra("userInfo", userInfo);
        SurveyPage20.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage20);

    }

    public void goBack(){
        Intent SurveyPage18 = new Intent(this, SurveyPage18.class);
        SurveyPage18.putExtra("userInfo", userInfo);
        SurveyPage18.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage18);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage19.this, Hint.class);
        startActivity(Hint);
    }

}