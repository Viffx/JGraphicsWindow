package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class Oval extends Shape {
    public Oval(int x, int y, int width, int height, Color color, boolean filled) {
        super(x, y, width, height, color);
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        if (filled) {
            g.fillOval(x, y, width, height);
        } else {
            g.drawOval(x, y, width, height);
        }
        if (drawBounds) {
            g.setColor(boundsColor);
            g.drawRect(this.x,this.y,this.width,this.height);
        }
    }
    @Override
    boolean isAt(int x, int y) {
        double centerX = this.x + width / 2.0;
        double centerY = this.y + height / 2.0;

        double radiusX = width / 2.0;
        double radiusY = height / 2.0;

        double dx = x - centerX;
        double dy = y - centerY;

        return (dx * dx) / (radiusX * radiusX) + (dy * dy) / (radiusY * radiusY) <= 1;
    }
}
