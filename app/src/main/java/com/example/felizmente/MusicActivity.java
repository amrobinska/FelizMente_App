package com.example.felizmente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
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