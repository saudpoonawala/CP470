
package com.example.androidassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String PREF_NAME = "LoginPreferences";
    private static final String EMAIL_KEY = "DefaultEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "inside onCreate");

        setContentView(R.layout.activity_login);

        EditText emailField = findViewById(R.id.loginName);
        EditText passwordField = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);

        SharedPreferences preferences =
                getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        String savedEmail =
                preferences.getString(EMAIL_KEY, "email@domain.com");

        emailField.setText(savedEmail);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString();

            if (email.isEmpty() ||
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError(getString(R.string.invalid_email));
                emailField.requestFocus();
                return;
            }

            if (password.trim().isEmpty()) {
                passwordField.setError(getString(R.string.empty_password));
                passwordField.requestFocus();
                return;
            }

            preferences.edit()
                    .putString(EMAIL_KEY, email)
                    .apply();

            Intent intent =
                    new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "inside onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "inside onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "inside onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "inside onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "inside onDestroy");
    }
}
