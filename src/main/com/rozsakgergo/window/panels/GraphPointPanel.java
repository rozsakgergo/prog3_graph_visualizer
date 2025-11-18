package main.com.rozsakgergo.window.panels;

import main.com.rozsakgergo.elements.data.GraphPointData;
import main.com.rozsakgergo.elements.data.PointTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GraphPointPanel extends JPanel {
    private final PointTableModel tableModel;
    private final JTable table;
    private final GraphPointData graphPointData;
    private final PlottingPanel plottingPanel;
    private static final String[] TYPES = {
            "FilledPoint",
            "ColoredPoint",
            "SunnyPoint"
    };

    public GraphPointPanel(GraphPointData graphPointData, PlottingPanel plottingPanel) {
        this.graphPointData = graphPointData;
        this.plottingPanel = plottingPanel;
        this.setBackground(Color.RED);
        setLayout(new BorderLayout());

        tableModel = new PointTableModel(graphPointData);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFocusable(false);

        tableModel.addTableModelListener(e -> {
            plottingPanel.repaint();
        });

        //JComboBox<String> typeBox = new JComboBox<>(graphPointData.getTypeNames().toArray(new String[0]));
        JComboBox<String> typeBox = new JComboBox<>(TYPES);
        TableColumn typeCol = table.getColumnModel().getColumn(0);
        typeCol.setCellEditor(new DefaultCellEditor(typeBox));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Buttons ===
        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton button_add = new JButton("Add Point");
        JButton button_remove = new JButton("Remove Selected");
        JButton button_removeall = new JButton("Remove All");
        JButton button_print = new JButton("Print Data");

        button_add.setFocusable(false);
        button_remove.setFocusable(false);
        button_removeall.setFocusable(false);
        button_print.setFocusable(false);

        button_panel.add(button_add);
        button_panel.add(button_remove);
        button_panel.add(button_removeall);
        button_panel.add(button_print);
        add(button_panel, BorderLayout.SOUTH);

        // === Button Actions ===
        button_add.addActionListener(e -> {
            tableModel.addRow();
            plottingPanel.repaint();
        });

        button_remove.addActionListener(e -> {
            int r = table.getSelectedRow();
            if(r >= 0) {
                tableModel.removeRow(r);
                plottingPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "No row selected!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        button_removeall.addActionListener(e -> {
            tableModel.removeAllRows();
            plottingPanel.repaint();
        });
        button_print.addActionListener(e -> {
            System.out.println("tableModel elements:");
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                System.out.print(tableModel.getValueAt(i, 0));
                System.out.print(" ");
                System.out.print(tableModel.getValueAt(i, 1));
                System.out.print(" ");
                System.out.print(tableModel.getValueAt(i, 2));
                System.out.print(" ");
                System.out.print(tableModel.getValueAt(i, 3));
                System.out.print("\n");
            }
        });
    }
}
