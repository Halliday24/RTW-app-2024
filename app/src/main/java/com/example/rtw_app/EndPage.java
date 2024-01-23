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


/**
 * This class is responsible for displaying finale questions before the workbook is done.
 * Includes functional methods such as: generateAndSavePDF, openHint, goBack and goTo.
 */
public class EndPage extends AppCompatActivity {

    private Button hint;
    private String userInfo;
    private SharedPreferences sharedPreferences;
    private EditText answerbox, answerbox2;

    /**
     * Called when the activity is first created. This is where the UI is initialized along with
     * where event listeners are made.
     *
     * @param savedInstanceState contains the previously saved state of the activitity if existing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_page);
        sharedPreferences = getSharedPreferences("survey_responses", MODE_PRIVATE);

        answerbox = findViewById(R.id.answerbox);
        answerbox2 = findViewById(R.id.answerbox2);
        Button buttonNext = findViewById(R.id.nextButton);
        // Set a click listener for the Next button
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndSavePdf();
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

        //set a click listener for the back Button
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the goBack method
                goBack();

            }
        });
    }

    /**
     * This method generates and saves a PDF with survey responses and displays the users responses
     * on a new blank text page.
     */
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

        String output = name + ccid + "_output35.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(EndPage.this, output,
                getSurveyAnswers(), questionTexts, mainQuestion);

        // Display a success message
        Toast.makeText(EndPage.this, "PDF generated and saved successfully!", Toast.LENGTH_SHORT).show();

        // Call the goTo method after generating and saving the PDF
        goTo();
    }

    /**
     *  This method gets the users informationc by using SharedPreferences.
     * @return An array containing the users information
     */
    private String[] getUserInfoFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Retrieve user information using keys
        String name = preferences.getString("Name", "");
        String ccid = preferences.getString("CCID", "");

        return new String[]{name, ccid};
    }

    /**
     * This method gets survey answers from EditTexts and returns them as a list.
     * @return A list containing survey answers
     */
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

    /**
     * This method sets the text color for all TextViews in the specified ViewGroup.
     * @param viewGroup The ViewGroyp containing TextViews
     * @param color     The color set for TextViews
     */
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

    /**
     * This method sends the user to the next page.
     */
    public void goTo() {
        Intent nextPage = new Intent(this, EndPageP2.class);
        nextPage.putExtra("userInfo", userInfo);
        nextPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(nextPage);

    }

    /**
     * This method sends the user to the previous page.
     */
    public void goBack() {
        Intent prevPage = new Intent(this, surveyPage21p2.class);
        prevPage.putExtra("userInfo", userInfo);
        prevPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(prevPage);

    }

    /**
     * This method opens the hint page.
     */
    private void openHint() {
        Intent Hint = new Intent(EndPage.this, Hint.class);
        startActivity(Hint);
    }

}