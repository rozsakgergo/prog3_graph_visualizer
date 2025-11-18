package main.com.rozsakgergo.elements.graphelements.graphpoints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SunnyPoint extends GraphPoint {

    public SunnyPoint(String id, double x, double y) {
        super(id, x, y);
    }

    @Override
    public void drawShape(Graphics2D g2, int shape_size, int shape_x, int shape_y) {
        try {
            final BufferedImage image = ImageIO.read(new File("src/main/resources/images/Sun.png"));
            g2.drawImage(image, shape_x-5, shape_y-5, shape_size+10, shape_size+10, null);
        } catch (IOException e) {
            System.err.println("IOException caught! SunnyPoint");
        }

    }
}
