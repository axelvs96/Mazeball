package com.example.mazeball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Wall extends View {
    public float inicioX, finalX;
    public float inicioY, finalY;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public Wall(Context context, float inicioX, float inicioY, float finalX, float finalY) {
        super(context);
        //color hex is [transparency][red][green][blue]
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(10);
        this.inicioX = inicioX;
        this.inicioY = inicioY;
        this.finalX = finalX;
        this.finalY = finalY;
    }

    public int getStrokeWidth(){
        return 10/2;
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(inicioX, inicioY, finalX, finalY, mPaint);
    }
}
