package com.example.felizmente.activities.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;

public class QuizzTypesActivity extends AppCompatActivity {

    public String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_types);
        getSupportActionBar().hide();
    }

    public void startFamousPeopleGame(View view){
        gameType = "pesonajes conocidos";
        startActivity(new Intent(this, PhotosActivity.class).putExtra("Tipo de juego", gameType));
    }

    public void startSpanishCultureGame(View view){
        gameType = "cultura española";
        startActivity(new Intent(this, PhotosActivity.class).putExtra("Tipo de juego", gameType));
    }
}