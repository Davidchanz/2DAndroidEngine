package com.androidengine2d.Test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Game;
import com.androidengine2d.engine.Rectangle;
import com.androidengine2d.engine.ShapeObject;

public class SimpleGame extends Game {
    public SimpleGame(Context context) {
        super(context);
    }

    @Override
    public void game(Canvas canvas) {
        super.game(canvas);
        /*Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(500, 500, 20,20,paint);*/
        ShapeObject obj = new ShapeObject();
        obj.add(new Rectangle(50,50, new Vector2(0,0), Color.RED));
        this.add(obj);
    }
}
