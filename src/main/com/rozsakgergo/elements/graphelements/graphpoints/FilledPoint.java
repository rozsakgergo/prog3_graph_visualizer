package main.com.rozsakgergo.elements.graphelements.graphpoints;

import java.awt.*;

public class FilledPoint extends GraphPoint {

    public FilledPoint(String id, double x, double y) {
        super(id, x, y);
    }

    @Override
    public void drawShape(Graphics2D g2, int shape_size, int shape_x, int shape_y) {
        g2.setColor(new Color(42,42,42));
        g2.fillOval(shape_x, shape_y, shape_size, shape_size);
    }
}
