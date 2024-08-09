package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class polygon extends Shape {
    public polygon(Color color, boolean filled, Point... points) {
        super(color,points);
        this.filled = filled;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        if (filled) {
            g.fillPolygon(toPolygon(this.points));
        } else {
            g.drawPolygon(toPolygon(this.points));
        }
        if (drawBounds) {
            g.setColor(boundsColor);
            g.drawRect(this.x,this.y,this.width,this.height);
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
