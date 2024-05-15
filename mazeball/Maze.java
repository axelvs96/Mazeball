package com.example.mazeball;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class Maze extends Activity {

    Ball vBall = null;
    Circle circleFinal = null;
    Wall[] muros;
    Handler RedrawHandler = new Handler();
    Timer mTmr = null;
    TimerTask mTsk = null;
    int mScrWidth, mScrHeight;
    float muroV, muroH;
    android.graphics.PointF mBallPos, mBallSpd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //Oculta la barra
        //Permite que la app se ejecute en pantalla completa y con la pantalla siempre encendida
        getWindow().setFlags(0xFFFFFFFF, LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.maze);

        final Intent intent = getIntent();
        //Recoge el nivel que le hemos pasado al clicar desde el menu principal
        String vLevel = intent.getExtras().getString("level");


        final FrameLayout mainView = (android.widget.FrameLayout) findViewById(R.id.maze);

        //Coge las dimensiones de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();
        mBallPos = new android.graphics.PointF();
        mBallSpd = new android.graphics.PointF();


        //Iniciamos variables de velocidad
        mBallSpd.x = 0;
        mBallSpd.y = 0;

        if (vLevel.equals("easy")) {

            //Longitud de los muros
            //verticales
            muroV = mScrHeight / 6;
            //horizontales
            muroH = mScrWidth / 5;

            //Iniciamos variables de posición
            mBallPos.x = muroH / 2;
            mBallPos.y = muroV / 2;

            muros = new Wall[]{
                    new Wall(this, 0, 0, 0, mScrHeight),//izquierda
                    new Wall(this, 0, 0, mScrWidth, 0),//arriba
                    new Wall(this, mScrWidth, 0, mScrWidth, mScrHeight),//derecha
                    new Wall(this, 0, mScrHeight, 1080, mScrHeight),//abajo
                    //fin bordes
                    new Wall(this, muroH, 0, muroH, muroV),
                    new Wall(this, muroH * 4, 0, muroH * 4, muroV),
                    new Wall(this, muroH, muroV, muroH * 2, muroV),
                    new Wall(this, muroH * 2, muroV, muroH * 3, muroV),
                    new Wall(this, muroH * 4, muroV, muroH * 5, muroV),
                    new Wall(this, 0, muroV * 2, muroH, muroV * 2),
                    new Wall(this, muroH, muroV * 2, muroH * 2, muroV * 2),
                    new Wall(this, muroH * 2, muroV * 2, muroH * 3, muroV * 2),
                    new Wall(this, muroH * 4, muroV * 2, muroH * 5, muroV * 2),
                    new Wall(this, muroH, muroV * 2, muroH, muroV * 3),
                    new Wall(this, muroH * 2, muroV * 2, muroH * 2, muroV * 3),
                    new Wall(this, muroH * 3, muroV * 2, muroH * 3, muroV * 3),
                    new Wall(this, muroH * 4, muroV * 2, muroH * 4, muroV * 3),
                    new Wall(this, 0, muroV * 3, muroH, muroV * 3),
                    new Wall(this, muroH * 2, muroV * 3, muroH * 3, muroV * 3),
                    new Wall(this, muroH * 4, muroV * 3, muroH * 4, muroV * 4),
                    new Wall(this, 0, muroV * 4, muroH, muroV * 4),
                    new Wall(this, muroH, muroV * 4, muroH * 2, muroV * 4),
                    new Wall(this, muroH * 3, muroV * 4, muroH * 4, muroV * 4),
                    new Wall(this, muroH * 3, muroV * 4, muroH * 3, muroV * 5),
                    new Wall(this, muroH * 4, muroV * 4, muroH * 4, muroV * 4),
                    new Wall(this, 0, muroV * 5, muroH, muroV * 5),
                    new Wall(this, muroH * 3, muroV * 5, muroH * 4, muroV * 5)
            };
            circleFinal = new Circle(this, Math.round(muroH * 4.5), Math.round(muroV * 2.5), 50);

        } else if (vLevel.equals("medium")) {

            //Longitud de los muros
            //verticales
            muroV = mScrHeight / 6;
            //horizontales
            muroH = mScrWidth / 5;

            mBallPos.x = muroH / 2;
            mBallPos.y = muroV / 2;

            muros = new Wall[]{
                    //Creamos las paredes de la pantalla
                    // PARED DE IZQUIERDA
                    new Wall(this, 0, 0, 0, mScrHeight),
                    // PARED DE ARRIBA
                    new Wall(this, 0, 0, mScrWidth, 0),
                    // PARED DE DERECHA
                    new Wall(this, mScrWidth, 0, mScrWidth, mScrHeight),
                    // PARED DE IZQUIERDA
                    new Wall(this, 0, mScrHeight, 1080, mScrHeight),
                    //Fin bordes
                    new Wall(this, muroH, 0, muroH, muroV * 2), // Vertical
                    new Wall(this, muroH, muroV * 2, muroH * 3, muroV * 2), // Horizontal
                    new Wall(this, muroH * 3, muroV, muroH * 3, (muroV * 2) + 10), // Vertical
                    new Wall(this, muroH * 2, muroV, (muroH * 3), muroV), // Horizontal
                    new Wall(this, 0, muroV * 3, muroH * 4, muroV * 3), // Horizontal
                    new Wall(this, muroH * 4, muroV, muroH * 4, (muroV * 3) + 10), // Vertical
                    new Wall(this, muroH, muroV * 4, muroH * 5, muroV * 4), // Horizontal
                    new Wall(this, muroH, muroV * 5, muroH * 4, muroV * 5), // Horizontal
                    new Wall(this, muroH, (muroV * 5), muroH, muroV * 6) // Vertical
            };

            circleFinal = new Circle(this, Math.round(muroH * 1.5), Math.round(muroV * 5.5), 50);

        }
        //crea la bola
        vBall = new Ball(this, mBallPos.x, mBallPos.y, 30);
        mainView.addView(vBall); //añade la bola a la pantalla
        vBall.invalidate(); //dibuja la bola

        //crea el circulo que marca el final del laberinto
        circleFinal.setZ(-1);
        mainView.addView(circleFinal);
        circleFinal.invalidate();

        for (int i = 0; i < muros.length; i++) {
            mainView.addView(muros[i]);
            muros[i].invalidate();
        }


        //Listener para el acelerometro
        ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener(
                new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        //Cambia la velocidad de la bola dependiendo de la inclinación del telefono
                        mBallSpd.x = -event.values[0];
                        mBallSpd.y = event.values[1];
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    } //precision en el sensor
                }, ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
    }//OnCreate


    @Override
    public void onPause() {
        mTmr.cancel();
        mTmr = null;
        mTsk = null;
        //Muestra un mensaje cuando finalizas el circuito
        if (finished()) {
            Toast.makeText(Maze.this, "Congratulations!", Toast.LENGTH_SHORT).show();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        //Crea un Timer para mover la bola a la nueva posicion
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {

                android.util.Log.d("TiltBall", "Timer Hit - " + mBallPos.x + ":" + mBallPos.y);
                //move ball based on current speed
                mBallPos.x += mBallSpd.x;
                mBallPos.y += mBallSpd.y;

                //Controlar colisiones con los muros
                for (int i = 0; i < muros.length; i++) {
                    //dirección arriba
                    if (mBallPos.y > muros[i].inicioY) {
                        if (mBallSpd.y < 0 && mBallPos.y < muros[i].inicioY + vBall.radio() + muros[i].getStrokeWidth() && (mBallPos.x > muros[i].inicioX && mBallPos.x < muros[i].finalX)) {
                            mBallPos.y = muros[i].finalY + vBall.radio() + muros[i].getStrokeWidth();
                        }
                    }
                    //dirección abajo
                    if (mBallPos.y < muros[i].inicioY) {
                        if (mBallSpd.y > 0 && mBallPos.y > muros[i].inicioY - vBall.radio() - muros[i].getStrokeWidth() && (mBallPos.x > muros[i].inicioX && mBallPos.x < muros[i].finalX)) {
                            mBallPos.y = muros[i].finalY - vBall.radio() - muros[i].getStrokeWidth();
                        }
                    }
                    //dirección izquierda
                    if (mBallPos.x > muros[i].inicioX) {
                        if (mBallSpd.x < 0 && mBallPos.x < muros[i].inicioX + vBall.radio() + muros[i].getStrokeWidth() && (mBallPos.y > muros[i].inicioY && mBallPos.y < muros[i].finalY)) {
                            mBallPos.x = muros[i].finalX + vBall.radio() + muros[i].getStrokeWidth();
                        }
                    }
                    //dirección derecha
                    if (mBallPos.x < muros[i].inicioX) {
                        if (mBallSpd.x > 0 && mBallPos.x > muros[i].inicioX - vBall.radio() - muros[i].getStrokeWidth() && (mBallPos.y > muros[i].inicioY && mBallPos.y < muros[i].finalY)) {
                            mBallPos.x = muros[i].finalX - vBall.radio() - muros[i].getStrokeWidth();
                        }
                    }
                }

                if (finished()) {
                    Intent i = new Intent(Maze.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


                //actualiza la bola
                vBall.x = mBallPos.x;
                vBall.y = mBallPos.y;
                //redibuja la bola

                RedrawHandler.post(new Runnable() {
                    public void run() {
                        vBall.invalidate();
                    }
                });
            }
        };// TimerTask
        mTmr.schedule(mTsk, 10, 10);
        super.onResume();
    }//onResume

    //Comprueba que ha entrado en el circulo rojo que marca el final del laberinto
    public boolean finished() {
        if (mBallPos.x > circleFinal.x - circleFinal.radio() && mBallPos.x < circleFinal.x + circleFinal.radio()
                && mBallPos.y > circleFinal.y - circleFinal.radio() && mBallPos.y < circleFinal.y + circleFinal.radio()) {
            return true;
        }
        return false;
    }

    //Listener para cambio de la configuración.
    //Se utiliza cuando el usuario inclina tanto el teléfono que se puede poner en modo Landscape(horizontal)
    //Esto hace que siga estando en Portrait(vertical)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
