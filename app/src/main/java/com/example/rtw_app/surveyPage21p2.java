package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

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

public class surveyPage21p2 extends AppCompatActivity {

    private Button hint;
    private String userInfo;


    private SharedPreferences sharedPreferences;
    private RadioGroup colorRadioGroup, timeRadioGroup;
    private EditText answerbox, answerbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p2);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);
        userInfo = getIntent().getStringExtra("userInfo");
        hint = findViewById(R.id.hint);

        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        timeRadioGroup = findViewById(R.id.timeRadioGroup);
        answerbox = findViewById(R.id.answerbox);
        answerbox2= findViewById(R.id.answerbox2);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
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
        Button generatePdfButton = findViewById(R.id.nextButton);
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
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
        questionTexts.add("I experience social anxiety in\n" +
                "academic settings ");
        questionTexts.add("I know how to get involved on\n" +
                "campus " );
        questionTexts.add("In which of the above areas are things working well for you?" );
        questionTexts.add("What challenges are most important for you to address right now?" );

        String output = userInfo + "_output34.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(surveyPage21p2.this, output,
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
                answerbox.getText().toString(),
                answerbox2.getText().toString()
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
        Intent EndPage = new Intent(this, EndPage.class);
        EndPage.putExtra("userInfo", userInfo);
        startActivity(EndPage);

    }

    public void goBack(){
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        SurveyPage21p1.putExtra("userInfo", userInfo);
        startActivity(SurveyPage21p1);

    }

    private void openHint() {
        Intent Hint = new Intent(surveyPage21p2.this, Hint.class);
        startActivity(Hint);
    }

}