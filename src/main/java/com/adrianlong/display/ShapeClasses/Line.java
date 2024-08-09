package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class Line extends Shape {
    public Line(Color color, Point p1, Point p2) {
        super(color,p1,p2);
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(points[0].x,points[0].y,points[1].x,points[1].y);
        if (drawBounds) {
            g.setColor(boundsColor);
            g.drawRect(x,y,width,height);
        }
    }

    @Override
    boolean isAt(int x, int y) {
        return false;
    }

    @Override
    public void move(int x, int y) {
        for (Point point : points) {
            point.x += x;
            point.y += y;
        }
        this.updateBounds();
    }

    @Override
    public void moveTo(int destX, int destY) {
        int transX = destX - x;
        int transY = destY - y;
        for (Point point : points) {
            point.x += transX;
            point.y += transY;
        }
        this.updateBounds();
    }
}
