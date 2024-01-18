package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class SurveyPage11 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ImageView TopGreenBar;
    private ImageView BottomGreenBar;
    private CheckBox degreeCheckBox;
    private CheckBox helpOthersCheckBox;
    private CheckBox getJobCheckBox;
    private CheckBox supportFamilyCheckBox;
    private CheckBox lifeGoalCheckBox;
    private CheckBox differenceCheckBox;
    private CheckBox learnCheckBox;
    private CheckBox unsureCheckBox;
    private CheckBox otherCheckBox;
    private EditText otherReasonEditText;
    private Button backButton;
    private Button nextButton;
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page11);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        TopGreenBar = findViewById(R.id.TopGreenBar);
        BottomGreenBar = findViewById(R.id.BottomGreenBar);
        degreeCheckBox = findViewById(R.id.degreeCheckBox);
        helpOthersCheckBox = findViewById(R.id.helpOthersCheckBox);
        getJobCheckBox = findViewById(R.id.getJobCheckBox);
        supportFamilyCheckBox = findViewById(R.id.supportFamilyCheckBox);
        lifeGoalCheckBox = findViewById(R.id.lifeGoalCheckBox);
        differenceCheckBox = findViewById(R.id.differenceCheckBox);
        learnCheckBox = findViewById(R.id.learnCheckBox);
        unsureCheckBox = findViewById(R.id.unsureCheckBox);
        otherCheckBox = findViewById(R.id.otherCheckBox);
        otherReasonEditText = findViewById(R.id.otherReasonEditText);
        backButton = findViewById(R.id.BackButton);
        nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectAndStoreResponses();
                goToNextPage();
            }
        });

        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage12p1.class);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }
    private void goBack() {
        Intent SurveyPage10 = new Intent(this, SurveyPage10.class);
        startActivity(SurveyPage10);
    }

    private void collectAndStoreResponses() {
        // Collect and store user responses in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("degreeCheckBox", degreeCheckBox.isChecked());
        editor.putBoolean("helpOthersCheckBox", helpOthersCheckBox.isChecked());
        editor.putBoolean("getJobCheckBox", getJobCheckBox.isChecked());
        editor.putBoolean("supportFamilyCheckBox", supportFamilyCheckBox.isChecked());
        editor.putBoolean("lifeGoalCheckBox", lifeGoalCheckBox.isChecked());
        editor.putBoolean("differenceCheckBox", differenceCheckBox.isChecked());
        editor.putBoolean("learnCheckBox", learnCheckBox.isChecked());
        editor.putBoolean("unsureCheckBox", unsureCheckBox.isChecked());
        editor.putString("otherReason", otherReasonEditText.getText().toString());
        editor.apply();
    }


    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof android.widget.TextView) {
                // Check if the view is a TextView
                android.widget.TextView textView = (android.widget.TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                // If the view is a ViewGroup, recursively call the method
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage11.this, Hint.class);
        startActivity(Hint);
    }

}
