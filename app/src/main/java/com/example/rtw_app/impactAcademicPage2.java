package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class impactAcademicPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_academic_page2);

        Button submitButton = findViewById(R.id.nextButton);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToImpactAcademicPage3();
            }
        });
    }

    public void goToImpactAcademicPage3(){
        Intent impactAcademicPage3 = new Intent(this, impactAcademicPage3.class);
        startActivity(impactAcademicPage3);

    }
}