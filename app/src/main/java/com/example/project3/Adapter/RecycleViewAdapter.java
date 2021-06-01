package com.example.project3.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>  {

    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<String> mName;
    private ArrayList<Uri> mImageUrl;
    private Context mContent;




    public RecycleViewAdapter(Context mContent, ArrayList<String> mName, ArrayList<Uri> mImageUrl) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;
        this.mContent = mContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.title_image.setText((mName.get(position)));
        Picasso.get().load(mImageUrl.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image" + mName);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView title_image;

        public ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image_upload);
            title_image = view.findViewById(R.id.title_image);
        }
    }
}
