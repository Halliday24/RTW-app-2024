package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage19 extends AppCompatActivity {

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page19);

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
        Intent SurveyPage20 = new Intent(this, SurveyPage20p1.class);
        startActivity(SurveyPage20);

    }

    public void goBack(){
        Intent SurveyPage18 = new Intent(this, SurveyPage18.class);
        startActivity(SurveyPage18);

    }

    private void openHint() {
        Intent Hint = new Intent(SurveyPage19.this, Hint.class);
        startActivity(Hint);
    }

}