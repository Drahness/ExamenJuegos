package self.joanciscar.examenjuegos.elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import self.joanciscar.examenjuegos.R;

public class Asteroid {

    private int puntosDano;
    private float posX;
    private float posY;
    private float velX = 0; // solo se mueve hacia abajo.
    private float velY;
    private final Bitmap asteroid_Bitmap;

    public float getRadius() {
        return radius;
    }

    private final float radius;
    private final Paint painter = new Paint();
    private final Paint rad_painter = new Paint();

    public Asteroid(int puntosDano, float maxY, float maxX, Bitmap bitmap) {
        this.puntosDano = puntosDano;
        this.posX = new Random().nextFloat()*maxX;
        this.posY = new Random().nextFloat()*maxX;
        this.asteroid_Bitmap = bitmap;
        this.radius = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth()/2f : bitmap.getHeight()/2f;
        rad_painter.setStyle(Paint.Style.STROKE);
        rad_painter.setColor(Color.YELLOW);
        //rad_painter.setStrokeWidth(20);
    }

    public int getPuntosDano() {
        return puntosDano;
    }

    public void setPuntosDano(int puntosDano) {
        this.puntosDano = puntosDano;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isDead() {
        return puntosDano < 0;
    }

    public void move() {
        this.posY += velY;
        this.posX += velX;
    }

    public void paint(Canvas canvas) {
        int maxY = asteroid_Bitmap.getHeight()
            , maxX = asteroid_Bitmap.getWidth();
        canvas.drawBitmap(asteroid_Bitmap,posX-maxX/2f,posY-maxY/2f,painter);
        canvas.drawCircle(this.getPosX(),this.getPosY(),radius,rad_painter);
    }
}
