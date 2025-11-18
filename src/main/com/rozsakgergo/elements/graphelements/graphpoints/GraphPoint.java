package main.com.rozsakgergo.elements.graphelements.graphpoints;

import java.awt.*;
import java.io.IOException;

public abstract class GraphPoint {
    private final String id;
    private final double abs_x;
    private final double abs_y;

    public GraphPoint(String id, double x, double y) {
        this.id = id;
        this.abs_x = x; this.abs_y = y;
    }

    public abstract void drawShape(Graphics2D g2, int shape_size, int shape_x, int shape_y) throws IOException;

    public double getX() { return abs_x; }
    public double getY() { return abs_y; }
    public String getId() { return id; }
}
