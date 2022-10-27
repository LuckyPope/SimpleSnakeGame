package ru.vsu.cs.zhilyaev;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(370,380);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mainWindow = new Main();

    }
}
