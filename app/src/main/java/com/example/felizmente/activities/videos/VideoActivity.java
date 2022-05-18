package com.example.felizmente.activities.videos;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.example.felizmente.activities.MainActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getSupportActionBar().hide();
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

    public void goBackToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}