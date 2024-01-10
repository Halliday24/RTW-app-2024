package com.example.rtw_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImpactFinancial extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_financial_page);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        // Initialize UI elements
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                handleNextButtonClick();
            }
        });
    }

    private void handleNextButtonClick() {
        RadioGroup moneyRadioGroup = findViewById(R.id.moneyRadioGroup);
        RadioGroup aidRadioGroup = findViewById(R.id.aidRadioGroup);
        RadioGroup managementRadioGroup = findViewById(R.id.managementRadioGroup);
        RadioGroup toolsRadioGroup = findViewById(R.id.toolsRadioGroup);

        // Get selected answers
        String selectedMoney = getSelectedRadioButtonText(moneyRadioGroup);
        String selectedAid = getSelectedRadioButtonText(aidRadioGroup);
        String selectedManagement = getSelectedRadioButtonText(managementRadioGroup);
        String selectedTools = getSelectedRadioButtonText(toolsRadioGroup);

        // Store responses in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Worried about money", selectedMoney);
        editor.putString("Inadequate financial aid/scholarship", selectedAid);
        editor.putString("Limited financial management skills", selectedManagement);
        editor.putString("Unable to purchase textbooks, laptop, or other necessary learning tools", selectedTools);
        editor.apply();

        // Display a success message
        Toast.makeText(ImpactFinancial.this, "Survey submitted successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to the next page
        //goToNextPage();
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            return ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();
        }
        return "";
    }

    //private void goToNextPage() {
       // Intent intent = new Intent(this, NextActivity.class); // Replace with the actual next activity
        //startActivity(intent);
  //  }
}
