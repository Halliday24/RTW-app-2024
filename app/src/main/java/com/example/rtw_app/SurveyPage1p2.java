package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyPage1p2 extends AppCompatActivity {

    private RadioGroup prepRadioGroup, readWriteRadioGroup, mathSkillsRadioGroup, disabilityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page1p2);

        prepRadioGroup = findViewById(R.id.prepRadioGroup);
        readWriteRadioGroup = findViewById(R.id.readWriteRadioGroup);
        mathSkillsRadioGroup = findViewById(R.id.mathSkillsRadioGroup);
        disabilityRadioGroup = findViewById(R.id.disabilityRadioGroup);

        Button submitButton = findViewById(R.id.nextButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToImpactAcademicPage3();
            }
        });

        Button buttonBack = findViewById(R.id.BackButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
    }

    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof TextView) {
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }

    public void goToImpactAcademicPage3() {
        Intent impactAcademicPage3 = new Intent(this, SurveyPage1p3.class);
        impactAcademicPage3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage3);
    }

    public void goBack() {
        Intent impactAcademicPage1 = new Intent(this, SurveyPage1p1.class);
        impactAcademicPage1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage1);
    }
}
