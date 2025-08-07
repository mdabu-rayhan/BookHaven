package com.bookhaven.View;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomJButton extends JButton {

    private boolean hover = false;

    public CustomJButton(String text) {
        super(text);
        setPreferredSize(new Dimension(160, 40)); // Medium-large size
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Track hover state
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hover = true;
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient color scheme
        Color start = hover ? new Color(64, 224, 208) : new Color(28, 181, 224);
        Color end = hover ? new Color(20, 20, 80) : new Color(0, 0, 70);

        GradientPaint gradient = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);

        // Rounded rectangle
        Shape roundedRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setPaint(gradient);
        g2.fill(roundedRect);

        // Optional soft border/glow
        g2.setColor(new Color(255, 255, 255, 30));
        g2.draw(roundedRect);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }

    @Override
    public boolean contains(int x, int y) {
        Shape roundedRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
        return roundedRect.contains(x, y);
    }
}


