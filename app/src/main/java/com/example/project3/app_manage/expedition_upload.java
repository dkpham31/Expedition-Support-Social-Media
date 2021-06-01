package com.example.project3.app_manage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project3.Adapter.RecycleViewAdapter;
import com.example.project3.R;
import com.example.project3.loginandsignup.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class expedition_upload extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private static final String TAG = "expedition_upload";

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<Uri> mImageUrls = new ArrayList<>();
    private EditText Title, Introduction, Long, Length, Trailer, Link_video, Content;
    String Title1, Introduction1, Long1, Length1, Trailer1, Link_video1, Content1;
    private static final int PICK_IMAGE_REQUEST = 1, PICK_COVER_IMAGE =2;
    private String method;
    private TextView post;
    private Button mButtonChooseImage, AddTeam, image_cover;
    LinearLayoutManager layoutManager;
    RecycleViewAdapter adapter2;
    RecyclerView recyclerView;
    ImageView image_cover1;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition_upload);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Title = findViewById(R.id.expedition_title);
        Introduction = findViewById(R.id.expedition_first_intro);
        Long = findViewById(R.id.expedition_long);
        Length = findViewById(R.id.expedition_distance);
        Trailer = findViewById(R.id.expedition_trailer);
        Link_video = findViewById(R.id.expedition_youtubevideo);
        Content = findViewById(R.id.expedition_content);
        AddTeam = findViewById(R.id.add_team_member);
        image_cover = findViewById(R.id.cover_image_select);




        post = findViewById(R.id.expedition_post);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Image_Expedition_File");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.date, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mButtonChooseImage = findViewById(R.id.get_image);
        image_cover1 = findViewById(R.id.image_cover);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.image_list);
        recyclerView.setLayoutManager(layoutManager);
        adapter2 = new RecycleViewAdapter(this, mName, mImageUrls);
        recyclerView.setAdapter(adapter2);

        image_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser1();
            }
        });
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        spinner.setAdapter(adapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Push_to_firebase();
            }
        });

    }

    private void Push_to_firebase(){
        Title1 =  Title.getText().toString().trim();
        Introduction1 = Introduction.getText().toString().trim();
        Long1 = Long.getText().toString().trim();
        Length1 = Length.getText().toString().trim();
        Trailer1 =Trailer.getText().toString().trim();
        Link_video1= Link_video.getText().toString().trim();
        Content1 =Content.getText().toString().trim();
        final DatabaseReference expedition = databaseReference.child("Expedition").push();

        final String Expedition_id = expedition.getKey();
        final String image_a ="Expedition/" + Expedition_id + "/" + "Image";

        Map <String, Object> previous_expedition = new HashMap<>();
        previous_expedition.put("Title", Title1);
        previous_expedition.put("Introduction", Introduction1);
        previous_expedition.put("Long", Long1);
        previous_expedition.put("Method", method);
        previous_expedition.put("Length", Length1);
        previous_expedition.put("Trailer", Trailer1);
        previous_expedition.put("Link_youtube", Link_video1);
        previous_expedition.put("Content", Content1);
        expedition.setValue(previous_expedition).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        for ( int i = 0; i < mImageUrls.size(); i++){
            final  int a = i;
            final StorageReference filePath = storageReference.child(Expedition_id).child(mName.get(i));
            filePath.putFile(mImageUrls.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Task<Uri> firebaseUi = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUi.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {

                            final String downloadURL = uri.toString();
                            databaseReference.child("Expedition").child(Expedition_id).child("Image")
                                    .child("Image" + a).setValue(downloadURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
//
                        }
                    });

                    Toast.makeText(expedition_upload.this, mImageUrls.size() +"done", Toast.LENGTH_SHORT).show();
                }
            });
        }

        databaseReference.child("Expedition").child(Expedition_id).child("Team").child("Kate").setValue("my name").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(expedition_upload.this, mImageUrls.size() + "Done", Toast.LENGTH_SHORT).show();
            }
        });





    }

    /** open file gallery **/
    private void openFileChooser(){

        try {
            if (ActivityCompat.checkSelfPermission(expedition_upload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(expedition_upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openFileChooser1(){
        try {
            if (ActivityCompat.checkSelfPermission(expedition_upload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(expedition_upload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_COVER_IMAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){
            /**Push to the array list */
            if (data.getClipData()!= null){
                int totalItem = data.getClipData().getItemCount();
                for (int i =0; i < totalItem; i++){
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    mImageUrls.add(fileUri);
                    String fileName = getFileName(fileUri);
                    mName.add(fileName);
                    adapter2.notifyDataSetChanged();

                }
            }
            else if (data.getData() != null){
                Uri uri = data.getData();
                mImageUrls.add(uri);
                String fileName = getFileName(uri);
                mName.add(fileName);
                adapter2.notifyDataSetChanged();

            }

        } else if (requestCode == PICK_COVER_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
                Uri uri = data.getData();
            Picasso.get().load(uri).into(image_cover1);
        }
    }

    /** get the name of the file to save in documentName, the value could be image or pdf file*/
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
                Toast.makeText(parent.getContext(), "Days", Toast.LENGTH_SHORT).show();
                method = "Days";
                break;
            case 1:
                Toast.makeText(parent.getContext(),"Months", Toast.LENGTH_SHORT).show();
                method = "Months";
                break;
            case 2:
                Toast.makeText(parent.getContext(),"Years", Toast.LENGTH_SHORT).show();
                method = "Years";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}