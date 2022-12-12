package logic;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d upperRight(Vector2d other) {
        Vector2d tmpVector = new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
        if(this.equals(tmpVector)){
            return this;
        }
        if(other.equals(tmpVector)){
            return other;
        }
        return tmpVector;
    }

    Vector2d lowerLeft(Vector2d other) {
        Vector2d tmpVector = new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
        if(this.equals(tmpVector)){
            return this;
        }
        if(other.equals(tmpVector)){
            return other;
        }
        return tmpVector;
    }

    Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2d vector2d = (Vector2d) other;
        return this.x == vector2d.x && this.y == vector2d.y;
    }

    Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
