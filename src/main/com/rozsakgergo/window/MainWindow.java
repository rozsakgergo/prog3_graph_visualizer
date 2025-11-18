package main.com.rozsakgergo.window;

import main.com.rozsakgergo.elements.data.*;
import main.com.rozsakgergo.window.bars.MenuBar;
import main.com.rozsakgergo.window.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Graph Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setJMenuBar(new MenuBar());

        ImageIcon image = new ImageIcon("src/main/resources/images/Logo.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(183, 209, 213));

        setLayout(new BorderLayout());

        GraphPointData graphPointData = new GraphPointData();
        GraphLineData graphLineData = new GraphLineData();

        PlottingPanel plottingPanel = new PlottingPanel(graphPointData);
        GraphPointPanel graphPointPanel = new GraphPointPanel(graphPointData, plottingPanel);
        GraphLinePanel graphLinePanel = new GraphLinePanel(graphLineData, graphPointData, plottingPanel);
        StatsPanel statsPanel = new StatsPanel(plottingPanel);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.add(plottingPanel, BorderLayout.CENTER);
        rightSide.add(statsPanel, BorderLayout.SOUTH);

        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphPointPanel, graphLinePanel);
        rightSplit.setResizeWeight(0.5);
        add(rightSplit, BorderLayout.CENTER);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rightSplit, rightSide);
        mainSplit.setResizeWeight(0.3);
        add(mainSplit, BorderLayout.CENTER);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);


        this.setVisible(true);
    }
}
