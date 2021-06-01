package com.example.project3.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.project3.Donation;
import com.example.project3.HomePage;
import com.example.project3.R;
import com.example.project3.UserProfile;
import com.example.project3.loginandsignup.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class Fragment_Setting extends Fragment {
    ImageView fb;
    ImageView ins;
    ImageView tw;
    ImageView partner1;
    ImageView partner2;
    ImageView partner3;
    Button signOut, donateUs;
    String urlfb = "https://www.facebook.com/kate.m.leeming";
    String urlins = "https://www.instagram.com/leeming_kate/";
    String urltw = "https://twitter.com/leeming_kate";
    String urlpartner1 = "https://dukeofed.com.au/";
    String urlpartner2 = "https://belouga.org/channel/breaking-the-cycle-education";
    String urlpartner3 = "https://footprintsontheglobe.com.au/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__setting, container, false);
        donateUs = v.findViewById(R.id.DonationButton);
        donateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Donation.class));
            }
        });
        signOut = v.findViewById(R.id.SignOutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fb = (ImageView) getActivity().findViewById(R.id.FacebookButton);
        ins = (ImageView) getActivity().findViewById(R.id.InstagramButton);
        tw = (ImageView) getActivity().findViewById(R.id.TwitterButton);
        partner1 = (ImageView) getActivity().findViewById(R.id.Partner1);
        partner2 = (ImageView) getActivity().findViewById(R.id.Partner2);
        partner3 = (ImageView) getActivity().findViewById(R.id.Partner3);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlfb));
                getActivity().startActivity(i);

            }
        });
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urltw));
                getActivity().startActivity(i);

            }
        });
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlins));
                getActivity().startActivity(i);

            }
        });
        partner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlpartner3));
                getActivity().startActivity(i);

            }
        });
        partner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlpartner1));
                getActivity().startActivity(i);

            }
        });
        partner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlpartner2));
                getActivity().startActivity(i);

            }
        });

    }
}