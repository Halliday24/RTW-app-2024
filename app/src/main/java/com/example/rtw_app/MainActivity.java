package com.example.rtw_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rtw_app.databinding.ActivityMainBinding;
import android.Manifest;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PERMISSION_WRITE_EXTERNALLY = Manifest.permission.MANAGE_EXTERNAL_STORAGE;
    private static final int PERMISSION_REQ_CODE_WRITE = 100;
    private static final String PERMISSION_READ_EXTERNALLY = Manifest.permission.READ_EXTERNAL_STORAGE;

    private static final int PERMISSION_REQ_CODE_READ = 200;
    private EditText editTextEmail, editTextCCID, editTextName;
    private Button buttonLogin;

    private String userInfo;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.LoginEmail);
        editTextCCID = findViewById(R.id.LoginCCID);
        editTextName = findViewById(R.id.LoginName);
        buttonLogin = findViewById(R.id.buttonLogin);
        hint = findViewById(R.id.hint);


        //Set an onClick listener for using the hint button
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHint();
            }
        });
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
                performLogin();
            }
        });


    }

    private void performLogin(){
        // Retrieve entered username and password
        String email = editTextEmail.getText().toString();
        String ccid = editTextCCID.getText().toString();
        String name = editTextName.getText().toString();
        userInfo = name + ccid;

        // Implement authentication logic here (will change when we get confirmation on if we can use servers or not)
        if ((ccid.length() < 8 && (name.length() > 0))) {
            // Successful login
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            goToInstructionPage();
        } else {
            // Failed login
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This function sends the user to the Instructionpage from the LoginPage
     */
   public void goToInstructionPage(){
        Intent instructionPage = new Intent(this, InstructionPage.class);
        instructionPage.putExtra("userInfo", userInfo);
        startActivity(instructionPage);

    }

    //this method is responsible for giving a hint to students to remind them about why they are
    //filling in this workbook
    private void openHint() {
       Intent Hint = new Intent(MainActivity.this, Hint.class);
       startActivity(Hint);
    }

}
