package main.com.rozsakgergo.elements.graphelements.lines;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

import java.awt.*;
import java.io.IOException;

public abstract class GraphLine {
    private final String id;
    private double len;
    private GraphPoint start;
    private GraphPoint end;

    public GraphLine(String id, GraphPoint start, GraphPoint end) {
        this.id = id;
        this.start = start; this.end = end;
        this.len = 0;
    }

    public abstract void drawLine(Graphics2D g2, int sx, int sy, int ex, int ey);

    public GraphPoint getStart() { return start; }
    public GraphPoint getEnd() { return end; }
    public String getId() { return id; }

    public double getLen() { return len; }
    public void setLen(double val) { len = val;}

    public void setStart(GraphPoint start) { this.start = start; }
    public void setEnd(GraphPoint end) { this.end = end; }
}
