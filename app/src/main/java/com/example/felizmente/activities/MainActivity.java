package com.example.felizmente.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felizmente.R;
import com.example.felizmente.activities.music.MusicActivity;
import com.example.felizmente.activities.quiz.QuizzTypesActivity;
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
        Intent intent = new Intent(this, QuizzTypesActivity.class);
        startActivity(intent);
    }

    public void closeApp(View view) {
        createDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            createDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Html.fromHtml("<big>¿Desea cerrar la aplicación?</big>",
                Html.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(Html.fromHtml("<big><font color='#99173C'>Cerrar aplicación</font></big>",
                        Html.FROM_HTML_MODE_LEGACY) ,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(Html.fromHtml("<big><font color='#EA596E'> Seguir en la app </font></big>",
                        Html.FROM_HTML_MODE_LEGACY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}