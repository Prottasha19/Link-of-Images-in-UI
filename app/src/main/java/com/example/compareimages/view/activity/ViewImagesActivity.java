package com.example.compareimages.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.bumptech.glide.Glide;
import com.example.compareimages.R;
import com.example.compareimages.adapter.ImageAdapter;
import com.example.compareimages.model.Image;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Image> imageList;
    private ViewFlipper viewFlipper;

    private ProgressDialog progressBar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        setTitle("ImageView");

        initialize();

        getImagesFromDatabase();


    }

    private void getImagesFromDatabase() {

        Loading();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imageList.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Image image = dataSnapshot.getValue(Image.class);

                        imageList.add(image);
                        setSliderImageView(image.getImgLink());
                        imageAdapter.notifyDataSetChanged();

                        progressBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.dismiss();
                Toast.makeText(ViewImagesActivity.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSliderImageView(String image) {

        Log.d("tag","images"+image);

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(image).into(imageView);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.startFlipping();


        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

        for(int i = 0;i<imageList.size();i++){

            int finalI = i;

            viewFlipper.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageList.get(finalI).getImgLink()));
                    startActivity(browserIntent);
                }
            });
        }
    }

    private void Loading() {
        progressBar.setTitle("Updating");
        progressBar.setMessage("Wait image updating...");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
    }

    private void initialize() {

        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerView = findViewById(R.id.imageViewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageList,this);
        recyclerView.setAdapter(imageAdapter);

        progressBar = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Images");
    }
}