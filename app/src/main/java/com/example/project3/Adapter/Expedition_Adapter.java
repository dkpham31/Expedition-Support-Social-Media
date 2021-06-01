package com.example.project3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project3.Model.previous_expeditions;
import com.example.project3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Expedition_Adapter extends RecyclerView.Adapter<Expedition_Adapter.ImageViewHolder> {

    private Context mContext;
    private List<previous_expeditions> mData;
    private DatabaseReference databaseReference;
    boolean isImageFitToScreen;

    public Expedition_Adapter(Context mContext, List<previous_expeditions> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.expedition_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        previous_expeditions previous_expeditions = mData.get(position);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expedition");
        holder.name.setText(mData.get(position).getTitle());
        holder.length.setText(mData.get(position).getLength());
        holder.duration.setText(mData.get(position).getLong());
        Picasso.get().load(mData.get(position).getImageCover()).into(holder.ImageCover);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView ImageCover;
        TextView length;
        TextView duration;



        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (itemView).findViewById(R.id.expedition_name);
            ImageCover = (itemView).findViewById(R.id.expedition_image);
            length = (itemView).findViewById(R.id.expedition_length);
            duration = (itemView).findViewById(R.id.expedition_duration);

            ImageCover.setAdjustViewBounds(true);
            ImageCover.setScaleType(ImageView.ScaleType.CENTER_CROP);


        }
    }
}
