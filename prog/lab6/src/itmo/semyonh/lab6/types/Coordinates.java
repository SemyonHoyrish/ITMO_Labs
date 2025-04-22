package itmo.semyonh.lab6.types;

/**
 * Represent data of Coordinates type.
 */
public class Coordinates implements Comparable<Coordinates> {
    private Float x; //Поле не может быть null
    private double y; //Максимальное значение поля: 2

    public Float getX() {
        return this.x;
    }

    /**
     * Sets value that is not null.
     *
     * @param x value
     */
    public void setX(Float x) {
        if (x != null) {
            this.x = x;
        } else {
            throw new IllegalArgumentException("x cannot be null");
        }
    }

    public double getY() {
        return y;
    }

    /**
     * Sets value that is not greater than 2.0
     *
     * @param y value
     */
    public void setY(double y) {
        if (y <= 2) {
            this.y = y;
        }
    }

    /**
     * String representation of the data.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Coordinates :: [x=" + this.x + ", y=" + this.y + "] ;";
    }

    /**
     * Compares objects based on distance from (0,0)
     *
     * @param o the object to be compared.
     * @return value as required for Comparable interface
     */
    @Override
    public int compareTo(Coordinates o) {
        if (x.floatValue() == o.x.floatValue() && y == o.y) {
            return 0;
        }
        return Math.sqrt(x*x + y*y) < Math.sqrt(o.x * o.x + o.y * o.y) ? -1 : 1;
    }
}
