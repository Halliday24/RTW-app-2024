package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage20p2 extends AppCompatActivity {

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page20p2);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openHint();
            }
        });
    }

    public void goTo(){
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        startActivity(SurveyPage21p1);

    }

    public void goBack(){
        Intent SurveyPage20 = new Intent(this, SurveyPage20p2.class);
        startActivity(SurveyPage20);

    }
}