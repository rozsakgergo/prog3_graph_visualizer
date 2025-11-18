package main.com.rozsakgergo.elements.data;

import main.com.rozsakgergo.elements.graphelements.graphpoints.*;
import main.com.rozsakgergo.elements.graphelements.lines.*;

import java.util.*;

public class GraphLineData {
    private final List<GraphLine> graphLines = new ArrayList<>();
    private final Map<String, LineFactory> types = new LinkedHashMap<>();
    {
        types.put("BlackLine", BlackLine::new);
        types.put("ColoredLine", ColoredLine::new);
    }

    public List<GraphLine> getLines() {
        return Collections.unmodifiableList(graphLines);
    }

    public int addNewLine() {
        String id = indexToLabel(graphLines.size());
        LineFactory f = types.get("BlackLine"); // default
        graphLines.add(f.create(id, null, null));
        return graphLines.size() - 1;
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
        if (index >= 0 && index < graphLines.size()) {
            graphLines.remove(index);
        }
    }

    public void clear() {
        graphLines.clear();
    }

    public void changeTypeAt(int row, String type_name) {
        GraphLine old = graphLines.get(row);
        LineFactory f = types.get(type_name);
        if (f == null) return;
        graphLines.set(row, f.create(old.getId(), old.getStart(), old.getEnd()));
    }

    public void updateXAt(int row, GraphPoint new_start) {
        GraphLine old = graphLines.get(row);
        graphLines.set(row, recreateSameType(old, new_start, old.getStart()));
    }

    public void updateYAt(int row, GraphPoint new_end) {
        GraphLine old = graphLines.get(row);
        graphLines.set(row, recreateSameType(old, old.getStart(), new_end));
    }

    private GraphLine recreateSameType(GraphLine old, GraphPoint old_start, GraphPoint old_end) {
        String typeName = old.getClass().getSimpleName();
        LineFactory f = types.getOrDefault(typeName, BlackLine::new);
        return f.create(old.getId(), old_start, old_end);
    }
}
