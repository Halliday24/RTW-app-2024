package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//this file is responsible for the end message that will be displayed once completed the workbook
public class EndPage extends AppCompatActivity {

    private Button hint;
    private String userInfo;


    private SharedPreferences sharedPreferences;
    private EditText answerbox, answerbox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_page);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);


        Button buttonNext=findViewById(R.id.nextButton);

        answerbox = findViewById(R.id.answerbox);
        answerbox2= findViewById(R.id.answerbox2);
        //set a click listener for the next Button
        // Set a click listener for the Next button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndSavePdf();
            }
        });
        Button buttonBack=findViewById(R.id.BackButton);

        //set a click listener for the next Button
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);

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

    private void generateAndSavePdf() {
        List<String> questionTexts = new ArrayList<>();
        String mainQuestion = "Social Engagement ";

        questionTexts.add("What new habits do you plan to develop to support your academic success? Are\n" +
                "there changes you feel need to be made so that you can fully participate in your\n" +
                "education? ");
        // Add your question texts to the list here
        questionTexts.add("What support or resources will be most useful for you to address these\n" +
                "challenges next year?");


        // Example of calling the method to get user information
        String[] userInfoArray = getUserInfoFromSharedPreferences();

// Access the individual elements
        String name = userInfoArray[0];
        String ccid = userInfoArray[1];

        String output = name +ccid + "_output35.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(EndPage.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Display a success message
        Toast.makeText(EndPage.this, "PDF generated and saved successfully!", Toast.LENGTH_SHORT).show();

        // Call the goTo method after generating and saving the PDF
        goTo();
    }
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }
    private List<String[]> getSurveyAnswers() {
        List<String[]> answersList = new ArrayList<>();

        // Create an array with the survey answers and add it to the list
        String[] surveyAnswers = {
                answerbox.getText().toString(),
                answerbox2.getText().toString()
        };
        answersList.add(surveyAnswers);

        return answersList;
    }
    private void setTextColorForAllTextViews(ViewGroup viewGroup, int color) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof TextView) {
                // Check if the view is a TextView
                TextView textView = (TextView) childView;
                textView.setTextColor(color);
            } else if (childView instanceof ViewGroup) {
                // If the view is a ViewGroup, recursively call the method
                setTextColorForAllTextViews((ViewGroup) childView, color);
            }
        }
    }


    public void goTo(){
        Intent EndPageP2 = new Intent(this, EndPageP2.class);
        EndPageP2.putExtra("userInfo", userInfo);
        EndPageP2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(EndPageP2);

    }

    public void goBack(){
        Intent SurveyPage21p1 = new Intent(this, surveyPage21p2.class);
        SurveyPage21p1.putExtra("userInfo", userInfo);
        SurveyPage21p1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(SurveyPage21p1);

    }

    private void openHint() {
        Intent Hint = new Intent(EndPage.this, Hint.class);
        startActivity(Hint);
    }

}