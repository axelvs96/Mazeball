package com.example.mazeball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Button play, options, acercade, salir;
    boolean easy, medium, hard, sound;
    MediaPlayer mediaPlayer;

    public static final String PREFS_NAME = "FitxerPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Musica del juego
        mediaPlayer = MediaPlayer.create(this,R.raw.theme);
        mediaPlayer.start();

        play = (Button) findViewById(R.id.play);

        options = (Button) findViewById(R.id.opciones);

        acercade = (Button) findViewById(R.id.acercade);

        salir = (Button) findViewById(R.id.salir);

    }

    @Override
    public void onResume(){
        final SharedPreferences config = getSharedPreferences(PREFS_NAME, 0);
        sound = config.getBoolean("switchSound", true);
        easy = config.getBoolean("easy", true);
        medium = config.getBoolean("medium", false);
        //hard = config.getBoolean("hard", false);

        //control de la musica durante el juego
        if(sound) {
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.setLooping(true);
                mediaPlayer.setVolume(100,100);
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        }else{
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Maze.class);
                //Segun el nivel escogido en opciones, se seleccionara un mapa u otro
                if(easy){
                    i.putExtra("level", "easy");
                }else if(medium){
                    i.putExtra("level","medium");
                }/*else{
                    //i = new Intent(MainActivity.this, Level3.class);
                }*/
                startActivity(i);
                finish();
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Opciones.class);
                startActivity(i);
            }
        });
        acercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AcercaDe.class);
                startActivity(i);
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
