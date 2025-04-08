package types;

public class Coordinates implements Comparable<Coordinates> {
    private Float x; //Поле не может быть null
    private double y; //Максимальное значение поля: 2

    public Float getX() {
        return this.x;
    }

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

    public void setY(double y) {
        if (y <= 2) {
            this.y = y;
        }
    }

    @Override
    public String toString() {
        return "Coordinates :: [x=" + this.x + ", y=" + this.y + "] ;";
    }

    @Override
    public int compareTo(Coordinates o) {
        if (x.floatValue() == o.x.floatValue() && y == o.y) {
            return 0;
        }
        return Math.sqrt(x*x + y*y) < Math.sqrt(o.x * o.x + o.y * o.y) ? -1 : 1;
    }
}
