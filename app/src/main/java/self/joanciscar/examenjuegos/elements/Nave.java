package self.joanciscar.examenjuegos.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Nave {
    private float posX;
    private float posY;
    private final Paint painter = new Paint();
    private final Bitmap bitmap;
    private final Paint rad_painter = new Paint();


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    private float radius;

    public Nave(float posX, float posY, Bitmap bitmap) {
        this.posY = posY;
        this.posX = posX;
        this.bitmap = bitmap;
        this.radius = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth()/2f : bitmap.getHeight()/2f;
        rad_painter.setStyle(Paint.Style.STROKE);
        rad_painter.setColor(Color.YELLOW);
        //rad_painter.setStrokeWidth(20);
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

    public void paint(Canvas canvas) {
        int maxY = bitmap.getHeight()
                , maxX = bitmap.getWidth();
        canvas.drawBitmap(bitmap,posX-(maxX/2f),posY-(maxY/2f),painter);
        canvas.drawCircle(this.getPosX(),this.getPosY(),radius,rad_painter);
    }
}
