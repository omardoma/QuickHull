package model;

import java.util.ArrayList;

public class QuickHull {
    private UpdateListener listener;

    public UpdateListener getListener() {
        return listener;
    }

    public void setListener(UpdateListener listener) {
        this.listener = listener;
    }

    /**
     * QuickHull is an algorithm to compute the convex hull of a set of points. The time complexity is O(n^2) in the worst
     * case and O(n*log n) on average.
     */
    public ArrayList<Point> executeQuickHull(ArrayList<Point> inputPoints) {
        if (inputPoints.isEmpty()) {
            throw new IllegalArgumentException("Cannot compute convex hull of zero points.");
        }
        ArrayList<Point> convexHull = new ArrayList<>();
        // search extreme values
        Point rightmostPoint = inputPoints.get(0);
        Point leftmostPoint = inputPoints.get(0);
        for (Point point : inputPoints) {
            if (point.getX() < rightmostPoint.getX()) {
                rightmostPoint = point;
            } else if (point.getX() > leftmostPoint.getX()) {
                leftmostPoint = point;
            }
        }
        // divide the set into two halves
        ArrayList<Point> leftOfLine = new ArrayList<>();
        ArrayList<Point> rightOfLine = new ArrayList<>();
        for (Point point : inputPoints) {
            if (point.equals(rightmostPoint) || point.equals(leftmostPoint)) {
                continue;
            }
            if (point.isLeftOfLine(leftmostPoint, rightmostPoint)) {
                leftOfLine.add(point);
            } else {
                rightOfLine.add(point);
            }
        }
        convexHull.add(leftmostPoint);
        notifyGUI(convexHull);
        ArrayList<Point> hull = divide(leftOfLine, leftmostPoint, rightmostPoint);
        //Show every update of points to the GUI
        for (Point p : hull) {
            convexHull.add(p);
            notifyGUI(convexHull);
        }
        convexHull.add(rightmostPoint);
        notifyGUI(convexHull);
        hull = divide(rightOfLine, rightmostPoint, leftmostPoint);
        //Show every update of points to the GUI
        for (Point p : hull) {
            convexHull.add(p);
            notifyGUI(convexHull);
        }
        return convexHull;
    }

    /**
     * Recursive implementation of QuickHull to find the furthest point to the line between the points p1
     * and p2 and divide the list of points. Caution: The points p1 and p2 must be in correct order so that
     * the points which are outside if the triangle furthest point - p1 - p2 are left of the viewing direction.
     * The viewing directions are as follow: p1 -> furthest point -> p2.
     */
    private ArrayList<Point> divide(ArrayList<Point> points, Point p1, Point p2) {
        ArrayList<Point> hull = new ArrayList<>();
        if (points.isEmpty()) {
            return hull;
        } else if (points.size() == 1) {
            hull.add(points.get(0));
            return hull;
        }
        Point maxDistancePoint = points.get(0);
        ArrayList<Point> l1 = new ArrayList<>();
        ArrayList<Point> l2 = new ArrayList<>();
        double distance = 0.0;
        for (Point point : points) {
            if (point.getDistanceToLine(p1, p2) > distance) {
                distance = point.getDistanceToLine(p1, p2);
                maxDistancePoint = point;
            }
        }
        points.remove(maxDistancePoint);
        for (Point point : points) {
            if (point.isLeftOfLine(p1, maxDistancePoint)) {
                l1.add(point);
            } else if (point.isLeftOfLine(maxDistancePoint, p2)) {
                l2.add(point);
            }
        }
        points.clear();
        ArrayList<Point> hullPart = divide(l1, p1, maxDistancePoint);
        hull.addAll(hullPart);
        hull.add(maxDistancePoint);
        hullPart = divide(l2, maxDistancePoint, p2);
        hull.addAll(hullPart);
        return hull;
    }

    private void notifyGUI(ArrayList<Point> points) {
        if (listener != null) {
            listener.updatePoints(points);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}