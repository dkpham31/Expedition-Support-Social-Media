package com.example.project3.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3.ModelAndCommon.User;
import com.example.project3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText username;
    EditText password;
    EditText mEmail;
    TextView back_login;
    Button Register;
    FirebaseAuth fAuth;
    String userID;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");

        //sign up
        username = findViewById(R.id.name_input);
        password = findViewById(R.id.passWord_sign_up);
        mEmail = findViewById(R.id.E_Mail_sign_up);
        back_login = findViewById(R.id.back_login);
        Register = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progressBar_sign_up);
        fAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String passwords = password.getText().toString().trim();
                final String usernames = username.getText().toString();

                if (TextUtils.isEmpty(usernames)){
                    username.setError("Please enter your name");
                    username.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passwords)){
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if (passwords.length() < 8){
                    password.setError("Password needs to be more than 8 characters");
                    password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {         //check if email pattern is correct
                    mEmail.setError("Please enter a valid email");
                    mEmail.requestFocus();
                    return;
                }
                else {
                    username.setEnabled(false);
                    mEmail.setEnabled(false);
                    password.setEnabled(false);
                    Register.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    final User user = new User(usernames, email);

                    fAuth.createUserWithEmailAndPassword(email, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                progressBar.setVisibility(View.GONE);

                                fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(SignUpActivity.this, "Register successful, Please check your email for verification !", Toast.LENGTH_SHORT).show();
                                            userID = fAuth.getCurrentUser().getUid();
                                            users.child(userID).setValue(user);
                                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                            finish();

                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                username.setEnabled(true);
                                mEmail.setEnabled(true);
                                password.setEnabled(true);
                                Register.setEnabled(true);
                            }
                        }
                    });
                }


            }
        });
    };

    public void BackLogin(View view) {
        startActivity(new Intent(SignUpActivity.this,Login_Register.class));
        finish();
    }
}