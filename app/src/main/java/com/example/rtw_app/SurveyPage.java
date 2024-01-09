package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;

public class SurveyPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup colorRadioGroup = findViewById(R.id.colorRadioGroup);
        final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();
                int selectedProgrammingId = programmingRadioGroup.getCheckedRadioButtonId();

                if (selectedColorId != -1 && selectedProgrammingId != -1) {
                    // Get selected answers
                    String selectedColor = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedProgramming = ((RadioButton) findViewById(selectedProgrammingId)).getText().toString();

                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("favorite_color", selectedColor);
                    editor.putString("enjoy_programming", selectedProgramming);
                    editor.apply();

                    //


                    // Display a success message
                    Toast.makeText(SurveyPage.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

//                    // Check if the app has permission to write to external storage
//                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        // Request permission
//                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    } else {
//                        // Permission already granted, proceed with generating PDF
//                        generatePDF(selectedColor, selectedProgramming);
//                    }
//
//                    generatePDF(selectedColor, selectedProgramming);

                    // Finish the survey activity
                    finish();
                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(SurveyPage.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}