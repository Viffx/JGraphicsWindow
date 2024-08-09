package com.adrianlong.display.ShapeClasses;

import com.adrianlong.Array;

import java.awt.*;

public class Rect {
    int rx, ry, rwidth, rheight;
    public Rect(int x, int y, int width, int height) {
        this.rx = x;
        this.ry = y;
        this.rwidth = width;
        this.rheight = height;
    }

    public static class TwoDem extends Shape {
        public TwoDem(int x, int y, int width, int height, Color color, boolean filled) {
            super(x, y, width, height, color);
            this.filled = filled;
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            if (filled) {
                g.fillRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
        }
        @Override
        boolean isAt(int x, int y) {
            return Array.inBounds(this.x,this.y,this.x + this.width, this.y + this.height, x, y);
        }
    }
    public static class Round extends Shape {
        public Round(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color, boolean filled) {
            super (x,y,width,height,arcWidth,arcHeight,color);
            this.filled = filled;
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            if (filled) {
                g.fillRoundRect(x,y,width,height,arcWidth,arcHeight);
            } else {
                g.drawRoundRect(x,y,width,height,arcWidth,arcHeight);
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

    public static Rect bounds(Point... points) {
        int[] xs = new int[points.length];
        int[] ys = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xs[i] = points[i].x;
            ys[i] = points[i].y;
        }

        Point[] values = getMinMax(xs,ys);
        int min = 0, max = 1;
        int lenX, lenY;
        lenX = Math.abs(values[max].x - values[min].x);
        lenY = Math.abs(values[max].y - values[min].y);

        return new Rect(values[min].x,values[min].y,lenX,lenY);
    }
    public static Point[] getMinMax (int[] x, int[] y) {
        int minY = y[0], maxY = y[0], minX = x[0], maxX = x[0];
        for (int i = 1; i < x.length; i++) {
            if (minX > x[i]) {
                minX = x[i];
            }
            if (maxX < x[i]) {
                maxX = x[i];
            }
            if (minY > y[i]) {
                minY = y[i];
            }
            if (maxY < y[i]) {
                maxY = y[i];
            }
        }
//            System.out.printf("""
//                    top left:     (%d,%d)
//                    bottom right: (%d,%d)
//
//                    """,minX,minY,maxX,maxY);
        return new Point[] {
                new Point(minX,minY), new Point(maxX,maxY)
        };
    }
}
