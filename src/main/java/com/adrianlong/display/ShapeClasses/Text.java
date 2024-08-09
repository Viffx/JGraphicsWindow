package com.adrianlong.display.ShapeClasses;

import java.awt.*;

public class Text extends Shape {
    public Text(int x, int y, String text, Color color) {
        super(x,y+12,color);
        this.text = text;
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text,x,y);
    }

    @Override
    boolean isAt(int x, int y) {
        return x == this.x && y == this.y;
    }
}
