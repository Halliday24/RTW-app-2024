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

public class SurveyPage15p1 extends AppCompatActivity {

    private Button hint;
    private String userInfo;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page15p1);
        userInfo = getIntent().getStringExtra("userInfo");
        sharedPreferences = getSharedPreferences("impact_responses", MODE_PRIVATE);

        final RadioGroup colorRadioGroup = findViewById(R.id.colorRadioGroup);
        final RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        final RadioGroup poorStudyRadioGroup2 = findViewById(R.id.poorStudyRadioGroup2);

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup2.getCheckedRadioButtonId();

                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 ) {

                    // Get selected answers
                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);

                    editor.apply();

                    // Display a success message
                    Toast.makeText(SurveyPage15p1.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    generateAndSavePdf();
                    goTo();

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage15p1.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
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

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
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

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Class Preparation";

        // Add your question texts to the list here
        questionTexts.add("I review the syllabus for each\n" +
                "course and understand the\n" +
                "expectations ");
        questionTexts.add("I complete my small\n" +
                "assignments with 24 hours\n" +
                "after class\n");
        questionTexts.add("I read the assigned reading\n" +
                "before class");

        String output = userInfo + "_output24.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(SurveyPage15p1.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);
    }

    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                sharedPreferences.getString("impact_study", ""),
                sharedPreferences.getString("impact_time", ""),
                sharedPreferences.getString("impact_poor_study", "")
        };
        answersList.add(surveyAnswers);

        return answersList;
    }
    public void goTo(){
        Intent SurveyPage15p2 = new Intent(this, SurveyPage15p2.class);
        SurveyPage15p2.putExtra("userInfo", userInfo);
        SurveyPage15p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage15p2);

    }

    public void goBack(){
        Intent SurveyPage14p2 = new Intent(this, SurveyPage14p2.class);
        SurveyPage14p2.putExtra("userInfo", userInfo);
        SurveyPage14p2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage14p2);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage15p1.this, Hint.class);
        startActivity(Hint);
    }

}