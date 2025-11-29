package main.com.rozsakgergo.elements.graphelements.lines;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

import java.awt.*;

public class BlackLine extends GraphLine {

    public BlackLine(String id, GraphPoint start, GraphPoint end) {
        super(id, start, end);
    }

    @Override
    public void drawLine(Graphics2D g2, int sx, int sy, int ex, int ey) {
        g2.setColor(new Color(0, 0, 0));
        g2.drawLine(sx, sy, ex, ey);
    }
}
