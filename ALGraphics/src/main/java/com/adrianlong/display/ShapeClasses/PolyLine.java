package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class PolyLine extends Shape {
    PolyLine (Color color, Point... points) {
        super(color,points);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        if (filled) {
            g.fillPolygon(toPolygon(points));
        } else {
            g.drawPolygon(toPolygon(points));
        }
        Polygon polygon = toPolygon(points);
        g.drawPolyline(polygon.xpoints,polygon.ypoints,polygon.npoints);
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
