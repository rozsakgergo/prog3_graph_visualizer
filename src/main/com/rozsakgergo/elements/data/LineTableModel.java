package main.com.rozsakgergo.elements.data;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;
import main.com.rozsakgergo.elements.graphelements.lines.GraphLine;

import javax.swing.table.AbstractTableModel;

public class LineTableModel extends AbstractTableModel {
    private final GraphLineData data;
    private final String[] column_names = {"Type", "ID", "Start", "End"};
    public LineTableModel(GraphLineData data) { this.data = data; }

    @Override
    public int getRowCount() {
        return data.getLines().size();
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
                return GraphPoint.class;
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
        GraphLine p = data.getLines().get(row);

        switch (col) {
            case 0:
                return p.getClass().getSimpleName();
            case 1:
                return p.getId();
            case 2:
                return p.getStart();
            case 3:
                return p.getEnd();
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
                data.updateXAt(row, (GraphPoint) aValue);
                break;
            case 3:
                data.updateYAt(row, (GraphPoint) aValue);
                break;
            default:
                break;
        }
        fireTableRowsUpdated(row, row);
    }


    public void addRow() {
        int new_index = data.addNewLine();
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

    public GraphLineData getGraphPointData() {
        return data;
    }
}
