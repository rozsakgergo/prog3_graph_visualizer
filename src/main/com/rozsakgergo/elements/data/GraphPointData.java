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

    public int size() {
        return graphPoints.size();
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
        GraphPoint p = graphPoints.get(row);
        p.setX(new_x);
    }

    public void updateYAt(int row, double new_y) {
        GraphPoint p = graphPoints.get(row);
        p.setY(new_y);
    }
}
