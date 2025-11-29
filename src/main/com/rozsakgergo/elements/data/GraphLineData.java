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

    public int addNewLine(GraphLine line) {
        graphLines.add(line);
        return graphLines.size() - 1;
    }

    public void removeAt(int index) {
        if (index >= 0 && index < graphLines.size()) {
            graphLines.remove(index);
        }
    }

    public void clear() {
        graphLines.clear();
    }

    public GraphLine get(int index) {
        return graphLines.get(index);
    }

    public int size() {
        return graphLines.size();
    }

    public void setStartAt(int row, GraphPoint start) {
        GraphLine line = graphLines.get(row);
        line.setStart(start);
    }

    public void setEndAt(int row, GraphPoint end) {
        GraphLine line = graphLines.get(row);
        line.setEnd(end);
    }

    public void changeTypeAt(int row, String typeName) {
        GraphLine old = graphLines.get(row);
        LineFactory f = types.get(typeName);
        if (f == null) return;

        GraphLine replacement = f.create(old.getId(), old.getStart(), old.getEnd());
        replacement.setLen(old.getLen());
        graphLines.set(row, replacement);
    }

}
