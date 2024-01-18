package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyPage14p2 extends AppCompatActivity {

    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page14p2);



        hint = findViewById(R.id.hint);

        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
    }



    public void goToNextPage(){
        Intent nextPage = new Intent(this, SurveyPage15p1.class);
        startActivity(nextPage);

    }

    public void goBack(){
        Intent Education_And_Goals2 = new Intent(this, SurveyPage14p1.class);
        startActivity(Education_And_Goals2);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
        Intent Hint = new Intent(SurveyPage14p2.this, Hint.class);
        startActivity(Hint);
    }
}