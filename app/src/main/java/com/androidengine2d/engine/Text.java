package com.androidengine2d.engine;


import android.graphics.Canvas;
import android.graphics.Paint;
import com.androidengine2d.UnityMath.Vector2;

public class Text extends AbstractShape{
    /**
     * Inner constructor for ini vertices and color members.
     *
     * @param c
     */
    public String text;
    public Text(String text, Vector2 position, int c) {
        super(c);
        this.text = text;
        this.position = new Vector2(position);
        this.center = new Vector2();
    }

    @Override
    public void paint(Canvas g, ShapeObject o) {
        Vector2 tmp = new Vector2(position);
        Game.toSceneDimension(tmp);
        Paint paint = new Paint();
        paint.setColor(this.color);
        paint.setTextSize(30);
        g.drawText(text, (int)tmp.x-5, (int)tmp.y+5, paint);//g.drawString(text, (int)tmp.x-5, (int)tmp.y+5);
    }
}
