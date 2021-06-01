package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Contact extends AppCompatActivity {
    ImageView fb;
    ImageView ins;
    ImageView tw;
    String urlfb = "https://www.facebook.com/kate.m.leeming";
    String urlins = "https://www.instagram.com/leeming_kate/";
    String urltw = "https://twitter.com/leeming_kate";

    EditText userName, userEmail, userMessage;
    Button Sendbutton;
    FirebaseFirestore fStore;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contact Us");
            super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        fb = (ImageView) findViewById(R.id.FacebookButton);
        ins = (ImageView) findViewById(R.id.InstagramButton);
        tw = (ImageView) findViewById(R.id.TwitterButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/kate.m.leeming");
            }
        });
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/leeming_kate/");
            }
        });
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://twitter.com/leeming_kate");
            }
        });




        userName = findViewById(R.id.namedata);
        userEmail = findViewById(R.id.emaildata);
        userMessage = findViewById(R.id.messagedata);
        Sendbutton = findViewById(R.id.btsend);

        fStore = FirebaseFirestore.getInstance();

        pd = new ProgressDialog(this);

        Sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String message = userMessage.getText().toString().trim();

                pd.setTitle("We are receiving your feedback. Thank You!");

                pd.show();

                String id = UUID.randomUUID().toString();

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", id);
                userMap.put("Name", name);
                userMap.put("Email", email);
                userMap.put("Message", message);

                fStore.collection("feedback").document(id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(Contact.this, "Uploaded ...", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Contact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}