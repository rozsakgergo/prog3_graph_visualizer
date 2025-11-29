package main.com.rozsakgergo.elements.data;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;
import main.com.rozsakgergo.elements.graphelements.lines.BlackLine;
import main.com.rozsakgergo.elements.graphelements.lines.GraphLine;

import javax.swing.table.AbstractTableModel;

public class LineTableModel extends AbstractTableModel {

    private final GraphLineData lineData;
    private final GraphPointData pointData;
    private final String[] columnNames = {"Type", "Start", "End", "Len"};

    public LineTableModel(GraphLineData lineData, GraphPointData pointData) {
        this.lineData = lineData;
        this.pointData = pointData;
    }

    @Override
    public int getRowCount() {
        return lineData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;      // Type
            case 1:                          // Start
            case 2: return GraphPoint.class; // End
            case 3: return Double.class;     // Len
            default: return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1 || columnIndex == 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GraphLine line = lineData.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return line.getClass().getSimpleName();
            case 1:
                return line.getStart();
            case 2:
                return line.getEnd();
            case 3:
                return 0.0; // or compute from start/end if you want
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        GraphLine line = lineData.get(rowIndex);
        switch (columnIndex) {
            case 0:
                // change type if you have multiple line types
                break;
            case 1:
                if (aValue instanceof GraphPoint) {
                    lineData.setStartAt(rowIndex, (GraphPoint) aValue);
                }
                break;
            case 2:
                if (aValue instanceof GraphPoint) {
                    lineData.setEndAt(rowIndex, (GraphPoint) aValue);
                }
                break;
            default:
                break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    // === API used by panel buttons ===

    public void addRow() {
        // You probably have concrete line types, adjust as needed
        GraphPoint defaultStart = pointData.getPoints().isEmpty() ? null : pointData.getPoints().get(0);
        GraphPoint defaultEnd = defaultStart;

        GraphLine newLine = new BlackLine("L" + lineData.size(), defaultStart, defaultEnd);
        int newIndex = lineData.addNewLine(newLine);
        fireTableRowsInserted(newIndex, newIndex);
    }

    public void removeRow(int row) {
        lineData.removeAt(row);
        fireTableRowsDeleted(row, row);
    }

    public void removeAllRows() {
        int count = getRowCount();
        lineData.clear();
        if (count > 0)
            fireTableRowsDeleted(0, count - 1);
    }

    public GraphLineData getGraphLineData() {
        return lineData;
    }

    public GraphPointData getGraphPointData() {
        return pointData;
    }
}
