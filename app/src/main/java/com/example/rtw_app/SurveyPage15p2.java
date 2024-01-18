package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage15p2 extends AppCompatActivity {
    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page15p2);

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
        Intent SurveyPage16p1 = new Intent(this, SurveyPage16p1.class);
        startActivity(SurveyPage16p1);

    }

    public void goBack(){
        Intent SurveyPage15 = new Intent(this, SurveyPage15p1.class);
        startActivity(SurveyPage15);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage15p2.this, Hint.class);
        startActivity(Hint);
    }

}