package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by avaniarora on 11/12/17.
 */

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mName;
    private EditText mAddress;
    private EditText mEmail;
    private EditText mMobile;
    private EditText mPassword;
    private EditText mReEnterPassword;
    private Button mSignUp;
    private TextView mLoginLink;
    String name;
    String address;
    String email;
    String mobile;
    String password;
    String reEnterPassword;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


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

        //FIREBASE :

        mAuth = FirebaseAuth.getInstance();

        // [START auth_state_listener] ,this method execute as soon as there is a change in Auth status , such as user sign in or sign out.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in


                } else {
                    // User is signed out

                }

            }
        };
        // [END auth_state_listener]




    }

    public void signup() {
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            mSignUp.setEnabled(true);
            return;
        }

        //mSignUp.setEnabled(false);
        name = mName.getText().toString();
        address = mAddress.getText().toString();
        email = mEmail.getText().toString().trim();
        mobile = mMobile.getText().toString();
        password = mPassword.getText().toString().trim();
        reEnterPassword = mReEnterPassword.getText().toString();



        //TODO: write email and password for authentication to firebase


        //Toast.makeText(getApplicationContext(), "Here's a toast "+ email,Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(getApplicationContext(), "Authentication successful, account created.",Toast.LENGTH_SHORT).show();
                            write_user_information();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void write_user_information(){
        //TODO: write user information to realtime database

        FirebaseDatabase user_database = FirebaseDatabase.getInstance();
        DatabaseReference user_Ref = user_database.getReference("user information");

        Map<String, String> dump_structure = new HashMap<String, String>();
        dump_structure.put("user_name", name);
        dump_structure.put("user_address", address);
        dump_structure.put("user_email", email);
        dump_structure.put("user_mobile_number", mobile);

        user_Ref.push().setValue(dump_structure);
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

