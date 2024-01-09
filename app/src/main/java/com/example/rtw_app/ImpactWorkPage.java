package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Bundle;

public class ImpactWorkPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_work_page);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup hoursRadioGroup = findViewById(R.id.hoursRadioGroup);
        final RadioGroup lateRadioGroup = findViewById(R.id.lateRadioGroup);
        final RadioGroup unemployedRadioGroup = findViewById(R.id.unemployedRadioGroup);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHoursId = hoursRadioGroup.getCheckedRadioButtonId();
                int selectedLateId = lateRadioGroup.getCheckedRadioButtonId();
                int selectedUnemployedId = unemployedRadioGroup.getCheckedRadioButtonId();

                if (selectedHoursId != -1 && selectedLateId != -1 && selectedUnemployedId != -1) {
                    // Get selected answers
                    String selectedHours = ((RadioButton) findViewById(selectedHoursId)).getText().toString();
                    String selectedLate = ((RadioButton) findViewById(selectedLateId)).getText().toString();
                    String selectedUnemployed = ((RadioButton) findViewById(selectedUnemployedId)).getText().toString();
                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Work Too many hours", selectedHours);
                    editor.putString("Work late hours, or schedules\n" +
                            "that conflict with class time", selectedLate);
                    editor.putString("Unemployed", selectedUnemployed);
                    editor.apply();

                    //


                    // Display a success message
                    Toast.makeText(ImpactWorkPage.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

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

                    // Go to Next page

                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(ImpactWorkPage.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}