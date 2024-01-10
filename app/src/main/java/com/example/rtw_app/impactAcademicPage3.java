package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class impactAcademicPage3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_academic_page3);

        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToImpactWorkPage();
            }
        });
    }

    public void goToImpactWorkPage(){
        Intent impactWorkPage = new Intent(this, ImpactWorkPage.class);
        startActivity(impactWorkPage);

    }
}