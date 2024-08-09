package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class Arc extends Shape {
    public Arc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color, boolean filled) {
        super(x, y, width, height,startAngle,arcAngle, color);
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if (filled) {
            g.fillArc(x, y, width, height,startAngle,arcAngle);
        } else {
            g.drawArc(x, y, width, height,startAngle,arcAngle);
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
}
