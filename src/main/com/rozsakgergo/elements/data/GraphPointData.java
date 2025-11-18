package main.com.rozsakgergo.elements.data;

import main.com.rozsakgergo.elements.graphelements.graphpoints.*;

import java.util.*;

public class GraphPointData {
    private final List<GraphPoint> graphPoints = new ArrayList<>();
    private final Map<String, PointFactory> types = new LinkedHashMap<>();
    {
        types.put("FilledPoint", FilledPoint::new);
        types.put("ColoredPoint", ColoredPoint::new);
        types.put("SunnyPoint", SunnyPoint::new);
    }

    public List<GraphPoint> getPoints() {
        return Collections.unmodifiableList(graphPoints);
    }

    public int addNewPoint() {
        String id = indexToLabel(graphPoints.size());
        PointFactory f = types.get("FilledPoint"); // default
        graphPoints.add(f.create(id, 0, 0));
        return graphPoints.size() - 1;
    }

    public static String indexToLabel(int idx) {
        int n = idx + 1;
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int rem = (n - 1) % 26;
            sb.insert(0, (char) ('A' + rem));
            n = (n - 1) / 26;
        }
        return sb.toString();
    }

    public void removeAt(int index) {
        if (index >= 0 && index < graphPoints.size()) {
            graphPoints.remove(index);
        }
    }

    public GraphPoint findPoint(int index) {
        return graphPoints.get(index);
    }

    public void clear() {
        graphPoints.clear();
    }

    public void changeTypeAt(int row, String type_name) {
        GraphPoint old = graphPoints.get(row);
        PointFactory f = types.get(type_name);
        if (f == null) return;
        graphPoints.set(row, f.create(old.getId(), old.getX(), old.getY()));
    }

    public void updateXAt(int row, double new_x) {
        GraphPoint old = graphPoints.get(row);
        graphPoints.set(row, recreateSameType(old, new_x, old.getY()));
    }

    public void updateYAt(int row, double new_y) {
        GraphPoint old = graphPoints.get(row);
        graphPoints.set(row, recreateSameType(old, old.getX(), new_y));
    }

    private GraphPoint recreateSameType(GraphPoint old, double x, double y) {
        String typeName = old.getClass().getSimpleName();
        PointFactory f = types.getOrDefault(typeName, FilledPoint::new);
        return f.create(old.getId(), x, y);
    }
}
