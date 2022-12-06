package com.androidengine2d.BallBounce;

import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Circle;
import com.androidengine2d.engine.ShapeObject;

public class Ball extends ShapeObject {
    public Vector2 dir;
    public boolean fly;
    public int radius;
    public Ball(String name, int id, Circle circle) {
        super(name, id);
        super.add(circle);
        this.fly = false;
        this.dir = new Vector2();
        this.radius = circle.height;
    }
    public Vector2 getPosition(){
        return this.position;
    }
}
