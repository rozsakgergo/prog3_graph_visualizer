package main.com.rozsakgergo.window.renderers;

import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PointCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        if (value instanceof GraphPoint) {
            value = ((GraphPoint) value).getId();
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}