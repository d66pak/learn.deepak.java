package learn.deepak.java.fundamentals;

import java.io.*;

/**
 * There is not need to override any methods to support serializing
 * as members are primitive data types. Take this as example.
 *
 * Created by dtelkar on 4/20/14.
 */
public class MyColor implements Comparable<MyColor>, Serializable {

    private final float colorVal;
    private final double colorContext;

    private static final long serialVersionUID = -8339642853373568428L;

    public float getColorVal() {
        return colorVal;
    }

    public double getColorContext() {
        return colorContext;
    }

    public MyColor(float colorVal, double colorContext) {

        this.colorVal = colorVal;
        this.colorContext = colorContext;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof MyColor)) {
            return false;
        }

        MyColor c = (MyColor)o;


        /**
         * To compare float and double use compare
         */
        if (Float.compare(this.colorVal, c.colorVal) == 0 &&
                Double.compare(this.colorContext, c.colorContext) == 0) {

            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (colorVal != +0.0f ? Float.floatToIntBits(colorVal) : 0);
        temp = Double.doubleToLongBits(colorContext);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(MyColor myColor) {

        int ret = Float.compare(this.colorVal, myColor.colorVal);
        if ( ret != 0) return ret;

        ret = Double.compare(this.colorContext, myColor.colorContext);
        return ret;
    }

    @Override
    public String toString() {
        return "MyColor{" +
                "colorVal=" + colorVal +
                ", colorContext=" + colorContext +
                '}';
    }

    /**
     * Called to write the object's state
     */
    private void writeObject(ObjectOutputStream out) throws IOException {

        out.defaultWriteObject(); // Default mechanism to write objects non transient fields

        // nothing else to be written
    }

    /**
     * Called to read the object's state
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        in.defaultReadObject(); // Default mechanism to restore object's non-static, non-transient fields

        // readObject must check for invariants
        if (colorContext < 0 || colorContext < 0) {

            throw new InvalidObjectException("color context and value cannot be negative values");
        }
    }
}
