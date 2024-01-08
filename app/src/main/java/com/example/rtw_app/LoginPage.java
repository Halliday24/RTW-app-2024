package com.example.rtw_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    private EditText editTextEmail, editTextCCID, editTextName;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.LoginEmail);
        editTextCCID = findViewById(R.id.LoginCCID);
        editTextName = findViewById(R.id.LoginName);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * This checks if the the email and CCID typed is in the database. if it is then the
             * login is succesful and the user can move to the next page. otherwise it will display
             * an error message.
             * For now it just checks if the email is Admin and if the password is 123.
             * @param view the button this is assigned to
             */
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String email = editTextEmail.getText().toString();
                String ccid = editTextCCID.getText().toString();
                String name = editTextName.getText().toString();

                // Implement authentication logic here (will change when we get confirmation on if we can use servers or not)
                if (email.equals("Admin") && ccid.equals("123")) {
                    // Successful login
                    Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // goToInstructionPage();
                } else {
                    // Failed login
                    Toast.makeText(LoginPage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * This function sends the user to the Instructionpage from the LoginPage
     */
//    public void goToInstructionPage(){
//        Intent instructionPage = new Intent(this, InstructionPage.class);
//        startActivity(instructionPage);
//
//    }
}