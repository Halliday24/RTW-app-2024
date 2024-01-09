package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionPage extends AppCompatActivity {

    private Button buttonNext;
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
                    goToSurveyPage();
                }
            }
        });

        }

    public void goToSurveyPage(){
        Intent ImpactPage = new Intent(this, impactPage.class);
        startActivity(ImpactPage);

    }
    }
