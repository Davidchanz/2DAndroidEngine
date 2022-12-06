package com.androidengine2d.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.androidengine2d.R;
import com.androidengine2d.UnityMath.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    //private Context context;
    public static ArrayList<ShapeObject> objects = new ArrayList<>();//set objects for painting
    private boolean showFPS;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    protected boolean Vaxis = false;//flag show axis XOY on scene
    protected boolean Vcenter = false;//flag show objects centers
    public static Camera camera = new Camera();//camera

    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        //Canvas canvas = surfaceHolder.lockCanvas();
        /*WIDTH = canvas.getWidth();
        HEIGHT = canvas.getHeight();*/
        WIDTH = 1080;
        HEIGHT = 1800;
        //surfaceHolder.unlockCanvasAndPost(canvas);

        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    public void game(Canvas canvas){

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(showFPS) {
            drawUPS(canvas);
            drawFPS(canvas);
        }
        game(canvas);

        if(this.Vaxis) {//if flag axis true paint XOY axis
            Paint paint = new Paint(); paint.setColor(Color.BLACK);//g.setColor(Color.BLACK);//set color BLACK
            canvas.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2, paint);//g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);//paint -XOX
            canvas.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT, paint);//g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);//paint -YOY
            Vector2 zero = new Vector2(0, 0);
            toSceneDimension(zero);//get center 0,0 in scene dimension
            paint.setColor(Color.RED);//g.setColor(Color.RED);//set color RED
            canvas.drawRect(zero.x-1, zero.y-1, 2, 2, paint);//g.fillRect((int) zero.x-1, (int) zero.y-1, 2, 2);//paint O(XOY) - center point
        }
        /*if(Vborder) {//if flag border true paint scene border
            for (var shape : this.border.body) {
                shape.paint(g, this.border);//paint border's shapes
            }
        }*/
        for (ShapeObject it : objects.toArray(new ShapeObject[0])) {
            if(this.Vcenter) {//if flag center true paint shapes centers
                Vector2 tmp = new Vector2(it.center);
                toSceneDimension(tmp);//get shape's center in scene dimension
                Paint paint = new Paint(); paint.setColor(Color.MAGENTA);//g.setColor(Color.MAGENTA);//set color MAGENTA
                canvas.drawRect(tmp.x, tmp.y, 3, 3, paint);//g.fillRect((int) tmp.x, (int) tmp.y, 3, 3);//paint shape's center
            }
            for(AbstractShape shape: it.body.toArray(new AbstractShape[0])) {
                //if(this.paintAll)
                shape.paint(canvas, it);//paint shapes
                /*else if(shape.repaint) {
                    shape.paint(g, it);//paint shapes if it need repaint
                    shape.repaint = false;
                }*/
            }
        }
    }
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {

    }

    public void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
    }
    /**Get point in scene dimension*/
    public static void toSceneDimension(Vector2 point){
        point.x = point.x + WIDTH/2;
        point.y = -(point.y - HEIGHT/2);
    }
    /**Get point in screen dimension*/
    public static void toScreenDimension(Vector2 point){
        point.x = point.x - WIDTH/2;
        point.y = HEIGHT/2 - point.y;
    }
    public void setCoordVisible(boolean b){
        this.Vaxis = b;//set axis show flag true
    }
    /**Show XOY center objects*/
    public void setCenterVisible(boolean b){
        this.Vcenter = b;//set center show flag true
    }
    public void remove(ShapeObject o){
        objects.remove(o);
    }
    /**Add new shape on scene*/
    public void add(ShapeObject o){
        objects.add(o);
    }
    /**Add all shape in collection on scene*/
    public void addAll(Collection<ShapeObject> o){
        objects.addAll(o);
    }
    /**Paint on the Image ande draw it*/
}





















