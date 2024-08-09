package com.adrianlong;


import com.adrianlong.display.Display;
import com.adrianlong.display.ShapeClasses.Rect;
import com.adrianlong.display.ShapeClasses.Shape;
import com.adrianlong.display.ShapeClasses.Text;

import java.awt.*;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = 1000;
        Display d = new Display("test",screenSize.width,screenSize.height);
        Display d1 = new Display("Terminal",350,400);
        d.frame.setResizable(false);
        int cellSize = 1;

        GameOfLife game = new GameOfLife(screenSize.width,screenSize.height,0);
        for (int i = 268; i < (Math.min(screenSize.width, screenSize.height)) / 2 - 100; i++) {
            game = new GameOfLife(screenSize.width,screenSize.height,i,game.terminal);
            int count = 0;

            d.shapes = game.toDisplay(cellSize);
            d.shapes.add(new Rect.TwoDem(0,0,14 * 10,20,Color.BLACK,true));
            d.shapes.add(new Text(100,0, String.format("%10d",count), Color.GREEN));
            d.repaint();
            //Thread.sleep(3000);

            while (!game.stop) {
                d.shapes = game.step(cellSize,count,i);
                d1.shapes = new ArrayList<>();
                d1.shapes.add(new Rect.TwoDem(0,0,14 * 10,20 * 11,Color.BLACK,true));
                d1.shapes.addAll(terminal(game.terminal,d1.frame.getHeight()));
                d1.repaint();
                d.repaint();
                count++;
            }
        }
    }
    public static List<Shape> terminal(String[] previousLines, int height) throws InterruptedException {
        List<Shape> shapes = new ArrayList<>();
        int lineCount = height / 20 - 2;
        for (int i = previousLines.length - 1; i >= 0; i--) {
            if (previousLines[i] == null || lineCount == 0) {
                break;
            }
            shapes.add(new Text(0,lineCount * 20, previousLines[i], Color.GREEN));
            lineCount--;
        }
        return shapes;
    }

}
