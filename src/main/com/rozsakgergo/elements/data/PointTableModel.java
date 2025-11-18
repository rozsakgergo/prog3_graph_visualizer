package main.com.rozsakgergo.elements.data;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

import javax.swing.table.AbstractTableModel;

public class PointTableModel extends AbstractTableModel {
    private final GraphPointData data;
    private final String[] column_names = {"Type", "ID", "X", "Y"};
    public PointTableModel(GraphPointData data) { this.data = data; }

    @Override
    public int getRowCount() {
        return data.getPoints().size();
    }

    @Override
    public String getColumnName(int column) {
        return column_names[column];
    }

    @Override
    public int getColumnCount() {
        return column_names.length;
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return switch (c) {
            case 0, 1 -> String.class;
            case 2, 3 -> Double.class;
            default -> Object.class;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0 || col == 2 || col == 3;
    }

    @Override
    public Object getValueAt(int row, int col) {
        GraphPoint p = data.getPoints().get(row);
        return switch (col) {
            case 0 -> p.getClass().getSimpleName();
            case 1 -> p.getId();
            case 2 -> p.getX();
            case 3 -> p.getY();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        switch (col) {
            case 0 -> data.changeTypeAt(row, (String) aValue);
            case 2 -> data.updateXAt(row, toDouble(aValue));
            case 3 -> data.updateYAt(row, toDouble(aValue));
        }
        fireTableRowsUpdated(row, row);
    }

    private double toDouble(Object v) {
        if (v instanceof Double i) return i;
        if (v instanceof Number n) return n.doubleValue();
        return Double.parseDouble(String.valueOf(v).trim());
    }

    public void addRow() {
        int new_index = data.addNewPoint();
        fireTableRowsInserted(new_index, new_index);
    }

    public void removeRow(int row) {
        data.removeAt(row);
        fireTableRowsDeleted(row, row);
    }

    public void removeAllRows() {
        int count = getRowCount();
        data.clear();
        if (count > 0)
            fireTableRowsDeleted(0, count - 1);
    }

    public GraphPointData getGraphPointData() {
        return data;
    }
}
