package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class impactAcademicPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_academic_page);

        sharedPreferences = getSharedPreferences("impact_responses", MODE_PRIVATE);

        final RadioGroup studyRadioGroup = findViewById(R.id.studyRadioGroup);
        final RadioGroup timeRadioGroup = findViewById(R.id.timeRadioGroup);
        final RadioGroup poorStudyRadioGroup = findViewById(R.id.poorStudyRadioGroup2);
        final RadioGroup disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);
        final RadioGroup preparationRadioGroup = findViewById(R.id.preparationRadioGroup);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedColorId = studyRadioGroup.getCheckedRadioButtonId();
                int selectedTimeId = timeRadioGroup.getCheckedRadioButtonId();
                int selectedPoorStudyId = poorStudyRadioGroup.getCheckedRadioButtonId();
                int selectedDisabilityId = disabilityRadioGroup.getCheckedRadioButtonId();
                int selectedPreparationId = preparationRadioGroup.getCheckedRadioButtonId();

                if (selectedColorId != -1 && selectedTimeId != -1 && selectedPoorStudyId != -1 &&
                        selectedDisabilityId != -1 && selectedPreparationId != -1) {

                    // Get selected answers
                    String selectedStudy = ((RadioButton) findViewById(selectedColorId)).getText().toString();
                    String selectedTime = ((RadioButton) findViewById(selectedTimeId)).getText().toString();
                    String selectedPoorStudy = ((RadioButton) findViewById(selectedPoorStudyId)).getText().toString();
                    String selectedDisability = ((RadioButton) findViewById(selectedDisabilityId)).getText().toString();
                    String selectedPreparation = ((RadioButton) findViewById(selectedPreparationId)).getText().toString();

                    // Store responses in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("impact_study", selectedStudy);
                    editor.putString("impact_time", selectedTime);
                    editor.putString("impact_poor_study", selectedPoorStudy);
                    editor.putString("impact_disability", selectedDisability);
                    editor.putString("impact_color5", selectedPreparation);
                    editor.apply();

                    // Display a success message
                    Toast.makeText(impactAcademicPage.this, "Impact survey submitted successfully!", Toast.LENGTH_SHORT).show();
                    goToImpactAcademicPage2();
                } else {
                    // Display an error message if not all questions are answered
                    Toast.makeText(impactAcademicPage.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToImpactAcademicPage2(){
        Intent impactAcademicPage2 = new Intent(this, impactAcademicPage2.class);
        startActivity(impactAcademicPage2);

    }
}
