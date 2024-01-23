package com.example.rtw_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private String userName = "";
    private static final String PERMISSION_READ_EXTERNALLY = Manifest.permission.READ_EXTERNAL_STORAGE;

    private static final int PERMISSION_REQ_CODE_READ = 200;
    private EditText editTextEmail, editTextCCID, editTextName;
    private static final String KEY_CURRENT_QUESTION = "current_question";
    private Button buttonLogin;
    private int currentQuestion = 0;

    private int totalQuestions = 5; // Set the total number of questions

    private SharedPreferences sharedPreferences;

    private String userInfo;
    private Button hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize your SharedPreferences
        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);


        // Initialize UI elements
        editTextEmail = findViewById(R.id.LoginEmail);
        editTextCCID = findViewById(R.id.LoginCCID);
        editTextName = findViewById(R.id.LoginName);
        buttonLogin = findViewById(R.id.buttonLogin);
        hint = findViewById(R.id.hint);
        // Check and request storage permissions if needed
        if (!checkStoragePermissions()) {
            requestStoragePermissions();
        }


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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //storing the increased current Question
                editor.putInt(KEY_CURRENT_QUESTION, currentQuestion);
                editor.putInt("Total_questions", totalQuestions);
                editor.apply();
                performLogin();

            }
        });


    }


    private void saveUserInfoToSharedPreferences(String name, String ccid) {
        // Use SharedPreferences to save user information
        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Save user information with keys
        editor.putString("Name", name);
        editor.putString("CCID", ccid);

        // Commit the changes
        editor.apply();
    }

    private void performLogin(){
        // Retrieve entered username and password
        String email = editTextEmail.getText().toString();
        String studentID = editTextCCID.getText().toString();
        userName = editTextName.getText().toString();

        sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName",userName);
        editor.apply();

        saveUserInfoToSharedPreferences(userName,studentID);

        // Implement authentication logic here (will change when we get confirmation on if we can use servers or not)
        if ((studentID.length() ==7 && (userName.length() > 0))) {
            // Successful login
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            goToInstructionPage();
        } else {
            // Failed login
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android is 11 (R) or above
            return Environment.isExternalStorageManager();
        } else {
            // Below android 11
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Request MANAGE_EXTERNAL_STORAGE permission for Android 11 and above
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Request WRITE_EXTERNAL_STORAGE and READ_EXTERNAL_STORAGE permissions for below Android 11
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQ_CODE_WRITE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE_WRITE) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform the desired action
                Toast.makeText(this, "Storage permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Storage permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
        // Handle other permission requests if needed

        // ... (existing code)
    }


    /**
     * This function sends the user to the Instructionpage from the LoginPage
     */
   public void goToInstructionPage(){
        Intent instructionPage = new Intent(this, InstructionPage.class);
        instructionPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
