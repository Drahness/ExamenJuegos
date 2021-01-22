package self.joanciscar.examenjuegos.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bala {
    private float posX;
    private float posY;
    private float velX = 0; // solo se mueve hacia arriba.
    private float velY;
    private float radius = 20f;
    private Paint painter = new Paint();

    public Bala() {
        painter.setColor(Color.BLUE);
    }

    public void paint(Canvas canvas) {
        canvas.drawCircle(this.posX,this.posY,this.radius,painter);
    }
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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

    public void move() {
        this.posY += velY;
        this.posX += velX;
    }

}
