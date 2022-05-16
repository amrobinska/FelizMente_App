package com.example.felizmente.activities.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        getSupportActionBar().hide();
    }

    public void goToNostalgicMusic(View view) {
        Intent intent = new Intent(this, MusicNostalgicActivity.class);
        startActivity(intent);
    }

    public void goToDancingMusic(View view) {
        Intent intent = new Intent(this, MusicNostalgicActivity.class);
        startActivity(intent);
    }
}