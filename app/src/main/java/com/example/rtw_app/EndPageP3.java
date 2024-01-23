package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


public class EndPageP3 extends AppCompatActivity {

    private ImageButton home;
    private SharedPreferences sharedPreferences;
    private int currentQuestion;

    private int totalQuestions = 35; // Set the total number of questions

    private static final String KEY_CURRENT_QUESTION = "current_question";

    private ProgressBar progressBar;
    private TextView progressText;

    /**
     * Called when the activity is first created. This is where the UI is initialized along with
     * where event listeners are made.
     *
     * @param savedInstanceState contains the previously saved state of the activitity if existing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_page3);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        currentQuestion = sharedPreferences.getInt(KEY_CURRENT_QUESTION,currentQuestion);
        updateProgress();

        //home page initialization
        home = findViewById(R.id.home);
        //Set an onClick listener for using the home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });
    }

    /**
     * This method is used to direct the user to the home page, our login page.
     * Once called, it will send the user back to the main activity
     */
    private void goHome() {
        Intent home = new Intent(EndPageP3.this, MainActivity.class);
        startActivity(home);
    }

    private void updateProgress() {
        int progress = (currentQuestion * 100) / totalQuestions;
        progressBar.setProgress(progress);
        progressText.setText(getString(R.string.progress_text, currentQuestion, totalQuestions, progress));
    }
}