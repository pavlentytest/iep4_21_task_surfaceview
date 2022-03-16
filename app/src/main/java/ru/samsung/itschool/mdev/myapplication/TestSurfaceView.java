package ru.samsung.itschool.mdev.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public float x,y,r;
    public boolean firstClick = false;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.x = event.getX();
        this.y = event.getY();
        this.r = 0;
        this.firstClick = true;
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // запустить поток
        new MyDrawThread().start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    class MyDrawThread extends Thread {
        @Override
        public void run() {
            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            while(true) {
                Canvas canvas = getHolder().lockCanvas();
                canvas.drawColor(Color.BLUE);
                r += firstClick ? 5 : 0;
                canvas.drawCircle(x,y,r,paint);
                getHolder().unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
