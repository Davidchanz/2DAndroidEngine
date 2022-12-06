package com.androidengine2d.BallBounce;


import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Game;
import com.androidengine2d.engine.Rectangle;
import com.androidengine2d.engine.Triangle;

import java.util.ArrayList;

public class Logic {
    public static Vector2 borderV;
    public static Vector2 borderH;
    public static ArrayList<Brick> bricks;
    public static boolean isCollision(Ball origBall){
        Vector2 ballPosition = new Vector2(origBall.getPosition());
        ballPosition.add(origBall.dir);
        /*if(isErrorCollision(origBall)){
            origBall.fly = false;
            countFlyBalls--;
            return countFlyBalls <= 0;
        }*/
        /*else*/ if(ballPosition.x < borderV.x) {
            float ang = origBall.dir.angleDeg();
            origBall.dir.rotateDeg(-2 * (ang - 90));
            return true;
        }
        else if(ballPosition.x > borderV.y){
            float ang = origBall.dir.angleDeg();
            origBall.dir.rotateDeg(2 * (90 - ang));
            return true;
        }
        else if(ballPosition.y < borderH.x){
            float ang = origBall.dir.angleDeg();
            origBall.dir.rotateDeg(2 * -ang);
            origBall.fly = false;
            /*countFlyBalls--;
            return countFlyBalls <= 0;*/
            return true;
        }
        else if(ballPosition.y > borderH.y){
            float ang = origBall.dir.angleDeg();
            origBall.dir.rotateDeg(-2 * ang);
            return true;
        }/*else if(ball.getPosition().x <= cart.getPosition().x + cart_width &&
                ball.getPosition().x >= cart.getPosition().x - cart_width &&
                ball.getPosition().y >= cart.getPosition().y - cart_height &&
                ball.getPosition().y <= cart.getPosition().y + cart_height){
            float ang = dir.angleDeg();
            dir.rotateDeg(2 * -ang);
            return false;
        }*/
        //BrickCollision(origBall);
        return false;
    }
    public static boolean isErrorCollision(Ball origBall){
        Vector2 ballPosition = new Vector2(origBall.getPosition());
        ballPosition.add(origBall.dir);
        if(ballPosition.x < borderV.x) {
            if(ballPosition.x < -(Game.WIDTH + origBall.radius * 4)) {
                origBall.fly = false;
                return true;
            }
        }
        else if(ballPosition.x > borderV.y){
            if(ballPosition.x > Game.WIDTH + origBall.radius * 4) {
                origBall.fly = false;
                return true;
            }
        }
        else if(ballPosition.y < borderH.x){
            if(ballPosition.y < -(Game.HEIGHT + origBall.radius * 4)) {
                origBall.fly = false;
                return true;
            }
        }
        else if(ballPosition.y > borderH.y){
            if(ballPosition.y > Game.HEIGHT + origBall.radius * 4) {
                origBall.fly = false;
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Integer> Interpolate(float i0, float d0, float i1, float d1) {
        ArrayList<Integer> values = new ArrayList<>();
        if (i0 == i1) {
            values.add((int)d0);
            return values;
        } else {
            float a = (d1 - d0) / (i1 - i0);
            float d = d0;

            for(int i = (int)i0; i <= (int)i1; ++i) {
                values.add((int)d);
                d += a;
            }
            return values;
        }
    }
    public static ArrayList<Vector2> Brezenheim(Vector2 v1, Vector2 v2) {
        ArrayList<Vector2> checkList = new ArrayList<>();
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        Vector2 tmp;
        int scanlineY;
        ArrayList<Integer> ys;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (v1.x > v2.x) {
                tmp = v1;
                v1 = v2;
                v2 = tmp;
            }

            ys = Interpolate(v1.x, v1.y, v2.x, v2.y);

            for(scanlineY = (int)v1.x; (float)scanlineY <= v2.x; ++scanlineY) {
                checkList.add(new Vector2(scanlineY, ys.get(scanlineY - (int)v1.x)));
            }
        } else {
            if (v1.y > v2.y) {
                tmp = v1;
                v1 = v2;
                v2 = tmp;
            }

            ys = Interpolate(v1.y, v1.x, v2.y, v2.x);

            for(scanlineY = (int)v1.y; (float)scanlineY <= v2.y; ++scanlineY) {
                checkList.add(new Vector2(ys.get(scanlineY - (int)v1.y), scanlineY));
            }
        }
        return checkList;
    }
    public static boolean sideCollision(ArrayList<Vector2> checkList, Ball origBall) {
        Vector2 ballPosition = new Vector2(origBall.getPosition());
        ballPosition.add(origBall.dir);
        for (Vector2 side : checkList) {
            float x = side.x;
            float y = side.y;
            float range = (float) Math.sqrt((Math.abs(x - ballPosition.x) * Math.abs(x - ballPosition.x)) + (Math.abs(y - ballPosition.y) * Math.abs(y - ballPosition.y)));
            if (range <= origBall.radius) {
                if(side == checkList.get(0) || side == checkList.get(checkList.size()-1)){
                    origBall.dir.rotateDeg(180);
                }
                return true;
            }
        }
        return false;
    }
    public static Brick BrickCollision(Ball ball){
        Brick[] bricks_old = bricks.toArray(new Brick[0]);
        int brick_count_old = /*brick_count*/bricks.size();
        boolean collision = false;
        for(int i = 0; i < brick_count_old; i++){
            Brick brick = bricks_old[i];
            switch (brick.getType()){
                case 1 : {
                    Rectangle rectangle = (Rectangle) brick.body.get(0);
                    if(sideCollision(Brezenheim(new Vector2(rectangle.Bot.P0).add(brick.position), new Vector2(rectangle.Bot.P1).add(brick.position)), ball)){//right
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(rectangle.Bot.P1).add(brick.position), new Vector2(rectangle.Bot.P2).add(brick.position)), ball)) {//top
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(rectangle.Top.P0).add(brick.position), new Vector2(rectangle.Top.P1).add(brick.position)), ball)) {//bot
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(2 * -ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(rectangle.Top.P1).add(brick.position), new Vector2(rectangle.Top.P2).add(brick.position)), ball)) {//left
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * (ang - 90));
                        collision = true;
                    }
                    break;
                }
                case 2 : {
                    Triangle triangle = (Triangle) brick.body.get(0);
                    if(sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P1).add(brick.position)), ball)){//bot
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(2 * -ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P1).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//right
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//diag
                        Vector2 perp = new Vector2(1,1).rotate90(1);
                        float ang = ball.dir.angleDeg(perp);
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }
                    break;
                }
                case 3 : {
                    Triangle triangle = (Triangle) brick.body.get(0);
                    if(sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P1).add(brick.position)), ball)){//right
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P1).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//top
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//diag
                        Vector2 perp = new Vector2(-1,1).rotate90(1);
                        float ang = ball.dir.angleDeg(perp);
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }
                    break;
                }
                case 4 : {
                    Triangle triangle = (Triangle) brick.body.get(0);
                    if(sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P1).add(brick.position)), ball)){//top
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P1).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//left
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * (ang - 90));
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//diag
                        Vector2 perp = new Vector2(1,1).rotate90(-1);
                        float ang = ball.dir.angleDeg(perp);
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }
                    break;
                }
                case 5 : {
                    Triangle triangle = (Triangle) brick.body.get(0);
                    if(sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P1).add(brick.position)), ball)){//left
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * (ang - 90));
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P1).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//bot
                        float ang = ball.dir.angleDeg();
                        ball.dir.rotateDeg(-2 * ang);
                        collision = true;
                    }else if (sideCollision(Brezenheim(new Vector2(triangle.P0).add(brick.position), new Vector2(triangle.P2).add(brick.position)), ball)) {//diag
                        Vector2 perp = new Vector2(-1,1).rotate90(-1);
                        float ang = ball.dir.angleDeg(perp);
                        ball.dir.rotateDeg(2 * (90 - ang));
                        collision = true;
                    }
                    break;
                }
                default : {
                    System.err.println("Unknown brick type!");
                    break;
                }
            }
            if (collision){
                return brick;
            }
            /*if(collision) {
                score++;
                brick.hit();
                if (brick.getScore() <= 0) {
                    brick_count -= 1;
                    scene.remove(brick);
                    bricks.remove(brick);
                }
                Vector2 tmp = new Vector2(brick.position).sub(ball.getPosition()).nor().mul(-1);
                ball.move(tmp);
                return;
            }*/
        }
        return null;
    }
}
