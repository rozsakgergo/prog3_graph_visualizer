package main.com.rozsakgergo.window.panels;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class StatsPanel extends JPanel {
    private final PlottingPanel graph;
    private boolean details_toggle = false;

    public StatsPanel(PlottingPanel graph) {
        this.graph = graph;
        this.setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 150));

        JLabel label = new JLabel();
        label.setText("Navigation Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);

        BasicArrowButton button_up = new BasicArrowButton(BasicArrowButton.NORTH);
        BasicArrowButton button_down = new BasicArrowButton(BasicArrowButton.SOUTH);
        BasicArrowButton button_left = new BasicArrowButton(BasicArrowButton.WEST);
        BasicArrowButton button_right = new BasicArrowButton(BasicArrowButton.EAST);

        Button button_center = new Button("O");
        Button button_scale_up = new Button("+");
        Button button_scale_down = new Button("-");

        button_up.setFocusable(false);
        button_down.setFocusable(false);
        button_left.setFocusable(false);
        button_right.setFocusable(false);
        button_center.setFocusable(false);
        button_scale_down.setFocusable(false);
        button_scale_up.setFocusable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        // row 0, col 1 (top)
        c.gridx = 1; c.gridy = 0;
        add(button_up, c);

        // row 1, col 0 (left)
        c.gridx = 0; c.gridy = 1;
        add(button_left, c);

        // row 1, col 1 (center)
        c.gridx = 1; c.gridy = 1;
        add(button_center, c);

        // row 1, col 2 (right)
        c.gridx = 2; c.gridy = 1;
        add(button_right, c);

        // row 2, col 1 (bottom)
        c.gridx = 1; c.gridy = 2;
        add(button_down, c);

        button_up.addActionListener(e -> System.out.println("Up"));
        button_down.addActionListener(e -> System.out.println("Down"));
        button_left.addActionListener(e -> System.out.println("Left"));
        button_right.addActionListener(e -> System.out.println("Right"));
        button_center.addActionListener(e -> System.out.println("Center"));
        button_scale_up.addActionListener(e -> {
            System.out.println("Action: Scale up");
            graph.incrementScale(-1);
        });
        button_scale_down.addActionListener(e -> {
            System.out.println("Action: Scale down");
            graph.incrementScale(1);
        });

        this.add(button_scale_up);
        this.add(button_scale_down);
        this.add(label);

        Button button_details = new Button("Toggle Details");
        button_details.setFocusable(false);

        button_details.addActionListener(e -> {
            if(details_toggle == false) {
                details_toggle = true;
                graph.setGp_state(PlottingPanel.GRAPHPANEL_STATES.DETAILED);
                graph.repaint();
            } else if(details_toggle) {
                details_toggle = false;
                graph.setGp_state(PlottingPanel.GRAPHPANEL_STATES.NORMAL);
                graph.repaint();
            }
        });

        this.add(button_details);

    }
}
