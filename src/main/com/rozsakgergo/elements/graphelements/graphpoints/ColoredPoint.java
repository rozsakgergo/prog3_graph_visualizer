package main.com.rozsakgergo.elements.graphelements.graphpoints;

import java.awt.*;

public class ColoredPoint extends GraphPoint {
    public ColoredPoint(String id, double x, double y) {
        super(id, x, y);
    }

    @Override
    public void drawShape(Graphics2D g2, int shape_size, int shape_x, int shape_y) {
        g2.setColor(new Color(28, 28, 28));
        g2.drawOval(shape_x, shape_y, shape_size, shape_size);
        g2.setColor(new Color(124, 232, 81));
        g2.fillOval(shape_x+1, shape_y+1, shape_size-1, shape_size-1);
    }
}
