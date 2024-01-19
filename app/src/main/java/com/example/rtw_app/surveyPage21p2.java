package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class surveyPage21p2 extends AppCompatActivity {

    private Button hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page21p2);

        hint = findViewById(R.id.hint);
        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });

        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the goBack method
                goBack();

            }
        });
    }

    public void goTo(){
        Intent EndPage = new Intent(this, EndPage.class);
        startActivity(EndPage);

    }

    public void goBack(){
        Intent SurveyPage21p1 = new Intent(this, SurveyPage21p1.class);
        startActivity(SurveyPage21p1);

    }

    private void openHint() {
        Intent Hint = new Intent(surveyPage21p2.this, Hint.class);
        startActivity(Hint);
    }

}