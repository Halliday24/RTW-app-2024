package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage16p1 extends AppCompatActivity {
    private Button hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page16p);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
    }

    public void goTo(){
        Intent SurveyPage16p2 = new Intent(this, SurveyPage16p2.class);
        startActivity(SurveyPage16p2);

    }

    public void goBack(){
        Intent SurveyPage15p2 = new Intent(this, SurveyPage15p2.class);
        startActivity(SurveyPage15p2);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage16p1.this, Hint.class);
        startActivity(Hint);
    }

}