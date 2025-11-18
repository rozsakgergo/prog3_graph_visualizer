package main.com.rozsakgergo.elements.graphelements.graphpoints;

@FunctionalInterface
public interface PointFactory {
    GraphPoint create(String id, double x, double y);
}
