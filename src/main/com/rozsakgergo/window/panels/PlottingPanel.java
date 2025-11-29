package main.com.rozsakgergo.window.panels;

import main.com.rozsakgergo.elements.data.GraphLineData;
import main.com.rozsakgergo.elements.data.GraphPointData;
import main.com.rozsakgergo.elements.graphelements.graphpoints.GraphPoint;
import main.com.rozsakgergo.elements.graphelements.lines.GraphLine;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class PlottingPanel extends JPanel {
    public enum GRAPHPANEL_STATES {
        NORMAL,
        DETAILED;
    }
    private static int scale = 1;
    private final GraphPointData graphPointData;
    private final GraphLineData graphLineData;
    private GRAPHPANEL_STATES gp_state = GRAPHPANEL_STATES.NORMAL;

    public PlottingPanel(GraphPointData graphPointData, GraphLineData graphLineData) {
        this.setBackground(Color.WHITE);
        this.graphPointData = graphPointData;
        this.graphLineData = graphLineData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        paintGraphLines(g2);

        if(graphLineData.size() > 0 && graphPointData.size() >= 2) {
            for (GraphLine l : graphLineData.getLines()) {
                if(!l.getStart().getId().equals("Null") && !l.getEnd().getId().equals("Null")) {
                    paintLine(g2, l);
                    l.setLen(calculateDistance(l));
                }
            }
        }

        for (GraphPoint p : graphPointData.getPoints()) {
            paintPoint(g2, p);
        }

        if(gp_state == GRAPHPANEL_STATES.DETAILED) {
            drawPointDetails(g2);
        }
    }

    private double calculateDistance(GraphLine l) {
        double x1 = l.getStart().getX();
        double y1 = l.getStart().getY();
        double x2 = l.getEnd().getX();
        double y2 = l.getEnd().getY();

        double dx = x2 - x1;
        double dy = y2 - y1;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public void drawPointDetails(Graphics2D g2) {
        for (GraphPoint p : graphPointData.getPoints()) {
            int spacing = 30;
            double cx = getWidth() / 2.0;
            double cy = getHeight() / 2.0;

            double rx = p.getX() / scale;
            double ry = p.getY() / scale;

            int shape_x = (int) (rx * spacing + cx);
            int shape_y = (int) (-ry * spacing + cy);

            // A[0,0]
            DecimalFormat f = new DecimalFormat("0.#");
            String text = p.getId() + '[' + f.format(p.getX()) + ',' + f.format(p.getY()) + ']';

            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            // Outline
            g2.setColor(new Color(255, 255, 255));
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.drawString(text, shape_x-textWidth/2 + 1, shape_y-10 - 1);
            g2.drawString(text, shape_x-textWidth/2 + 1, shape_y-10 + 1);
            g2.drawString(text, shape_x-textWidth/2 - 1, shape_y-10 - 1);
            g2.drawString(text, shape_x-textWidth/2 - 1, shape_y-10 + 1);

            // White
            g2.setColor(new Color(0, 0, 0));
            g2.drawString(text, shape_x-textWidth/2, shape_y-10);
        }
    }

    public void incrementScale(int i) {
        scale = Math.max(1, scale + i);
        this.revalidate();
        this.repaint();
    }

    public void paintGraphLines(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        // SECONDARY LINES
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(162, 162, 162));

        // LEFT VERTICAL LINES
        for(int i = width/2 - 30; i > 0; i -= 30) {
            g2.drawLine(i, height, i, 0);
        }
        // RIGHT VERTICAL LINES
        for(int i = width/2 + 30; i < width; i += 30) {
            g2.drawLine(i, height, i, 0);
        }

        // TOP HORIZONTAL LINES
        for(int i = height/2 - 30; i > 0; i -= 30) {
            g2.drawLine(0, i, width, i);
        }
        // BOTTOM VERTICAL LINES
        for(int i = height/2 + 30; i < height; i += 30) {
            g2.drawLine(0, i, width, i);
        }

        // MAIN AXIS LINES
        g2.setColor(new Color(42, 42, 42));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(width/2, 0, width/2, height);
        g2.drawLine(0, height/2, width, height/2);

        // NUMBERS
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(getFont().deriveFont(Font.PLAIN, 12f));
        FontMetrics fm = g2.getFontMetrics();

        int increment = 5;
        int spacing = 30 * increment;
        int cx = width / 2;
        int cy = height / 2;

        // ---- X-axis labels ----
        for (int x = cx + spacing, i = 1; x < width; x += spacing, i++) {
            int value = i * increment * scale;
            String s = Integer.toString(value);
            int tw = fm.stringWidth(s);
            g2.drawString(s, x - tw / 2, cy + fm.getAscent() + 4);
        }

        for (int x = cx - spacing, i = -1; x >= 0; x -= spacing, i--) {
            int value = i * increment * scale;
            String s = Integer.toString(value);
            int tw = fm.stringWidth(s);
            g2.drawString(s, x - tw / 2, cy + fm.getAscent() + 4);
        }

        // ---- Y-axis labels ----
        for (int y = cy - spacing, i = 1; y >= 0; y -= spacing, i++) {
            int value = i * increment * scale;
            String s = Integer.toString(value);
            g2.drawString(s, cx + 6, y + fm.getAscent() / 2);
        }

        for (int y = cy + spacing, i = -1; y < height; y += spacing, i--) {
            int value = i * increment * scale;
            String s = Integer.toString(value);
            g2.drawString(s, cx + 6, y + fm.getAscent() / 2);
        }
    }

    public void paintPoint(Graphics2D g2, GraphPoint p) {
        int spacing = 30;
        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        double rx = p.getX() / scale;
        double ry = p.getY() / scale;

        // Size range: [3-10]
        double shape_scale_factor = 0.8;
        int shape_size = Math.min(Math.max(3, (int)(10 - scale * shape_scale_factor)), 10);

        int shape_x = (int) (rx * spacing + cx - shape_size / 2.0);
        int shape_y = (int) (-ry * spacing + cy - shape_size / 2.0);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            p.drawShape(g2, shape_size, shape_x, shape_y);
        } catch (IOException e) {
            System.err.println("Exception caught");
        }
    }

    public void paintLine(Graphics2D g2, GraphLine l) {
        if (l.getStart() == null || l.getEnd() == null) return;

        int spacing = 30;
        double cx = getWidth() / 2.0;
        double cy = getHeight() / 2.0;

        // Start
        double sx = (l.getStart().getX() / scale) * spacing + cx;
        double sy = -(l.getStart().getY() / scale) * spacing + cy;

        // End
        double ex = (l.getEnd().getX() / scale) * spacing + cx;
        double ey = -(l.getEnd().getY() / scale) * spacing + cy;

        double shape_scale_factor = 0.8;
        int shape_size = Math.min(Math.max(1, (int)(3 - scale * shape_scale_factor)), 3);
        g2.setStroke(new BasicStroke(shape_size));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        l.drawLine(g2, (int)Math.round(sx), (int)Math.round(sy), (int)Math.round(ex), (int)Math.round(ey));
    }


    public static int getScale() {
        return scale;
    }

    public void setGp_state(GRAPHPANEL_STATES gp) {
        this.gp_state = gp;
    }
}
