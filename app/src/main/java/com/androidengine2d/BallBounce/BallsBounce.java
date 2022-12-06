package com.androidengine2d.BallBounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BallsBounce extends Game {
    private Ball[] balls;
    private int ball_count;
    private int brick_size;
    private int ball_radius;
    private ArrayList<Brick> bricks;
    private int brick_count;
    private Cart cart;
    private int cart_width;
    private int cart_height;
    private Input input;
    private boolean game;
    private boolean fly;
    private int lvl;
    private int score;
    private boolean fl;
    private int delay;
    private float acceleration;
    private Map map;
    private Vector2 startPoint;
    //private JFileChooser fileChooser;TODO
    private long timeStart;
    private long timeEnd;
    private long timeFly;
    private Aim aim;
    public static int countFlyBalls;
    public Vector2 oldCursor;
    public BallsBounce(Context context) {
        super(context);
        ini();
    }
    private void ini(){
        this.setShowFPS(true);
        //fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File("src/main/resources"));
        String mapPath = "/home/katsitovlis/Documents/Project/JavaMaven/2DAndroidEngine/app/src/main/res/save.txt";
        //int result = fileChooser.showOpenDialog(this);
        //if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
        //    File selectedFile = fileChooser.getSelectedFile();
        //    mapPath = selectedFile.getPath();
        //}
        //WIDTH = 800;
        //HEIGHT = 600;
        startPoint = new Vector2(0,-HEIGHT/2+50);
        acceleration = 1.0f;
        fl = true;
        score = 0;
        lvl = 0;
        delay = 2 + (lvl * 10);
        fly = false;
        game = true;
        input = new Input();
        /*scene = new Scene(WIDTH, HEIGHT);
        scene.setCenterVisible(true);
        scene.setCoordVisible(true);
        scene.setBorderVisible(true);*/
        ball_count = 50;
        countFlyBalls = 0;
        balls = new Ball[ball_count];
        ball_radius = 10;
        int border_size = 1;
        for(int i = 0; i < ball_count; i++)
            balls[i] = new Ball("ball", 1, new Circle(ball_radius, new Vector2(startPoint), Color.WHITE));
        for (Ball ball : balls)
            this.add(ball);
        brick_size = 50;
        //map = loadMap(mapPath);
        //this.setBorder(border_size, Color.BLACK);TODO
        int shift = border_size*3 + brick_size/2;
        Logic.borderV = new Vector2(-WIDTH/2 + shift, WIDTH/2 - shift);
        Logic.borderH = new Vector2(-HEIGHT/2 + shift, HEIGHT/2 - shift);
        //brick_count = map.objects.size();
        brick_count = 20;
        int row = 5;
        int col = 20;
        bricks = new ArrayList<>();
        int count = 0;
        int shiftV = 300;
        int shiftH = 0;
        for(int i = 0; i < brick_count; i++){
            shiftH += brick_size * 2;
            bricks.add(new Brick("brick", 2, new Rectangle(brick_size, brick_size, new Vector2(-WIDTH/2 + brick_count + shiftH,HEIGHT/2-50 - shiftV), Color.RED),20));
            if(++count == brick_count / row){
                count = 0;
                shiftV += brick_size * 2;
                shiftH = 0;
            }
            //bricks.add(map.objects.get(i));
        }
        for (Brick brick : bricks){
            this.add(brick);
        }
        Logic.bricks = bricks;
        /*cart_width = 40;
        cart_height = 2;
        cart = new Cart("cart", 1, new Rectangle(cart_height, cart_width, new Vector2(0, -250 - ball_radius-cart_height), Color.RED));
        scene.add(cart);*/
        aim = new Aim("aim", 0);
        this.add(aim);
        oldCursor = new Vector2();
    }
    private Map loadMap(String path){
        try{
            return Map.loadMap(path, brick_size, getContext());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void game(Canvas canvas) {
        super.game(canvas);
        balls[0].move(new Vector2(0, 1));
        if(fly){
            int j = 0;
            int count = 0;
            while(fl) {
                timeFly++;
                if(timeFly - timeStart > 1000 && acceleration <= 10.0f){
                    timeStart = timeFly;
                    acceleration += 0.5;
                    for(int i = 0; i < ball_count; i++){
                        if(balls[i].fly) {
                            balls[i].dir.mul(acceleration);
                        }
                    }
                }
                for (int i = 0; i < j; i++) {
                    if(balls[i].fly) {
                        balls[i].move(new Vector2(balls[i].dir));
                        if (isCollision(balls[i])) {
                            if(!balls[i].fly) {
                                countFlyBalls--;
                                if(countFlyBalls <= 0) {
                                    fly = false;
                                    fl = true;
                                }
                            }
                        }
                    }
                }
                //this.repaint();TODO
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                if(count >= ((ball_radius * 2) / acceleration)){
                    j++;
                    count = 0;
                }
                if(j > ball_count)
                    fl = false;
            }
            timeFly++;
            if(timeFly - timeStart > 1000 && acceleration <= 10.0f){
                timeStart = timeFly;
                acceleration += 0.5;
                for(int i = 0; i < ball_count; i++){
                    if(balls[i].fly) {
                        balls[i].dir.mul(acceleration);
                    }
                }
            }
            for(int i = 0; i < ball_count; i++){
                if(balls[i].fly) {
                    balls[i].move(balls[i].dir);
                    if (isCollision(balls[i])) {
                        if(!balls[i].fly) {
                            countFlyBalls--;
                            if(countFlyBalls <= 0) {
                                fly = false;
                                fl = true;
                            }
                        }
                    }
                }
            }
            if(!fly){
                for (Ball ball: balls) {
                    Vector2 offset = new Vector2(startPoint).sub(ball.position);
                    ball.move(offset);
                }
                timeEnd = System.currentTimeMillis();
            }
        }else {
            /*if(Input.isClicked()){
                acceleration = 1.0f;
                countFlyBalls = ball_count;
                int mouseX = Input.getMouseX();
                int mouseY = Input.getMouseY();
                Vector2 cursor = new Vector2(mouseX, mouseY);
                for (Ball ball: balls) {
                    ball.dir = new Vector2(cursor).sub(ball.getPosition()).nor().mul(acceleration);
                    ball.fly = true;
                }
                fly = true;
                timeStart = System.currentTimeMillis();
                timeFly = timeStart;
                aim.setAim(startPoint, startPoint, ball_radius);
            }
            else {
                int mouseX = Input.getMouseX();
                int mouseY = Input.getMouseY();
                Vector2 cursor = new Vector2(mouseX, mouseY);
                if(!Objects.equals(oldCursor, cursor))
                    aim.setAim(startPoint, new Vector2(cursor).sub(startPoint).nor(), ball_radius);
                oldCursor = cursor;
            }*/
        }
    }
    public boolean isCollision(Ball originBall){
        if(Logic.isErrorCollision(originBall)){
            //originBall.fly = false;
            return true;
            /*countFlyBalls--;
            return countFlyBalls <= 0;*/
        }
        Brick brick = Logic.BrickCollision(originBall);
        if(brick != null) {
            score++;
            brick.hit();
            if (brick.getScore() <= 0) {
                brick_count -= 1;
                this.remove(brick);
                bricks.remove(brick);
                Logic.bricks = bricks;
            }
            Vector2 tmp = new Vector2(brick.position).sub(originBall.getPosition()).nor().mul(-1);
            originBall.move(tmp);
        }
        return Logic.isCollision(originBall);
    }
}
