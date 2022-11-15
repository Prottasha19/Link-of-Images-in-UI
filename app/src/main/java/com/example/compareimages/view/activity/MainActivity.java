package com.example.compareimages.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.compareimages.R;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private Button viewImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                lottieAnimationView.setVisibility(View.INVISIBLE);
                viewImageButton.setVisibility(View.VISIBLE);

            }
        }, 2000);

        viewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, ViewImagesActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    private void initialize() {

        lottieAnimationView = findViewById(R.id.animationView);
        viewImageButton = findViewById(R.id.viewImagesBTN);
    }
}