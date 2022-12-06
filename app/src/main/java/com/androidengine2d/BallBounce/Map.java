package com.androidengine2d.BallBounce;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Rectangle;
import com.androidengine2d.engine.Triangle;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**Class for game map*/
public class Map {
    public ArrayList<Brick> objects = new ArrayList<>();
    public int WIDTH;
    public int HEIGHT;
    public char[][] mat_array;
    public int size;
    public static Vector2 start = new Vector2(0,0);
    private static Map map;

    Map(int w, int h, int size, Vector2 st){
        this.WIDTH = w;
        this.HEIGHT = h;
        this.mat_array = new char[this.WIDTH][this.HEIGHT];
        this.size = size;
        start = st;
    }

    /**Parse element of map*/
    private static void parseMap(int i, int j, String elem){
        if (Objects.equals(elem, "0")) {//space
            /*ShapeObject space = new ShapeObject("Space [" + i + "][" + j + "]", 0);
            space.add(new Engine2D.Rectangle(map.size, new Vector2(start.x + 2*map.size * j, start.y + 2*map.size * i), null));
            map.objects.add(space);*/
        } else {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.RED), Integer.parseInt(elem));
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }
    }
    private static void parseMap(int i, int j, int type, int color, int score){
        Vector2 P0 = new Vector2(-map.size, -map.size);
        Vector2 P1 = new Vector2(map.size, -map.size);
        Vector2 P2 = new Vector2(map.size, map.size);
        Vector2 P3 = new Vector2(-map.size, map.size);
        if (type == 0) {//space
            /*ShapeObject space = new ShapeObject("Space [" + i + "][" + j + "]", 0);
            space.add(new Engine2D.Rectangle(map.size, new Vector2(start.x + 2*map.size * j, start.y + 2*map.size * i), null));
            map.objects.add(space);*/
        }else if(type == 1) {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), color), score);
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }else if(type == 2) {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Triangle(P0, P1, P2, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), color), score, type);
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }else if(type == 3) {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Triangle(P1, P2, P3, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), color), score, type);
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }else if(type == 4) {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Triangle(P2, P3, P0, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), color), score, type);
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }else if(type == 5) {//brick
            Brick brick = new Brick("Border [" + i + "][" + j + "]", 1, new Triangle(P3, P0, P1, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), color), score, type);
            //border.add(new Rectangle(map.size, new Vector2(start.x + 2 * map.size * j, start.y + 2 * map.size * i), Color.BLACK));
            map.objects.add(brick);
        }
    }

    /**Load map from file  format:
     *
     * 0,0,0,0
     * 1,0,0,1
     *
     * */
    public static Map loadMap(String path, int size, Context context) throws IOException{
        try(/*FileReader fr = new FileReader(path);FileReader f = new FileReader(path)*/InputStream fr = context.openFileInput(path);InputStream f = context.openFileInput(path)){
            Scanner sc = new Scanner(fr);
            int ii = 0;
            int jj = 0;
            int h = 0;
            int w = 0;
            while(sc.hasNext()){
                String line = sc.nextLine();
                ii++;
                Scanner scl = new Scanner(line);
                scl.useDelimiter("],\\[");
                while (scl.hasNext()){
                    String c = scl.next();
                    jj++;
                }
                h = jj;
                jj = 0;
            }
            w = ii;
            ii = 0;
            //Vector2 st = new Vector2(-(size*(w-1)), -(size*(h-1)));
            Vector2 st = new Vector2(-h*size,0);
            map = new Map(w, h, size, st);
            sc = new Scanner(f);
            for(int i = 0; i < map.mat_array.length; ++i){
                String line = sc.nextLine();
                Scanner scl = new Scanner(line);
                scl.useDelimiter("],\\[");
                for(int j = 0; j < map.mat_array[i].length; ++j){
                    StringBuffer item = new StringBuffer(scl.next());
                    if(item.indexOf("[") != -1)
                        item.deleteCharAt(item.indexOf("["));
                    if(item.indexOf("]") != -1)
                        item.deleteCharAt(item.indexOf("]"));
                    Scanner itemSc = new Scanner(item.toString());
                    itemSc.useDelimiter(",");
                    int type = itemSc.nextInt();
                    int rgb = itemSc.nextInt();
                    int score = itemSc.nextInt();

                    map.mat_array[i][j] = (char) type;
                    parseMap(i, j, type, rgb, score);
                }
            }
            return map;
        }
    }
}
