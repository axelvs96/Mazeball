package com.example.mazeball;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import static android.R.attr.id;

public class Circle extends View{
    public float x;
    public float y;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Circle(Context context, float x, float y, int r) {
        super(context);
        mPaint.setColor(0xFFFF0000);  //Color rojo para el circulo
        this.x = x; //Posición X en la pantalla al crearlo
        this.y = y; //Posición Y en la pantalla al crearlo
        this.r = r; //Radio del circulo
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
