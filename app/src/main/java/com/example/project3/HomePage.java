package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project3.fragment.Fragment_Blogs;
import com.example.project3.fragment.Fragment_Gallery;
import com.example.project3.fragment.Fragment_Setting;
import com.example.project3.fragment.Fragment_donation;
import com.example.project3.fragment.Fragment_expedition;
import com.example.project3.fragment.Fragment_homepage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity  {

    FrameLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        fragment = findViewById(R.id.frameLayout2);
        Fragment selectedFragement = new Fragment_homepage();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout2, selectedFragement)
                .commit();

        bottomNavigationView.setSelectedItemId(R.id.homePage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragement = new Fragment_homepage();
                switch (item.getItemId()){
                    case R.id.homePage:
                        selectedFragement = new Fragment_homepage();
                        break;
                    case R.id.Blogs:
                        selectedFragement = new Fragment_Blogs();
                        break;
                    case R.id.Gallery:
                        selectedFragement = new Fragment_Gallery();
                        break;
                    case R.id.Setting:
                        selectedFragement = new Fragment_Setting();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout2,selectedFragement).commit();
                return true;
            }
        });
    }


    public  void user_profile (View view){
        startActivity(new Intent(getApplicationContext(), UserProfile.class));

    }

}
