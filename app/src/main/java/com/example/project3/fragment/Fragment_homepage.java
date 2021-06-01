package com.example.project3.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3.Adapter.Expedition_Adapter;
import com.example.project3.LiveMap;
import com.example.project3.Location_Tracking.Admin_map;
import com.example.project3.R;
import com.example.project3.app_manage.expedition_upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.example.project3.Model.previous_expeditions;

import java.util.ArrayList;
import java.util.List;


public class Fragment_homepage extends Fragment {
    ImageView imageView;
    boolean isImageFitToScreen;
    ImageView liveExpedition;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference first = databaseReference.child("image");
    private DatabaseReference expedition = databaseReference.child("Expedition");
   private List<previous_expeditions> List;
   Expedition_Adapter expedition_adapter;
    private  RecyclerView recyclerView;

    TextView News;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        News = (view).findViewById(R.id.News);
        imageView = (view).findViewById(R.id.live_image);
        imageView.setImageResource(R.drawable.live_expedition);
        recyclerView = (view).findViewById(R.id.expedition_list_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        List = new ArrayList<>();


        expedition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){

                    previous_expeditions previous_expeditions = dataSnapshot1.getValue(com.example.project3.Model.previous_expeditions.class);

                    List.add(previous_expeditions);

                }

                expedition_adapter = new Expedition_Adapter(getContext(), List);
                recyclerView.setAdapter(expedition_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/project3-a36fb.appspot.com/o/live_expedition.jpg?alt=media&token=e2feca32-274a-4aae-99cf-2ae2c1b1e205").into(imageView);
        imageView.setAdjustViewBounds(true);
        if(isImageFitToScreen) {
            isImageFitToScreen=false;
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
        }else{
            isImageFitToScreen=true;
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), expedition_upload.class);
                startActivity(intent);
            }
        });

//        live_expedition.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String link = dataSnapshot.getValue(String.class);
//                Picasso.get().load(link).into(liveExpedition);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        return view;

    }




}
