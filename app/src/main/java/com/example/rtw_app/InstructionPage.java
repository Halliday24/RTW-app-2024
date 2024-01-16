package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class InstructionPage extends AppCompatActivity {

    private Button buttonNext;
    private Button buttonBack;
    int Counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_page);
        TextView textView = (TextView) findViewById(R.id.Instructions);
        textView.setText("This self-guided workbook is designed to help you to identify, and reflect on, the factors" +
                " that lead to being required to withdraw. The workbook will also help you " +
                " to identify the" +
                " strengths you possess, and available resources that will help you succeed " +
                " in the future." +
                " Once you’ve identified these pieces, you’ll be ready to write a strong appeal " +
                " letter."); //set text for text view

        buttonNext=findViewById(R.id.buttonNext);

        //set a click listener for the next Button
        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(Counter<1){
                    TextView textView = (TextView) findViewById(R.id.Instructions);
                    textView.setText("We encourage you to take your time with this exercise." +
                            " Think through each question" +
                            " and be honest with yourself about what areas you struggled with and" +
                            " where you can" +
                            " find areas of improvement. You should expect to spend 1-2 hours " +
                            " completing this" +
                            " workbook."); //set text for text view
                    Counter++;
                }
                else if(Counter<2){
                    TextView textView = (TextView) findViewById(R.id.Instructions);
                    textView.setText("To the extent that you are comfortable, it is strongly " +
                            " recommended that you submit a" +
                            " copy of your completed workbook with your appeal letter." +
                            " Taking the time to complete" +
                            " the exercise demonstrates a commitment to growth and implementing" +
                            " changes. "); //set text for text view
                    Counter++;
                }
                else{
                    goToimpactAcademicPage();
                }
            }
        });

        buttonBack=findViewById(R.id.buttonBack);

        //set a click listener for the next Button
        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(Counter==1 && Counter>0){
                    TextView textView = (TextView) findViewById(R.id.Instructions);
                    textView.setText("This self-guided workbook is designed to help you to identify, and reflect on, the factors" +
                            " that lead to being required to withdraw. The workbook will also help you " +
                            " to identify the" +
                            " strengths you possess, and available resources that will help you succeed " +
                            " in the future." +
                            " Once you’ve identified these pieces, you’ll be ready to write a strong appeal " +
                            " letter."); //set text for text view
                    Counter--;
                }

                else if(Counter==2){
                    TextView textView = (TextView) findViewById(R.id.Instructions);
                    textView.setText("We encourage you to take your time with this exercise." +
                            " Think through each question" +
                            " and be honest with yourself about what areas you struggled with and" +
                            " where you can" +
                            " find areas of improvement. You should expect to spend 1-2 hours " +
                            " completing this" +
                            " workbook."); //set text for text view
                    Counter--;
                }
                else{
                    goToLoginPage();
                }
            }
        });

        // Set text color for all TextViews in the layout
        setTextColorForAllTextViews((ViewGroup) findViewById(android.R.id.content), Color.BLACK);
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

    public void goToimpactAcademicPage(){
        Intent impactAcademicPage1 = new Intent(this, SurveyPage1p1.class);
        impactAcademicPage1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(impactAcademicPage1);

    }

    public void goToLoginPage(){
        Intent ImpactAcademicPage = new Intent(this, MainActivity.class);
        startActivity(ImpactAcademicPage);

    }
    }
