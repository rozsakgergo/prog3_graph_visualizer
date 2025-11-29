package main.com.rozsakgergo.elements.graphelements.lines;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

import java.awt.*;
import java.io.IOException;

public abstract class GraphLine {
    private final String id;
    private final double len;
    public GraphPoint start, end;

    public GraphLine(String id, GraphPoint start, GraphPoint end) {
        this.id = id;
        this.start = start; this.end = end;
        this.len = 0;
    }

    public abstract void drawShape();

    public GraphPoint getStart() { return start; }
    public GraphPoint getEnd() { return end; }
    public String getId() { return id; }
}
