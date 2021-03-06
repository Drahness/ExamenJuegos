package self.joanciscar.examenjuegos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import self.joanciscar.examenjuegos.elements.Asteroid;
import self.joanciscar.examenjuegos.elements.Bala;
import self.joanciscar.examenjuegos.elements.Nave;

public class SurfaceViewElements extends SurfaceView implements SurfaceHolder.Callback{
    private final GameThread gameThread;
    private final Paint defaultPainter = new Paint();
    private final Paint balaPainter = new Paint();

    private final Bitmap background_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fons_estreles);
    private final Bitmap button_stop_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_pause);
    private final Bitmap button_start_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_play);
    private final Bitmap asteroid_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
    private final Bitmap player_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nau_espacial_unity);

    private Bitmap drawnedBitmap;

    private final int STOPPED = 0;
    private final int RUNNING = 1;
    private int counter = 0;
    private int currentStateButton = 0;
    private Bitmap mutableButtonStop;
    private Bitmap mutableButtonStart;
    private int height,width;
    private Paint text_painter = new Paint();
    private Nave player;
    private Asteroid asteroid;
    private final List<Bala> balaList = new ArrayList<>();

    static {
    }
    public SurfaceViewElements(Context context) {
        super(context);
        this.getHolder().addCallback(this);
        gameThread = new GameThread(this.getHolder());
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        mutableButtonStart = Bitmap.createScaledBitmap(button_start_bitmap,width,height/7,false);
        mutableButtonStop = Bitmap.createScaledBitmap(button_stop_bitmap,width,height/7,false);
        this.height = height;
        this.width = width;
        asteroid = new Asteroid(1,height,width,asteroid_bitmap);
        player = new Nave(width / 2f, height-height/5f,player_bitmap);
        text_painter.setColor(Color.YELLOW);
        text_painter.setTextSize(60);
        text_painter.setTextAlign(Paint.Align.CENTER);
        gameThread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameThread.stop = true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE: // mantener pulsado
                break;
            case MotionEvent.ACTION_DOWN:
                double x = event.getX();
                double y = event.getY();
                if(counter < 10) {
                    if(y > height - height/7f) {
                        currentStateButton = currentStateButton == 1 ? 0 : 1;
                        asteroid = new Asteroid(1,height,width,asteroid_bitmap);
                        counter ++;
                    }
                }
                break;

        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void newDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(background_bitmap,0,0,defaultPainter);
        canvas.drawBitmap(drawnedBitmap,
                0,
                canvas.getHeight()-canvas.getHeight()/7f,
                defaultPainter);
        asteroid.paint(canvas);
        player.paint(canvas);
        if(counter < 10) {
            canvas.drawText(String.valueOf(counter),canvas.getWidth()/2f,canvas.getHeight()/2f,text_painter);
        }
        else {
            canvas.drawText("Game over",canvas.getWidth()/2f,canvas.getHeight()/2f,text_painter);
        }
    }


    private class GameThread extends Thread {
        private final SurfaceHolder holder;
        public boolean stop;

        public GameThread(SurfaceHolder surfaceHolder) {
            this.holder = surfaceHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            super.run();

            while (!stop) {
                if (currentStateButton == STOPPED) {
                    drawnedBitmap = mutableButtonStart;
                }
                else {
                    drawnedBitmap = mutableButtonStop;
                }
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas(null);
                    synchronized (holder) {
                        newDraw(canvas);
                    }
                } finally {
                    if(!stop) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }
    }

}

