package com.example.felizmente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.activities.music.MusicActivity;
import com.example.felizmente.activities.music.MusicNostalgicActivity;
import com.example.felizmente.R;
import com.example.felizmente.activities.quiz.PhotosActivity;
import com.example.felizmente.activities.videos.VideoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void goToMusic(View view){
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    public void goToVideos(View view){
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void goToPhotos(View view){
        Intent intent = new Intent(this, PhotosActivity.class);
        startActivity(intent);
    }
}