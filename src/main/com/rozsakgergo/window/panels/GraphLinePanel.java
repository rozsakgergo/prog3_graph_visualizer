package main.com.rozsakgergo.window.panels;

import main.com.rozsakgergo.elements.data.GraphLineData;
import main.com.rozsakgergo.elements.data.GraphPointData;
import main.com.rozsakgergo.elements.data.LineTableModel;
import main.com.rozsakgergo.window.renderers.PointCellEditor;
import main.com.rozsakgergo.window.renderers.PointCellRenderer;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GraphLinePanel extends JPanel {
    private final LineTableModel tableModel;
    private final JTable table;
    private final GraphLineData graphLineData;
    private final GraphPointData graphPointData;
    private final PlottingPanel plottingPanel;
    private static final String[] TYPES = {
            "BlackLine",
            "ColoredLine"
    };

    public GraphLinePanel(GraphLineData graphLineData, GraphPointData graphPointData, PlottingPanel plottingPanel) {
        this.graphLineData = graphLineData;
        this.graphPointData = graphPointData;
        this.plottingPanel = plottingPanel;
        this.setBackground(Color.RED);
        setLayout(new BorderLayout());

        tableModel = new LineTableModel(graphLineData, graphPointData);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFocusable(false);

        tableModel.addTableModelListener(e -> plottingPanel.repaint());

        // === Type column editor as before ===
        JComboBox<String> typeBox = new JComboBox<>(TYPES);
        TableColumn typeCol = table.getColumnModel().getColumn(0);
        typeCol.setCellEditor(new DefaultCellEditor(typeBox));

        // === Start/End point columns ===
        int startColIndex = 1;
        int endColIndex = 2;

        // Renderer: show point ID
        PointCellRenderer pointRenderer = new PointCellRenderer();
        table.getColumnModel().getColumn(startColIndex).setCellRenderer(pointRenderer);
        table.getColumnModel().getColumn(endColIndex).setCellRenderer(pointRenderer);

        // Editors with filtering rules
        PointCellEditor startEditor = new PointCellEditor(
                table, graphPointData, graphLineData,
                true,  // isStartColumn
                startColIndex, endColIndex
        );
        PointCellEditor endEditor = new PointCellEditor(
                table, graphPointData, graphLineData,
                false, // isStartColumn
                startColIndex, endColIndex
        );

        table.getColumnModel().getColumn(startColIndex).setCellEditor(startEditor);
        table.getColumnModel().getColumn(endColIndex).setCellEditor(endEditor);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // === Buttons (unchanged) ===
        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton button_add = new JButton("Add Line");
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

        button_add.addActionListener(e -> {
            tableModel.addRow();
            plottingPanel.repaint();
        });

        button_remove.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
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
            for (int i = 0; i < tableModel.getRowCount(); i++) {
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

