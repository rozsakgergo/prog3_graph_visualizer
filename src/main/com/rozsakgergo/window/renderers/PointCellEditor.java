package main.com.rozsakgergo.window.renderers;

import main.com.rozsakgergo.elements.data.GraphLineData;
import main.com.rozsakgergo.elements.data.GraphPointData;
import main.com.rozsakgergo.elements.graphelements.graphpoints.FilledPoint;
import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;
import main.com.rozsakgergo.elements.graphelements.lines.GraphLine;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

public class PointCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final JComboBox<GraphPoint> comboBox = new JComboBox<>();
    private final GraphPointData pointData;
    private final GraphLineData lineData;
    private final JTable table;
    private final boolean isStartColumn;
    private final int startColumnIndex;
    private final int endColumnIndex;

    public PointCellEditor(JTable table,
                           GraphPointData pointData,
                           GraphLineData lineData,
                           boolean isStartColumn,
                           int startColumnIndex,
                           int endColumnIndex) {
        this.table = table;
        this.pointData = pointData;
        this.lineData = lineData;
        this.isStartColumn = isStartColumn;
        this.startColumnIndex = startColumnIndex;
        this.endColumnIndex = endColumnIndex;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        comboBox.removeAllItems();
        comboBox.addItem(new FilledPoint("Null",0,0));

        GraphPoint otherEnd = null;
        if (isStartColumn) {
            Object v = table.getValueAt(row, endColumnIndex);
            if (v instanceof GraphPoint) {
                otherEnd = (GraphPoint) v;
            }
        } else {
            Object v = table.getValueAt(row, startColumnIndex);
            if (v instanceof GraphPoint) {
                otherEnd = (GraphPoint) v;
            }
        }

        for (GraphPoint candidate : pointData.getPoints()) {
            if (otherEnd != null && candidate == otherEnd) {
                continue;
            }

            if (otherEnd != null && wouldCreateParallel(candidate, otherEnd, row)) {
                continue;
            }

            comboBox.addItem(candidate);
        }

        if (value instanceof GraphPoint) {
            comboBox.setSelectedItem(value);
        }

        return comboBox;
    }

    private boolean wouldCreateParallel(GraphPoint a, GraphPoint b, int currentRow) {
        List<GraphLine> lines = lineData.getLines();
        for (int i = 0; i < lines.size(); i++) {
            if (i == currentRow) continue;
            GraphLine line = lines.get(i);
            GraphPoint s = line.getStart();
            GraphPoint e = line.getEnd();
            if (s == null || e == null) continue;

            // Check for parallel
            if ((s == a && e == b) || (s == b && e == a)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
}
