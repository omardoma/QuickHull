package model;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance(Point p2) {
        return Math.sqrt((this.x - p2.x) * (this.x - p2.x)
                + (this.y - p2.y) * (this.y - p2.y));
    }

    /**
     * Calculates the distance of this point to the line which is formed by points a and b.
     *
     * @param a
     * @param b
     * @return The distance to the line.
     */
    public double getDistanceToLine(Point a, Point b) {
        return Math.abs((b.getX() - a.getX()) * (a.getY() - this.y) - (a.getX() - this.x) * (b.getY() - a.getY()))
                / Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    /**
     * Calculate the crossproduct of vectors origin->p2 and origin->this.
     *
     * @param origin The point in which both vectors originate
     * @param p2     The point that determines the second vector.
     * @return 0 if both points are collinear, a value > 0 if this point lies
     * left of vector origin->p2 (when standing in origin looking at p2), a
     * value < 0 if this point lies right of vector origin->p2.
     */
    private double calcCrossProduct(Point origin, Point p2) {
        return (p2.x - origin.x) * (this.y - origin.y)
                - (p2.y - origin.y) * (this.x - origin.x);
    }

    public boolean isLeftOfLine(Point from, Point to) {
        return Double.compare(calcCrossProduct(from, to), 0) > 0;
    }


    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}