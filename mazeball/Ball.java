package com.example.mazeball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Ball extends View{
    public float x;
    public float y;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Ball(Context context, float x, float y, int r) {
        super(context);
        mPaint.setColor(0xFF0000FF);  //Color azul para la bola
        this.x = x; //Posición X en la pantalla al crearla
        this.y = y; //Posición Y en la pantalla al crearla
        this.r = r; //Radio de la bola
    }

    public int radio(){
        return this.r;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
    }
}
