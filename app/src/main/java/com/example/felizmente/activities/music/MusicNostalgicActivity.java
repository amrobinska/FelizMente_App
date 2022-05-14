package com.example.felizmente.activities.music;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.felizmente.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MusicNostalgicActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener{
    YouTubePlayerView youTubePlayerView;
    String youtubeKey ="AIzaSyCShf8QPsI4-7pJ8ggqY3txjKoSq3mSczY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_nostalgic);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(youtubeKey,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        if(!restored){
            youTubePlayer.cuePlaylist("PL7YnDfuCicfUfJj97G4WDLuu5Ko87vjBA"); //caracteres tras /watch?v=
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,1).show();
        } else{
            String error = "Error al inicializar YouTube "+youTubeInitializationResult.toString();
            Toast.makeText(getApplication(),error,Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==1){
            getYoutubePlayerProvider().initialize(youtubeKey,this);
        }
    }
    protected YouTubePlayer.Provider getYoutubePlayerProvider(){
        return youTubePlayerView;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}