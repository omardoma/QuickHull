package view;

import model.Point;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PointPanel extends JPanel {

    private static final int POINT_DIAMETER = 5;
    private static final int MARGIN = 10;

    private double scale;
    private int offsetX;
    private int offsetY;

    private boolean drawLines;

    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> allPoints = new ArrayList<>();

    public PointPanel() {
        super();
        this.setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            int x1 = (int) (offsetX + p.getX() * scale);
            int y1 = (int) (offsetY + p.getY() * scale);
            g2d.fillOval(x1 - POINT_DIAMETER / 2, y1 - POINT_DIAMETER / 2,
                    POINT_DIAMETER, POINT_DIAMETER);

            if (drawLines) {
                model.Point p2 = points.get((i + 1) % points.size());
                int x2 = (int) (offsetX + p2.getX() * scale);
                int y2 = (int) (offsetY + p2.getY() * scale);
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        g2d.setColor(Color.blue);
        for (int i = 0; i < allPoints.size(); i++) {
            model.Point p = allPoints.get(i);
            int x1 = (int) (offsetX + p.getX() * scale);
            int y1 = (int) (offsetY + p.getY() * scale);
            g2d.fillOval(x1 - POINT_DIAMETER / 2, y1 - POINT_DIAMETER / 2,
                    POINT_DIAMETER, POINT_DIAMETER);
        }
    }

    public void addPoints(ArrayList<Point> p) {
        this.points.addAll(p);
        this.drawLines = false;
        repaint();
    }

    public void savePoints(ArrayList<Point> p) {
        this.allPoints.clear();
        this.allPoints.addAll(p);
        repaint();
    }

    public void clearPoints() {
        this.points.clear();
        this.drawLines = false;
        repaint();
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public void drawLines() {
        this.drawLines = true;
    }

    @Override
    public void doLayout() {
        super.doLayout();
        int width = getWidth();
        int height = getHeight();
        if (getWidth() < getHeight()) {
            this.offsetX = MARGIN;
            this.offsetY = (height - width) / 2 + MARGIN;
            this.scale = width - 2 * MARGIN;
        } else {
            this.offsetX = (width - height) / 2 + MARGIN;
            this.offsetY = MARGIN;
            this.scale = height - 2 * MARGIN;
        }
    }

}