package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


/**
 * Created by avaniarora on 11/12/17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;
    private TextView mSignUpLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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

        mAuth = FirebaseAuth.getInstance();

        // [START auth_state_listener] ,this method execute as soon as there is a change in Auth status , such as user sign in or sign out.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    //redirect
                    //updateUI(user);

                } else {
                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    updateUI(null);
                }

            }
        };
        // [END auth_state_listener]


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), user.getEmail().toString() + " logged in",
                    Toast.LENGTH_SHORT).show();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            //User is signed out
            //Log.d(TAG, "onAuthStateChanged:signed_out");
        }





    }

public void login(){
    if (!validate()) {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mLoginButton.setEnabled(true);

        return;
    }

    String email = mEmail.getText().toString();
    String password = mPassword.getText().toString();

    // TODO: read all values from firebase and see if login is valid

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());



                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        //Log.w(TAG, "signInWithEmail:failed", task.getException());
                        Toast.makeText(getApplicationContext(), "Login Unsuccessful, invalid credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Login Successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }


                }
            });





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
