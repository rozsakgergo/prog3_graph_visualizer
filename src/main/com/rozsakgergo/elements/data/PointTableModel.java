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
        switch (c) {
            case 0:
            case 1:
                return String.class;
            case 2:
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0 || col == 2 || col == 3;
    }

    @Override
    public Object getValueAt(int row, int col) {
        GraphPoint p = data.getPoints().get(row);
        switch (col) {
            case 0:
                return p.getClass().getSimpleName();
            case 1:
                return p.getId();
            case 2:
                return p.getX();
            case 3:
                return p.getY();
            default:
                return null;
        }
    }
    @Override
    public void setValueAt(Object aValue, int row, int col) {
        switch (col) {
            case 0:
                data.changeTypeAt(row, (String) aValue);
                break;
            case 2:
                data.updateXAt(row, toDouble(aValue));
                break;
            case 3:
                data.updateYAt(row, toDouble(aValue));
                break;
            default:
                break;
        }

        fireTableRowsUpdated(row, row);
    }

    private double toDouble(Object v) {
        if (v instanceof Number) {
            return ((Number) v).doubleValue();
        }
        String s = String.valueOf(v).trim();
        return Double.parseDouble(s);
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
