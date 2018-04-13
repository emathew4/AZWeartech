package com.azweartech.azweartech;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /** Set OnClickListener for Create Account Button */
        TextView createAccount = (TextView) findViewById(R.id.create_account_text_view);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CreateAccountIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(CreateAccountIntent);
            }
        });

        /** Set OnClickListener for Forgot Password Button */
        TextView forgotPassword = (TextView) findViewById(R.id.forgot_password_text_view);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ForgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(ForgotPasswordIntent);
            }
        });
    }

    /** Try to login using login information when login button is pressed */
    public void attemptLogin(View view){

    }
}
