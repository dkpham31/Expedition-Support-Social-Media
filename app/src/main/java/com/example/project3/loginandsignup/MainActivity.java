package com.example.project3.loginandsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3.HomePage;
import com.example.project3.R;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText mEmail;
    EditText password;
    MaterialEditText forget_pass_info;
    TextView SignUp;
    TextView Forget;
    private Button login;
    com.facebook.login.widget.LoginButton fb_login;
    SharedPreferences sharedPreferences;
    CheckBox checkBox;
    ProgressBar progressBar, progressBar_forget;
    SharedPreferences.Editor editor;
    FirebaseAuth fAuth;
    DatabaseReference users;
    FirebaseDatabase firebaseDatabase;
    String userID;

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1001;
    private static final String EMAIL = "email";

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference().child("Users");
        mEmail = findViewById(R.id.E_Mail);
        password = findViewById(R.id.passWord);
        login = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkBox2);
        Forget = findViewById(R.id.Forget);
        progressBar = findViewById(R.id.progressbar_login);
        mAuth = FirebaseAuth.getInstance();
        fb_login = (LoginButton) findViewById(R.id.Login_with_FB);

        sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkPreferences();

        fAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();



        fb_login = (LoginButton) findViewById(R.id.Login_with_FB);
        fb_login.setPermissions(Arrays.asList(EMAIL));

        fb_login.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String passwords = password.getText().toString().trim();

                if (checkBox.isChecked()) {
                    editor.putString("email",mEmail.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.commit();
                }
                else {
                    editor.putString("email","");
                    editor.putString("password","");
                    editor.commit();
                }
                editor.putBoolean("checkbox",checkBox.isChecked());
                editor.commit();

                if (TextUtils.isEmpty(email)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwords)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    password.setError("Password is required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {         //check if email pattern is correct
                    progressBar.setVisibility(View.INVISIBLE);
                    mEmail.setError("Please enter a valid email");
                    mEmail.requestFocus();
                    return;
                }

                mEmail.setEnabled(false);
                password.setEnabled(false);
                login.setEnabled(false);
                checkBox.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            if(fAuth.getCurrentUser().isEmailVerified()){

                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                usersRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
//                                            String name = dataSnapshot.child("name").getValue().toString();
//                                            if (name.equals("viet")) {
//                                                startActivity(new Intent(getApplicationContext(), Contact.class));
//                                            }
//                                            else {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            startActivity(new Intent(MainActivity.this, HomePage.class));
//                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {
                                progressBar.setVisibility(View.INVISIBLE);
                                mEmail.setEnabled(true);
                                password.setEnabled(true);
                                login.setEnabled(true);
                                checkBox.setEnabled(true);
                                Toast.makeText(MainActivity.this, "Please verify your email address " , Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            mEmail.setEnabled(true);
                            password.setEnabled(true);
                            login.setEnabled(true);
                            checkBox.setEnabled(true);
                            Toast.makeText(MainActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //@Override
    //public void onStart() {
    //    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    //    FirebaseUser currentUser = mAuth.getCurrentUser();
    //    if (currentUser != null){
    //    if (currentUser.isEmailVerified()) {
    //        startActivity(new Intent(MainActivity.this, HomePage.class));
    //    }}
    //}

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void checkPreferences() {
        String mail = sharedPreferences.getString("email","");
        String pass = sharedPreferences.getString("password","");
        mEmail.setText(mail);
        password.setText(pass);
        boolean valueChecked = sharedPreferences.getBoolean("checkbox",false);
        checkBox.setChecked(valueChecked);
    }

    public void ForgetPassword(View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);     //Open Alert Dialog


        LayoutInflater inflater = this.getLayoutInflater();
        final View forget_pass_layout = inflater.inflate(R.layout.forget_pass_layout,null);       //get forget_password_layout

        forget_pass_info = (MaterialEditText)forget_pass_layout.findViewById(R.id.forget_pass_info);
        progressBar_forget = forget_pass_layout.findViewById(R.id.progressbar_forget);


        alertDialog.setView(forget_pass_layout);
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {        //Negative Button to dismiss dilog
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {     //Positive Button for Submit
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog dialog = alertDialog.create();            //open dialog for set up Positive button function
        dialog.show();

        final Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundResource(R.drawable.custom_loginfb);


        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {       //setting up positive button

                final String info = forget_pass_info.getText().toString().trim();

                if (info.isEmpty()) {                                                   //check if edit text is empty
                    forget_pass_info.setError("Email required");
                    forget_pass_info.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(info).matches()) {         //check if email pattern is correct
                    forget_pass_info.setError("Please enter a valid email");
                    forget_pass_info.requestFocus();
                    return;
                }
                else
                {   forget_pass_info.setEnabled(false);     //set edit text disable
                    progressBar_forget.setVisibility(View.VISIBLE);
                    pbutton.setEnabled(false);
                    users.orderByChild("email").equalTo(info)
                            .addListenerForSingleValueEvent(new ValueEventListener() {             //add Listener for "users" in database

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        fAuth.sendPasswordResetEmail(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {          // open retrieving email for password reset function
                                                progressBar_forget.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this, "Check your email for Password Reset", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();

                                                }
                                            }

                                        });
                                    } else {
                                        progressBar_forget.setVisibility(View.GONE);
                                        pbutton.setEnabled(true);
                                        forget_pass_info.setEnabled(true);  //set edit text enable again
                                        //if the input is not email that stored on database then notice to user
                                        Toast.makeText(MainActivity.this, "Email does not register or not being activated", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }
        });
    }
}

