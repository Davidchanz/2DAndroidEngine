package com.androidengine2d.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.androidengine2d.UnityMath.Vector2;

import java.util.ArrayList;

/**Class for Rectangles*/
public class Rectangle extends AbstractShape implements Resizing{
    public Triangle Top;//Top Triangle
    public Triangle Bot;//Bot Triangle
    /**Rectangle constructor
     * for Square
     * ini size, ini position, ini color*/
    public Rectangle(int size, Vector2 pos, int c){
        //invoke main constructor
        this(new Vector2(-size,-size), new Vector2(size,-size), new Vector2(size,size), new Vector2(-size,size), pos, c);
        this.height = size;//ini height
        this.width = size;//ini width
    }
    /**Rectangle constructor
     * for rectangles
     * ini height, ini width, ini position, ini color*/
    public Rectangle(int height, int width, Vector2 pos, int c){
        //invoke main constructor
        this(new Vector2(-width,-height), new Vector2(width,-height), new Vector2(width,height), new Vector2(-width,height), pos, c);
        this.height = height;//ini height
        this.width = width;//ini width
    }
    /**Rectangle main constructor
     * ini 4 point for rectangle, ini position, ini color*/
    public Rectangle(Vector2 P0, Vector2 P1, Vector2 P2, Vector2 P3, Vector2 pos, int c){
        super(c);//ini AbstractShape constructor
        this.position = new Vector2(pos);//ini position
        this.Top = new Triangle(P0, P1, P2, pos, this.color);//ini Top triangle
        this.Bot = new Triangle(P0, P3, P2, pos, this.color);//ini Bot triangle
        this.center = new Vector2((Top.center.x + Bot.center.x)/2,(Top.center.y + Bot.center.y)/2);//ini center
        this.vertices.addAll(this.Top.vertices);//add all vertices of Top's triangle
        this.vertices.add(this.Bot.vertices.get(1));//add 2 point of Bot triangle's in vertices
    }
    /**Method for resize*/
    public void resize(){
        Vector2 P0 = new Vector2(-width,-height);//ini new P0
        Vector2 P1 = new Vector2(width,-height);//ini new P1
        Vector2 P2 = new Vector2(width,height);//ini new P2
        Vector2 P3 = new Vector2(-width,height);//ini new P3
        this.Top = new Triangle(P0, P1, P2, this.position, this.color);//ini new Top triangle
        this.Bot = new Triangle(P0, P3, P2, this.position, this.color);//ini new Bot triangle
        this.vertices.clear();//clear old vertices
        this.vertices.addAll(this.Top.vertices);//add all vertices of new Top's triangle
        this.vertices.add(this.Bot.vertices.get(1));//add 2 point of new Bot triangle's in vertices
        this.center = new Vector2((Top.center.x + Bot.center.x)/2,(Top.center.y + Bot.center.y)/2);//ini new center
    }
    /**Method for paint on scene*/
    @Override
    public void paint(Canvas g, ShapeObject o) {
        ArrayList<Vector2> dots = getVertices(this.vertices);//get vertices for paint in screen dimension
        if(dots == null) return;//if no points for paint return
        if(colored) {//if color flag true fill object
            Paint paint = new Paint(); paint.setColor(this.color);//g.setColor(this.color);//set color object color
            DrawFilledTriangle(dots.get(0), dots.get(1), dots.get(2), g, paint, o);//paint Top triangle
            DrawFilledTriangle(dots.get(0), dots.get(3), dots.get(2), g, paint, o);//paint Bot triangle
        }
        Paint paint = new Paint(); paint.setColor(Color.BLACK);//g.setColor(Color.BLACK);//set color BLACK
        Brezenheim(dots.get(0), dots.get(1), g, paint);//paint rectangle's line
        Brezenheim(dots.get(1), dots.get(2), g, paint);//paint rectangle's line
        Brezenheim(dots.get(2), dots.get(3), g, paint);//paint rectangle's line
        Brezenheim(dots.get(3), dots.get(0), g, paint);//paint rectangle's line
        if(this.CENTER) {//if center flag true paint center point
            Vector2 zero = getVertices(this.center);//get object center in screen dimension
            paint = new Paint(); paint.setColor(Color.RED);//g.setColor(Color.RED);//set color for center point RED
            g.drawRect(zero.x, zero.y, 2, 2, paint);//g.fillRect((int) zero.x, (int) zero.y, 2, 2);//paint center point
        }
    }
}
