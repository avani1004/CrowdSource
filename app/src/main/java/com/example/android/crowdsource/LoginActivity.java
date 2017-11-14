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

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;
    private TextView mSignUpLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mSignUpLink = (TextView) findViewById(R.id.link_signup);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               login();
            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
                // login();
            }
        });

}

public void login(){
    if (!validate()) {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);

        return;
    }

    String email = mEmail.getText().toString();
    String password = mPassword.getText().toString();

// firebase

}

public boolean validate(){
    boolean valid = true;

    String email = mEmail.getText().toString();
    String password = mPassword.getText().toString();

    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        mEmail.setError("Enter a valid email address");
        valid = false;
    } else {
        mEmail.setError(null);
    }

    if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
        mPassword.setError("Between 4 and 10 alphanumeric characters");
        valid = false;
    } else {
        mPassword.setError(null);
    }

    return valid;
}

}
