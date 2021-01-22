package self.joanciscar.examenjuegos;

import android.annotation.SuppressLint;
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

public class SurfaceViewGame extends SurfaceView implements SurfaceHolder.Callback{
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
    private Asteroid asteroid = null;
    private final List<Bala> balaList = new ArrayList<>();

    static {
    }
    public SurfaceViewGame(Context context) {
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        double x = event.getX();
        double y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(currentStateButton != STOPPED) {
                    Bala b = new Bala();
                    b.setPosX(player.getPosX());
                    b.setPosY(player.getPosY() - 20);
                    b.setVelY(-20);
                    balaList.add(b);
                }
                break;
            case MotionEvent.ACTION_MOVE: // mantener pulsado
                if(currentStateButton != STOPPED) {
                    if (x > player.getPosX() - player.getRadius() && x < player.getPosX() + player.getRadius()) {
                        if (y > player.getPosY() - player.getRadius() && y < player.getPosY() + player.getRadius()) {
                            player.setPosX((float) x);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(y > height - height/7f) {
                    currentStateButton = currentStateButton == 1 ? 0 : 1;
                    asteroid = new Asteroid(1,height,width,asteroid_bitmap);
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
        if(asteroid != null) {
            asteroid.paint(canvas);
        }
        player.paint(canvas);
        if(counter < 10) {
            canvas.drawText(String.valueOf(counter),canvas.getWidth()/2f,canvas.getHeight()/2f,text_painter);
        }
        else {
            canvas.drawText("Game over",canvas.getWidth()/2f,canvas.getHeight()/2f,text_painter);
        }
        for (Bala b :balaList) {
            b.paint(canvas);
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
                for(int i = 0 ; i < balaList.size() ; i++) {
                    Bala b = balaList.get(i);
                    b.move();
                    if(asteroid != null) {
                        if(b.getPosY() < 0) {
                            balaList.remove(b);
                        }
                        if (b.getPosY() > asteroid.getPosY() - asteroid.getRadius() && b.getPosY() < asteroid.getPosY() + asteroid.getRadius()) {
                            if (b.getPosX() > asteroid.getPosX() - asteroid.getRadius() && b.getPosX() < asteroid.getPosX() + asteroid.getRadius()) {
                                asteroid = null;
                                currentStateButton = STOPPED;
                                balaList.remove(b);
                                counter++;
                            }
                        }
                    }
                }
                if (currentStateButton == STOPPED) {
                    drawnedBitmap = mutableButtonStop;
                }
                else {
                    drawnedBitmap = mutableButtonStart;
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

