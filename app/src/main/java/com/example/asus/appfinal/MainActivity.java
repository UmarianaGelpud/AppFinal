package com.example.asus.appfinal;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    private static final String TAG="MainActivity";
    YouTubePlayerView view;
    //Clave de registro API.
    String clave = "AIzaSyCcnfOYxR6QWx-dfIHp94m4Vb_qlQ2SAXY";

    //VideoModel videoModel;
    //Video inicial.
    String video="nuckTcoZG4Q";

    ArrayList<VideoModel> vd = new ArrayList<>(), imgs=new ArrayList<>();


    private RecyclerView recyclerViewVideo;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view= (YouTubePlayerView)findViewById(R.id.youtube);
        view.initialize(clave,this);

        construirRecycler();
    }

    public void listVideo(){
        Log.d(TAG, "initImageBitMaps: Preparing bitmaps.");
        vd.add(new VideoModel("Alan Walker - Fade","", R.drawable.uno,"bM7SZ5SBzyY"));

        vd.add(new VideoModel("Swedish House Mafia - Greyhound","Download \"Greyhound\" on iTunes now: http://smarturl.it/SHMGreyhound UNTIL NOW - The Soundtrack to One Last Tour.", R.drawable.dos, "PDboaDrHGbA"));
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {

        if(!fueRestaurado){
            youTubePlayer.cueVideo(video); //video que se reproducirá en la parte superior.
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError()){

            youTubeInitializationResult.getErrorDialog(this, 1).show();
        }
        else {
            String error = "Error al inicializar YouTube." + youTubeInitializationResult.toString();
            Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resutlCode, Intent data){
        if(resutlCode==1){
            getYoutubePlayerProvider().initialize(clave, this);
        }
    }

    protected YouTubePlayer.Provider getYoutubePlayerProvider(){
        return view;
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

    public void construirRecycler(){
        vd=new ArrayList<>();

        recyclerViewVideo= (RecyclerView) findViewById(R.id.lista);

        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(this));


        listVideo();

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(vd);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(getApplicationContext(),""+vd.get(recyclerViewVideo.getChildAdapterPosition(view)).getTitulo(),Toast.LENGTH_SHORT).show();
                video=vd.get(recyclerViewVideo.getChildAdapterPosition(view)).getUrlVideo();
                YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        video="ft4jcPSLJfY&list=RDft4jcPSLJfY&start_radio=1";
                        youTubePlayer.loadPlaylist(video);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
            }
        });
        recyclerViewVideo.setAdapter(adapter);
    }
}

