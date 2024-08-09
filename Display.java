import com.adrianlong.display.ShapeClasses.*;
import com.adrianlong.display.ShapeClasses.Shape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Display {
    public JFrame frame;
    public List<com.adrianlong.display.ShapeClasses.Shape> shapes = new ArrayList<>();

    public Display(String name, int xLength, int yLength) {
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(xLength + 16,yLength + 39);
        frame.add(new PaintPanel());
        frame.setVisible(true);
    }

    // Swing stuff
    public void repaint() {frame.repaint();}

    // Shape handling
    public void moveShape(int id, int x, int y) {
        shapes.get(id).move(x,y);
        frame.repaint();
    }
    public void moveShapeTo(int id, int x, int y) {
        shapes.get(id).moveTo(x,y);
        frame.repaint();
    }
    public Shape get(int indx) {return shapes.get(indx);}
    public void erase(int indx) {
        shapes.remove(indx);
        frame.repaint();
    }
    public void eraseAll() {
        shapes = new ArrayList<>();
        frame.repaint();
    }

    // Shape painting
    public void drawRect(int x, int y, int width, int height, Color color,boolean filled) {
        shapes.add(new Rect.TwoDem(x,y,width,height,color,filled));
    }
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color,boolean filled) {
        shapes.add(new Rect.Round(x,y,width,height,arcWidth,arcHeight,color,filled));
    }
    public void drawOval(int x, int y, int width, int height, Color color,boolean filled) {
        shapes.add(new Oval(x,y,width,height,color,filled));
    }
    public void drawPolygon(Color color,boolean filled, Point... points) {
        shapes.add(new polygon(color,filled,points));
    }
    public void drawArc(int x, int y, int width, int height, int startAngle,int arcAngle, Color color, boolean filled) {
        shapes.add(new Arc(x, y, width, height,startAngle,arcAngle,color,filled));
    }
    public void drawLine(Color color, Point p1, Point p2) {
        shapes.add(new Line(color,p1,p2));
    }

    class PaintPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.setBackground(Color.BLACK);
            // draw all shapes
            try {
                for (Shape shape : shapes) {
                    shape.draw(g);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

}
