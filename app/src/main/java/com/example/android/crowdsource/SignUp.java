package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by avaniarora on 11/12/17.
 */

public class SignUp extends AppCompatActivity {

    private EditText mName;
    private EditText mAddress;
    private EditText mEmail;
    private EditText mMobile;
    private EditText mPassword;
    private EditText mReEnterPassword;
    private Button mSignUp;
    private TextView mLoginLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        mName = (EditText) findViewById(R.id.input_name);
        mAddress = (EditText) findViewById(R.id.input_address);
        mEmail = (EditText) findViewById(R.id.input_email);
        mMobile = (EditText) findViewById(R.id.input_mobile);
        mPassword = (EditText) findViewById(R.id.input_password);
        mReEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
        mSignUp = (Button) findViewById(R.id.btn_signup);
        mLoginLink = (TextView) findViewById(R.id.link_login);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void signup() {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            mSignUp.setEnabled(true);
            return;
        }

        //mSignUp.setEnabled(false);
        String name = mName.getText().toString();
        String address = mAddress.getText().toString();
        String email = mEmail.getText().toString();
        String mobile = mMobile.getText().toString();
        String password = mPassword.getText().toString();
        String reEnterPassword = mReEnterPassword.getText().toString();

        //firebase
    }

    public boolean validate() {
            boolean valid = true;

            String name = mName.getText().toString();
            String address = mAddress.getText().toString();
            String email = mEmail.getText().toString();
            String mobile = mMobile.getText().toString();
            String password = mPassword.getText().toString();
            String reEnterPassword = mReEnterPassword.getText().toString();

            if (name.isEmpty() || name.length() < 3) {
                mName.setError("at least 3 characters");
                valid = false;
            } else {
                mName.setError(null);
            }

            if (address.isEmpty()) {
                mAddress.setError("Enter Valid Address");
                valid = false;
            } else {
                mAddress.setError(null);
            }


            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmail.setError("enter a valid email address");
                valid = false;
            } else {
                mEmail.setError(null);
            }

            if (mobile.isEmpty() || mobile.length()!=10) {
                mMobile.setError("Enter Valid Mobile Number");
                valid = false;
            } else {
                mMobile.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                mPassword.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                mPassword.setError(null);
            }

            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
                mReEnterPassword.setError("Password Do not match");
                valid = false;
            } else {
                mReEnterPassword.setError(null);
            }

            return valid;
        }

    }

