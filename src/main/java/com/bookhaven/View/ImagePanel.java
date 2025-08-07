package com.bookhaven.View;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that draws a background image, scaled to fill its entire area.
 */
public class ImagePanel extends JPanel {

    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        // Load the image from the resources folder
        try {
            // getClass().getResource() is the standard way to load resources from the classpath
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image: " + imagePath);
            e.printStackTrace();
            // Set a fallback color if the image fails to load
            setBackground(Color.DARK_GRAY);
        }
    }

    /**
     * This is the magic method. Swing calls this whenever the panel needs to be drawn.
     * We override it to draw our image first, then let Swing draw the children on top.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent's method first to handle standard background painting
        super.paintComponent(g);

        // Draw the background image, scaled to the panel's current size.
        // This makes the image responsive to window resizing.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}