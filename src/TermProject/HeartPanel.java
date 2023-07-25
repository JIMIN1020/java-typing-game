package TermProject;
import javax.swing.*;
import java.awt.*;

/* ------- StatusPanel에 있는 Life 하트를 그리는 패널 ------- */
public class HeartPanel extends JPanel
{
    public Color color;

    public HeartPanel() {
        color = Color.RED;
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(color);
        super.paintComponent(g);

        g.fillOval(0, 0, 15, 15);
        g.fillOval(12, 0, 15, 15);

        int[] xPos = {0, 27, 14};
        int[] yPos = {10, 10, 28};

        g.fillPolygon(xPos, yPos, 3);
    }
}