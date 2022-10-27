package ru.vsu.cs.zhilyaev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image snake;
    private Image apple;
    private Image headSnake;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private int headX = x[0];
    private int headY = y[0];
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int score = 0;


    public GameField() {
        Color background = new Color(171,126,43);
        setBackground(background);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                headX = 48;
                headY = 48;
            }
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(180, this);
        timer.start();
        createApple();
    }

    public boolean isEmpty(int posX, int posY) {
        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                if (headY == posY && headX == posX) {
                    return true;
                }
                continue;
            }
            if (x[i] == posX && y[i] == posY) {
                return true;
            }
        }
        return false;
    }

    public void createApple() {
        do {
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;
        } while (isEmpty(appleX, appleY));
    }

    public void loadImages() {
        ImageIcon imageApple = new ImageIcon("apple.png");
        apple = imageApple.getImage();
        ImageIcon imageSnake = new ImageIcon("snake.png");
        snake = imageSnake.getImage();
        ImageIcon imageHeadSnake = new ImageIcon("head.png");
        headSnake = imageHeadSnake.getImage();
    }

    public void paintScore(Graphics g) {
        super.paintComponent(g);
        String scoreStr = String.valueOf(score);
        g.setColor(Color.WHITE);
        g.drawString(scoreStr,170, 20);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            paintScore(g);
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(headSnake, headX, headY, this);
                    continue;
                }
                g.drawImage(snake, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(str, 160, SIZE/2);
        }
    }


    public void move() {
        for(int i = dots; i > 0; i--) {
            if (i == 1) {
                x[i] = headX;
                y[i] = headY;
                continue;
            }
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(left) {
            headX -= DOT_SIZE;
        }
        if(right) {
            headX += DOT_SIZE;
        }
        if(up) {
            headY -= DOT_SIZE;
        }
        if(down) {
            headY += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (headX == appleX && headY == appleY) {
            dots++;
            score++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && headX == x[i] && headY == y[i]) {
                inGame = false;
            }
        }
        if (headX > SIZE) {
            inGame = false;
        }
        if (headX < 0) {
            inGame = false;
        }
        if (headY > SIZE) {
            inGame = false;
        }
        if (headY < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
