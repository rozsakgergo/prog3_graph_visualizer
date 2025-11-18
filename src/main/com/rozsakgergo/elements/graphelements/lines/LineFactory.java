package main.com.rozsakgergo.elements.graphelements.lines;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

@FunctionalInterface
public interface LineFactory {
    GraphLine create(String id, GraphPoint start, GraphPoint end);
}
