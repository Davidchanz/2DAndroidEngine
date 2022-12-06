package com.androidengine2d.BallBounce;


import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Circle;
import com.androidengine2d.engine.ShapeObject;

public class Aim extends ShapeObject {
    public Aim(String name, int id) {
        super(name, id);
    }
    public void setAim(Vector2 st, Vector2 d, int ball_radius){
        body.clear();
        Vector2 start = new Vector2(st);
        Vector2 prev = new Vector2(st);
        Ball testBall;
        int hit = 2;
        Vector2 dir = new Vector2(d).mul(ball_radius);
        Circle circle;
        int fl = 8;
        while (hit > 0){
            Vector2 next = start.add(dir);
            circle = new Circle(ball_radius, ball_radius, new Vector2(next), 0);
            testBall = new Ball("testBall", 1, circle);
            testBall.dir = new Vector2(dir);
            if(isCollision(testBall)){
                hit--;
                dir = new Vector2(testBall.dir);
                super.add(circle);
            }
            else if(range(next, prev) >= ball_radius){
                if(--fl == 0) super.add(circle);
                if(fl == 0) fl = 8;
            }
        }
    }
    private boolean isCollision(Ball originBall){
        /*if(Logic.isErrorCollision(originBall)){
            return true;
        }*/
        Brick brick = Logic.BrickCollision(originBall);
        if(brick != null) {

            Vector2 tmp = new Vector2(brick.position).sub(originBall.getPosition()).nor().mul(-1);
            originBall.move(tmp);
            return true;
        }
        return Logic.isCollision(originBall);
    }
    private float range(Vector2 v1, Vector2 v2){
        return (float)Math.sqrt((Math.abs(v1.x - v2.x) * Math.abs(v1.x - v2.x)) + (Math.abs(v1.y - v2.y) * Math.abs(v1.y - v2.y)));
    }
}
