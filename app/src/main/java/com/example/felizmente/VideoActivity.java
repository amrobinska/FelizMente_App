package com.example.felizmente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    public void goToHumor(View view) {
        Intent intent = new Intent(this, MusicNostalgicActivity.class);
        startActivity(intent);
    }

    public void goToRelaxation(View view) {
        Intent intent = new Intent(this, MusicNostalgicActivity.class);
        startActivity(intent);
    }

    public void goToNostagicVideos(View view) {
        Intent intent = new Intent(this, MusicNostalgicActivity.class);
        startActivity(intent);
    }
}