package com.example.felizmente.activities.videos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    public void goToHumor(View view) {
        Intent intent = new Intent(this, FunnyVideosActivity.class);
        startActivity(intent);
    }

    public void goToRelaxation(View view) {
        Intent intent = new Intent(this, RelaxActivity.class);
        startActivity(intent);
    }

    public void goToNostagicVideos(View view) {
        Intent intent = new Intent(this, NostalgicVideosActivity.class);
        startActivity(intent);
    }
}