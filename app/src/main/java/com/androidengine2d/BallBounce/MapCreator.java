package com.androidengine2d.BallBounce;

import android.content.Context;
import android.graphics.Color;
import com.androidengine2d.UnityMath.Vector2;
import com.androidengine2d.engine.Circle;
import com.androidengine2d.engine.Game;
import com.androidengine2d.engine.Rectangle;
import com.androidengine2d.engine.Triangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapCreator extends Game {
    private int WIDTH;
    private int HEIGHT;
    private Input input;
    private int ball_radius;
    private Ball ball;
    private Brick[][] map;
    private int row;
    private int col;
    private int brick_size;
    private Vector2 start;
    /*private JTextField textField;TODO
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private JFileChooser fileChooser;*/
    private int[] palette;
    private int MAX_SCORE;
    private void game(){
        /*int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY()+23;
        System.out.println(mouseX+" / "+mouseY);
        if(Input.isClicked()){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++) {
                    if(mouseX > map[i][j].body.get(0).position.x - brick_size &&
                       mouseX < map[i][j].body.get(0).position.x + brick_size &&
                       mouseY > map[i][j].body.get(0).position.y - brick_size &&
                       mouseY < map[i][j].body.get(0).position.y + brick_size){
                        //double score = Integer.parseInt(textField.getText());
                        double score = 20;
                        if(map[i][j].getType() == 0) {
                            map[i][j].setScore((int)score);
                            map[i][j].body.get(0).setColor(palette[(int)((score/MAX_SCORE) * palette.length)]);
                            map[i][j].setColor(palette[(int)((score/MAX_SCORE) * palette.length)]);
                            map[i][j].setType(1);
                        } else if (map[i][j].getType() == 1) {
                            Vector2 P0 = new Vector2(-brick_size, -brick_size);
                            Vector2 P1 = new Vector2(brick_size, -brick_size);
                            Vector2 P2 = new Vector2(brick_size, brick_size);
                            Vector2 P3 = new Vector2(-brick_size, brick_size);
                            this.remove(map[i][j]);
                            map[i][j] = new Brick("brick", 1, new Triangle(P0, P1, P2, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), palette[(int)((score/MAX_SCORE) * palette.length)]), 0,2);
                            map[i][j].setScore((int)score);
                            map[i][j].setType(2);
                            this.add(map[i][j]);
                        }else if (map[i][j].getType() == 2) {
                            Vector2 P0 = new Vector2(-brick_size, -brick_size);
                            Vector2 P1 = new Vector2(brick_size, -brick_size);
                            Vector2 P2 = new Vector2(brick_size, brick_size);
                            Vector2 P3 = new Vector2(-brick_size, brick_size);
                            this.remove(map[i][j]);
                            map[i][j] = new Brick("brick", 1, new Triangle(P1, P2, P3, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), palette[(int)((score/MAX_SCORE) * palette.length)]), 0,3);
                            map[i][j].setScore((int) score);
                            map[i][j].setType(3);
                            this.add(map[i][j]);
                        }else if (map[i][j].getType() == 3) {
                            Vector2 P0 = new Vector2(-brick_size, -brick_size);
                            Vector2 P1 = new Vector2(brick_size, -brick_size);
                            Vector2 P2 = new Vector2(brick_size, brick_size);
                            Vector2 P3 = new Vector2(-brick_size, brick_size);
                            this.remove(map[i][j]);
                            map[i][j] = new Brick("brick", 1, new Triangle(P2, P3, P0, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), palette[(int)((score/MAX_SCORE) * palette.length)]), 0,4);
                            map[i][j].setScore((int) score);
                            map[i][j].setType(4);
                            this.add(map[i][j]);
                        }else if (map[i][j].getType() == 4) {
                            Vector2 P0 = new Vector2(-brick_size, -brick_size);
                            Vector2 P1 = new Vector2(brick_size, -brick_size);
                            Vector2 P2 = new Vector2(brick_size, brick_size);
                            Vector2 P3 = new Vector2(-brick_size, brick_size);
                            this.remove(map[i][j]);
                            map[i][j] = new Brick("brick", 1, new Triangle(P3, P0, P1, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), palette[(int)((score/MAX_SCORE) * palette.length)]), 0,5);
                            map[i][j].setScore((int) score);
                            map[i][j].setType(5);
                            this.add(map[i][j]);
                        } else *//*if(map[i][j].getType() == 2)*//*{
                            this.remove(map[i][j]);
                            map[i][j] = new Brick("brick", 1, new Rectangle(brick_size, brick_size, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), 0), 0);
                            map[i][j].setType(0);
                            this.add(map[i][j]);
                        }
                    }
                }
            }
            //scene.repaint();TODO
        }*/
    }
    private void ini(){
        MAX_SCORE = 100;
        palette = new int[10];
        /*int offsetR = 0;
        int offsetG = 0;
        int offsetB = 0;
        int tmpR = 1;
        int tmpG = 1;
        for (int i = 0; i < 10; i++){
            if(i % 1 == 0) offsetR = 1;
            //if(i % 3 == 0) offsetG = 1;
            //if(i % 1 == 0) offsetB = 1;
            if(i >= 15/2) tmpR = 0;
            if(tmpR == 1) tmpG = 0;
            else tmpG = 1;
            //palette[i] = new Color((100 + (15 - tmp*i)) + i*15*offsetR,i*15*offsetG,(40 + (15 - i)) + i*15*offsetB);
            palette[i] = new Color((100 + (15 - tmpR*i)) + i*15*offsetR,(100 + (15 - tmpG*i)) + i*15*offsetG,0);
        }*/
        palette[0] = Color.RED;
        palette[1] = Color.MAGENTA;
        palette[2] = Color.MAGENTA;
        palette[3] = Color.BLUE;
        palette[4] = Color.CYAN;
        palette[5] = Color.GREEN;
        palette[6] = Color.GREEN;
        palette[7] = Color.YELLOW;
        palette[8] = Color.WHITE;
        palette[9] = Color.YELLOW;

        WIDTH = 800;
        HEIGHT = 600;
        /*scene = new Scene(WIDTH, HEIGHT);TODO
        scene = new Scene(WIDTH, HEIGHT);
        scene.setCenterVisible(true);
        scene.setCoordVisible(true);
        scene.setBorderVisible(true);
        int border_size = 1;
        scene.setBorder(border_size, Color.BLACK);*/
        input = new Input();
        ball_radius = 10;
        ball = new Ball("ball", 1, new Circle(ball_radius, new Vector2(0,-HEIGHT/2 + 50), Color.WHITE));
        this.add(ball);
        row = 10;
        col = 21;
        brick_size = 15;
        start = new Vector2(-col*brick_size,0);
        map = new Brick[row][col];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                map[i][j] = new Brick("brick", 1, new Rectangle(brick_size, brick_size, new Vector2(start.x + 2 * brick_size * j, start.y + 2 * brick_size * i), 0), 0);
                map[i][j].setType(0);
                this.add(map[i][j]);
            }
        }
        /*fileChooser = new JFileChooser();TODO
        fileChooser.setCurrentDirectory(new File("src/main/resources"));

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        menuItem = new JMenuItem("Save");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveMap("src/main/resources/save.txt");
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = fileChooser.showSaveDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    // user selects a file
                    File selectedFile = fileChooser.getSelectedFile();
                    saveMap(selectedFile.getPath());
                }
            }
        });
        menu.add(menuItem);*/
    }
    private void saveMap(String fileName){
        System.out.println("Save");
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(fileName);
            StringBuilder res = new StringBuilder();
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    res.append("[");
                    res.append(String.valueOf(map[i][j].getType())).append(",");
                    res.append(String.valueOf(map[i][j].getColor())).append(",");
                    res.append(String.valueOf(map[i][j].getScore()));
                    res.append("]");
                    res.append(',');
                }
                if(res.lastIndexOf(",") != -1)
                    res.deleteCharAt(res.lastIndexOf(","));
                res.append("\n");
            }
            res.deleteCharAt(res.length()-1);
            myWriter.write(res.toString());

            //myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public MapCreator(Context context){
        super(context);
        ini();
        /*this.setSize(new Dimension(WIDTH+10, HEIGHT+45));TODO
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(scene, BorderLayout.CENTER);
        this.addMouseMotionListener(input.getMouseAdapter());
        this.addMouseListener(input.getMouseAdapter());
        this.addKeyListener(input.getKeyAdapter());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                game();
            }
        });
        textField = new JTextField("0");
        this.add(textField, BorderLayout.SOUTH);
        this.setJMenuBar(menuBar);
        this.setVisible(true);*/
    }
}
