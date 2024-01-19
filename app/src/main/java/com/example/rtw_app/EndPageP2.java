package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EndPageP2 extends AppCompatActivity {
    private CheckBox javaCheckBox;
    private CheckBox kotlinCheckBox;
    private CheckBox swiftCheckBox;
    private CheckBox CheckBox;
    private CheckBox CheckBox1;
    private CheckBox CheckBox2;
    private CheckBox CheckBox3;
    private CheckBox CheckBox4;
    private CheckBox CheckBox5;
    private CheckBox CheckBox6;
    private CheckBox CheckBox7;
    private CheckBox CheckBox8;
    private CheckBox CheckBox9;
    private String userInfo;
    private Button hint;
    private Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfo = getIntent().getStringExtra("userInfo");
        setContentView(R.layout.activity_end_page_p2);
        javaCheckBox = findViewById(R.id.javaCheckbox);
        kotlinCheckBox = findViewById(R.id.kotlinCheckbox);
        swiftCheckBox = findViewById(R.id.swiftCheckbox);
        CheckBox = findViewById(R.id.checkBox);
        CheckBox1 = findViewById(R.id.checkBox2);
        CheckBox2 = findViewById(R.id.checkBox3);
        CheckBox3 = findViewById(R.id.checkBox4);
        CheckBox4 = findViewById(R.id.checkBox5);
        CheckBox5 = findViewById(R.id.checkBox6);
        CheckBox6 = findViewById(R.id.checkBox7);
        CheckBox7 = findViewById(R.id.checkBox8);
        CheckBox8 = findViewById(R.id.checkBox9);
        CheckBox9 = findViewById(R.id.checkBox10);
        nextButton = findViewById(R.id.submitButton);
        // Set a click listener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
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
    }
    private void generateAndSavePdf() {
        List<String[]> selectedCheckboxesList = getSelectedCheckboxes();

        // Add your question texts to the list here
        List<String> questionTexts = new ArrayList<>();
        questionTexts.add("Tutoring");
        questionTexts.add("Writing Centre");
        questionTexts.add("Counseling Services");
        questionTexts.add("Peer MentorShip");
        questionTexts.add("Student Experience Coordinator");
        questionTexts.add("Accessibility Resources");
        questionTexts.add("Financial Aid");
        questionTexts.add("Wellness Resources");
        questionTexts.add("Student Success Workshops");
        questionTexts.add("eClass tutorial");
        questionTexts.add("Academic Advising");
        questionTexts.add("Career Services");
        questionTexts.add("Campus Recreation");

        String output = userInfo + "_output36.pdf";

        // Call the PdfGenerator to generate PDF
        PdfGenerator.generatePdf(EndPageP2.this, output, selectedCheckboxesList, questionTexts, "Final Survey Questions");

        // Display a success message
        Toast.makeText(EndPageP2.this, "PDF generated and saved successfully!", Toast.LENGTH_SHORT).show();

        // Call the goTo method after generating and saving the PDF
        // After generating all individual PDFs
        PdfGenerator.combinePdfFiles(this, userInfo +"_output.pdf", 36);

    }

    private List<String[]> getSelectedCheckboxes() {
        List<String[]> selectedCheckboxesList = new ArrayList<>();

        // Create an array with the selected Checkboxes for each question and add it to the list
        String[] questionCheckboxes = {
                javaCheckBox.isChecked() ? javaCheckBox.getText().toString() : "",
                kotlinCheckBox.isChecked() ? kotlinCheckBox.getText().toString() : "",
                swiftCheckBox.isChecked() ? swiftCheckBox.getText().toString() : "",
                CheckBox.isChecked() ? CheckBox.getText().toString() : "",
                CheckBox1.isChecked() ? CheckBox1.getText().toString() : "",
                CheckBox2.isChecked() ? CheckBox2.getText().toString() : "",
                CheckBox3.isChecked() ? CheckBox3.getText().toString() : "",
                CheckBox4.isChecked() ? CheckBox4.getText().toString() : "",
                CheckBox5.isChecked() ? CheckBox5.getText().toString() : "",
                CheckBox6.isChecked() ? CheckBox6.getText().toString() : "",
                CheckBox7.isChecked() ? CheckBox7.getText().toString() : "",
                CheckBox8.isChecked() ? CheckBox8.getText().toString() : "",
                CheckBox9.isChecked() ? CheckBox9.getText().toString() : ""
        };
        selectedCheckboxesList.add(questionCheckboxes);


        return selectedCheckboxesList;
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
    private void done() {

    }
    private void openHint() {
        Intent Hint = new Intent(EndPageP2.this, Hint.class);
        startActivity(Hint);
    }

}