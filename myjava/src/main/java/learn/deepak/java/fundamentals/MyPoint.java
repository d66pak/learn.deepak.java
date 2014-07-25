package learn.deepak.java.fundamentals;

import java.io.Serializable;

/**
 * Default serialization should work fine as all members are primitive data types
 * Created by dtelkar on 4/20/14.
 */
public class MyPoint implements Comparable<MyPoint>, Serializable {

    private final int x;
    private final int y;
    private volatile int hashCode;

    /**
     * Good to define serial version id else default will be generated
     * How to generate:
     * serialver learn.deepak.java.fundamentals.MyPoint
     */
    private static final long serialVersionUID = -9178337669644135740L;


    MyPoint() {
        x = 0;
        y = 0;
        hashCode = 0;
    }

    MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
        hashCode = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {

        /**
         * 1. Check for self reference
         * Optional, just for performance improvement
         */
        if (o == this) {
            return true;
        }

        /**
         * 2. Check if the argument is of correct type
         * If o == null than instanceof will return false
         * so, no need to check in o == null
         */
        if (!(o instanceof MyPoint)) {
            return false;
        }

        /**
         * 3. Cast the argument to correct type
         * This cast will not throw because we have already used instanceof
         */
        MyPoint p = (MyPoint)o;

        /**
         * 4. Check all the member variables for equality
         * - For all primitive types use ==
         * - For float and double use Float.compare, Double.compare
         * - For obj references use equals recursively
         */
        if (this.x == p.x && this.y == p.y) {
            return true;
        }

        return false;
    }

    /**
     * Since MyPoint is immutable, hash code can be cached for efficiency
     * Lazy initialized hash code
     * @return
     */
    @Override
    public int hashCode() {

        if (hashCode == 0) {

            int result = 17; // some arbitrary value
            result = 31 * result + x;
            result = 31 * result + y;
            hashCode = result;
        }
        return hashCode;
    }

    /**
     * Throws ClassCastException if object passed is other than MyPoint
     * Throws NullPointerException if null is passed
     *
     * @param myPoint
     * @return
     */
    @Override
    public int compareTo(MyPoint myPoint) {

        /**
         * Compare all the member variables
         * - For all primitive types use ==, <, >
         * - For float and double use Float.compare, Double.compare
         * - For obj references use equals recursively
         */
        if (this.x > myPoint.x) return 1;
        if (this.x < myPoint.x) return -1;

        if (this.y > myPoint.y) return 1;
        if (this.y < myPoint.y) return -1;

        // all fields are equal
        return 0;
    }

    @Override
    public String toString() {
        return "MyPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}
