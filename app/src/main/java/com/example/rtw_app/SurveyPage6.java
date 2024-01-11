package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;

public class SurveyPage6 extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page6);

        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        final RadioGroup option1_answers = findViewById(R.id.option1_answers);
        // final RadioGroup programmingRadioGroup = findViewById(R.id.programmingRadioGroup);

        TextView textView = (TextView) findViewById(R.id.surveyPage6_Question);
        textView.setText("How much of an impact did each of these potential social barriers have on your " +
                "experience last year:");
        //option1
        TextView textView1 = (TextView) findViewById(R.id.surveyPage6_Option1);
        textView1.setText("New independent status/living " +
                "away from home for the first " +
                "time");
        //option2
        TextView textview2 = (TextView) findViewById(R.id.surveyPage6_Option2);
        textview2.setText("Roommate problems");
        //option3
        TextView textview3 = (TextView) findViewById(R.id.surveyPage6_Option3);
        textview3.setText("Relationship worries ");
        //option4
        TextView textview4 = (TextView) findViewById(R.id.surveyPage6_Option4);
        textview4.setText("Loneliness");
        //option5
        TextView textView5 = (TextView) findViewById(R.id.surveyPage6_Option5);
        textView5.setText("Socially uncomfortable/shy");
        //option6
        TextView textView6 = (TextView) findViewById(R.id.surveyPage6_Option6);
        textView6.setText("Housing problems");
        //option7
        TextView textView7 = (TextView) findViewById(R.id.surveyPage6_Option7);
        textView7.setText("Homesickness");
        //option8
        TextView textView8 = (TextView) findViewById(R.id.surveyPage6_Option8);
        textView8.setText("Dislike Augustana");
        //option9
        TextView textView9 = (TextView) findViewById(R.id.surveyPage6_Option9);
        textView9.setText("Dislike university/studying");
        //option10
        TextView textView10 = (TextView) findViewById(R.id.surveyPage6_Option10);
        textView10.setText("Negative attitude");
        //option11
        TextView textView11 = (TextView) findViewById(R.id.surveyPage6_Option11);
        textView11.setText("Lack of Sleep");
        //option12
        TextView textView12 = (TextView) findViewById(R.id.surveyPage6_Option12);
        textView12.setText("Major illness or injury");
        //option13
        TextView textView13 = (TextView) findViewById(R.id.surveyPage6_Option13);
        textView13.setText("Challenges with mental health " +
                "(Depression, Anxiety, etc)");
        //option14
        TextView textView14 = (TextView) findViewById(R.id.surveyPage6_Option14);
        textView14.setText("Fear of failure");
        //option15
        TextView textView15 = (TextView) findViewById(R.id.surveyPage6_Option15);
        textView15.setText("Fear of not being perfect");
        //option16
        TextView textView16 = (TextView) findViewById(R.id.surveyPage6_Option16);
        textView16.setText("Fear of disappointing others ");
        //option17
        TextView textView17 = (TextView) findViewById(R.id.surveyPage6_Option17);
        textView17.setText("Previous failure");
        //option18
        TextView textView18 = (TextView) findViewById(R.id.surveyPage6_Option18);
        textView18.setText("Experiences of discrimination ");
        //option19
        TextView textView19 = (TextView) findViewById(R.id.surveyPage6_Option19);
        textView19.setText("Experiences of violence or " +
                "trauma ");

        Button nextButton = findViewById(R.id.submitButton);
        goToSurveyPage7();
    }

    public void goToSurveyPage7(){
        Intent SurveyPage4 = new Intent(this, SurveyPage7.class);
        startActivity(SurveyPage4);

    }
}