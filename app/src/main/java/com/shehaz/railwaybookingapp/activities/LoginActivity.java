package com.shehaz.railwaybookingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.shehaz.railwaybookingapp.R;

public class LoginActivity extends AppCompatActivity
{

    EditText nameEditText,emailEditText,passwordEditText;
    Button loginButton;
    AwesomeValidation awesomeValidation;
    SharedPreferences  preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        preferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);
        editor = preferences.edit();
        //For login validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.password, "[A-Za-z0-9]{4,}",R.string.invalid_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    String name = nameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    editor.putString("Name", name);
                    editor.putString("Email", email);
                    editor.putString("Password", password);
                    editor.putString("Logged", "logged");
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Successfull Login", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, BookingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);
        editor = preferences.edit();
        //checking if user already logged in
        if (preferences.getString("Logged", "").toString().equals("logged"))
        {
            //if yes, then opens booking activity
            Intent intent = new Intent(LoginActivity.this, BookingActivity.class);
            startActivity(intent);
        }

    }
}





