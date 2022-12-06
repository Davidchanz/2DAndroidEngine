package com.androidengine2d.BallBounce;


public class Input {
    /*private MouseAdapter mouseAdapter;
    private KeyAdapter keyAdapter;
    private static boolean isLeftMove;
    private static boolean isRightMove;
    private static int mouseX;
    private static int mouseY;
    private static boolean clicked;
    Input(){
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    isLeftMove = true;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    isRightMove = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    isLeftMove = false;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    isRightMove = false;
            }
        };
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clicked = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clicked = false;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                Vector2 tmp = new Vector2(e.getX(), e.getY()-40);
                Scene.toScreenDimension(tmp);
                mouseX = (int)tmp.x;
                mouseY = (int)tmp.y;
            }
        };
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static boolean isClicked() {
        return clicked;
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    public static boolean isLeftMove() {
        return isLeftMove;
    }

    public static boolean isRightMove() {
        return isRightMove;
    }*/
}
