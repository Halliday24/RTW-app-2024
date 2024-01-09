package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;



public class SurveyPage3 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page3);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
       // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage3_Question);
        textView.setText("How did each of these potential major-related barriers impact your education?");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage3_Option1);
        textView1.setText("I was not admitted to my first choice program");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage3_Option2);
        textview2.setText("Unclear education or career goals");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage3_Option3);
        textview3.setText("Pressure to choose a path that was not a good fit for me");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage3_Option4);
        textview4.setText("Major related classes were unavailable");
        //option5
        TextView textview5 = (TextView) findViewById(R.id.surveyPage3_Option5);
        textview5.setText("Major not offered");
        //option6
        TextView textview6 = (TextView) findViewById(R.id.surveyPage3_Option6);
        textview6.setText("Unhappy with major");

        Button nextButton = findViewById(R.id.submitButton);
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TextView textView = (TextView) findViewById(R.id.surveyPage3_Question);
//                textView.setText("How did each of these potential major-related barriers impact your education?");
//                //option1
//                TextView textView1 = (TextView) findViewById(R.id.surveyPage3_Option1);
//                textView1.setText("I was not admitted to my first choice program");
//                //option2
//                TextView textview2 = (TextView) findViewById(R.id.surveyPage3_Option2);
//                textview2.setText("Unclear education or career goals");
//                //option3
//                TextView textview3 = (TextView) findViewById(R.id.surveyPage3_Option3);
//                textview3.setText("Pressure to choose a path that was not a good fit for me");
//                //option4
//                TextView textview4 = (TextView) findViewById(R.id.surveyPage3_Option4);
//                textview4.setText("Major related classes were unavailable");
//                //option5
//                TextView textview5 = (TextView) findViewById(R.id.surveyPage3_Option5);
//                textview5.setText("Major not offered");
//                //option6
//                TextView textview6 = (TextView) findViewById(R.id.surveyPage3_Option6);
//                textview6.setText("Unhappy with major");

//                int selectedColorId = option1_answers.getCheckedRadioButtonId();
//               // int selectedProgrammingId = programmingRadioGroup.getCheckedRadioButtonId();
//
//                if (selectedColorId != -1 && selectedProgrammingId != -1) {
//                    // Get selected answers
//                    String selectedColor = ((RadioButton) findViewById(selectedColorId)).getText().toString();
//                    String selectedProgramming = ((RadioButton) findViewById(selectedProgrammingId)).getText().toString();
//
//                    // Store responses in SharedPreferences
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("favorite_color", selectedColor);
//                    editor.putString("enjoy_programming", selectedProgramming);
//                    editor.apply();
//
//                    //
//
//
//                    // Display a success message
//                    Toast.makeText(SurveyPage3.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();
//
////                    // Check if the app has permission to write to external storage
////                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////                        // Request permission
////                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
////                    } else {
////                        // Permission already granted, proceed with generating PDF
////                        generatePDF(selectedColor, selectedProgramming);
////                    }
////
////                    generatePDF(selectedColor, selectedProgramming);
//
//                    //go to next SurveyPage
//                    //goToSurveyPage4();
//                } else {
//                    // Display an error message if not all questions are answered
//                    Toast.makeText(SurveyPage3.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
 //               }
   //       }
      //});
            }
        }