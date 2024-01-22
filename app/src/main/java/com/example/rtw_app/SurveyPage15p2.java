package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage15p2 extends AppCompatActivity {
    private Button hint;
    private SharedPreferences sharedPreferences;
    private RadioGroup FirstAnswer;
    private RadioGroup SecondAnswer;
    private String userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        setContentView(R.layout.activity_survey_page15p2);

        FirstAnswer = findViewById(R.id.colorRadioGroup);
        SecondAnswer = findViewById(R.id.timeRadioGroup);
        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button BackButton = findViewById(R.id.nextButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the generateAndSavePdf method
                generateAndSavePdf();
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
    }

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Class Preparation";

        // Add your question texts to the list here
        questionTexts.add("I have access to my textbook\n" +
                "and class materials ");
        questionTexts.add("I turn in assignments by the set\n" +
                "deadlines\n");

        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name + ccid + "_output25.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage15p2.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Call the goTo method after generating and saving the PDF
        goTo();
    }
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }
    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                getSelectedRadioButtonText(FirstAnswer),
                getSelectedRadioButtonText(SecondAnswer)
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
        Intent SurveyPage16p1 = new Intent(this, SurveyPage16p1.class);
        SurveyPage16p1.putExtra("userInfo", userInfo);
        SurveyPage16p1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage16p1);

    }

    public void goBack(){
        Intent SurveyPage15 = new Intent(this, SurveyPage15p1.class);
        SurveyPage15.putExtra("userInfo", userInfo);
        SurveyPage15.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage15);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage15p2.this, Hint.class);
        startActivity(Hint);
    }

}