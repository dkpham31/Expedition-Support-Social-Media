package com.example.project3.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.project3.R;

public class Login_Register extends AppCompatActivity {

    ViewFlipper flipperImage;
    Button signin, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);

        int images [] = {R.drawable.image_login3, R.drawable.image_login2, R.drawable.image_login};

        flipperImage = findViewById(R.id.image_logo);

        signin = findViewById(R.id.SignIn);
        register = findViewById(R.id.register);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Register.this, MainActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Register.this, SignUpActivity.class));
            }
        });
        for (int image: images){
            flipperImages(image);
        }

    }

    public void flipperImages (int image){
        ImageView imageView = new ImageView(this);

        imageView.setBackgroundResource(image);
        flipperImage.addView(imageView);
        flipperImage.setFlipInterval(6000);
        flipperImage.setAutoStart(true);

        flipperImage.setInAnimation(this, android.R.anim.slide_in_left);
        flipperImage.setOutAnimation(this, android.R.anim.slide_out_right);
    }
}