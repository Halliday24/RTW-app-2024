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

public class SurveyPage17 extends AppCompatActivity {

    private Button hint;
    private String userInfo;


    private SharedPreferences sharedPreferences;
    private RadioGroup colorRadioGroup, timeRadioGroup, poorStudyRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page17);
        userInfo = getIntent().getStringExtra("userInfo");



        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button

        // Initialize your RadioGroup instances
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
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
        String mainQuestion = "Reading and Writing Skills";

        // Add your question texts to the list here
        questionTexts.add("My long papers have multiple\n" +
                "drafts ");
        questionTexts.add("I feel confident about my\n" +
                "writing ability");
        questionTexts.add("Second Additional Question");
        questionTexts.add("Second Additional Question");
        String output = userInfo + "_output28.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage17.this, output,
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
            return ((RadioButton)findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }
    public void goTo(){
        Intent SurveyPage18 = new Intent(this, SurveyPage18.class);
        SurveyPage18.putExtra("userInfo", userInfo);
        SurveyPage18.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage18);

    }

    public void goBack(){
        Intent SurveyPage16p2 = new Intent(this, SurveyPage16p2.class);
        SurveyPage16p2.putExtra("userInfo", userInfo);
        SurveyPage16p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage16p2);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage17.this, Hint.class);
        startActivity(Hint);
    }

}