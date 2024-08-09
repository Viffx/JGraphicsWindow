package com.adrianlong.display.ShapeClasses;

import com.adrianlong.display.Display;

import java.awt.*;
import java.util.Arrays;

public abstract class Shape {
    int x, y, width, height, startAngle, arcAngle, arcWidth, arcHeight;

    Point[] points = new Point[0];

    boolean drawBounds = false;
    boolean filled = false;

    Color color;
    Color boundsColor = Color.WHITE;

    String text = "";

    Shape(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    Shape(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    Shape(int x, int y, int width, int height, int startAngle, int arcAngle, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcHeight = startAngle;
        this.arcAngle = arcAngle;
        this.arcWidth = arcAngle;
        this.color = color;
    }

    Shape(Color color, Point... points) {
        Rect bounds = Rect.bounds(points);
        x = bounds.rx;
        y = bounds.ry;
        width = bounds.rwidth;
        height = bounds.rheight;
        this.color = color;
        this.points = points;
    }

    public abstract void draw(Graphics g);
    abstract boolean isAt(int x, int y);

    public void moveTo(int destX, int destY) {
        x = destX;
        y = destY;
    };
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void updateBounds() {
        Rect bounds = Rect.bounds(points);
        x = bounds.rx;
        y = bounds.ry;
        width = bounds.rwidth;
        height = bounds.rheight;
    }
    public void toggleBounds() {
        drawBounds = !drawBounds;
    }
    public void setBoundsColor(Color color) {
        boundsColor = color;
    }


    public static Polygon toPolygon(Point... points) {
        int[] xPoints = new int[0];
        int[] yPoints = new int[0];
        for (Point point : points) {
            xPoints = Arrays.copyOf(xPoints,xPoints.length+1);
            xPoints[xPoints.length - 1] = point.x;
            yPoints = Arrays.copyOf(yPoints,yPoints.length+1);
            yPoints[yPoints.length - 1] = point.y;
        }
        return new Polygon(xPoints,yPoints,points.length);
    }
}
