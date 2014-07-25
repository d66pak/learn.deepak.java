package learn.deepak.java.fundamentals;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_en;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Serialization proxy pattern
 *
 * Created by dtelkar on 4/20/14.
 */
public class MyColorPoint implements Comparable<MyColorPoint>, Serializable{

    private final MyColor color;
    private final MyPoint point;
    private static final long serialVersionUID = 7143057118764222753L;

    /**
     * inner class implementing comparator interface
     */
    private static class MyPointColorComparator implements Comparator<MyColorPoint> {

        @Override
        public int compare(MyColorPoint mcp1, MyColorPoint mcp2) {

            // Changed order, 1st compare point then color
            int ret = mcp1.point.compareTo(mcp2.point);
            if (ret != 0) return ret;

            ret = mcp1.color.compareTo(mcp2.color);
            return ret;
        }
    }

    /**
     * static comparator instance
     */
    public static final Comparator<MyColorPoint> POINT_COLOR_COMPARATOR = new MyPointColorComparator();

    /**
     * Serialization proxy inner class
     */
    private static class SerializationProxy implements Serializable {

        private final MyColor color;
        private final MyPoint point;
        private static final long serialVersionUID = -7840127624759828104L;

        SerializationProxy(MyColorPoint cp) {
            this.color = cp.color;
            this.point = cp.point;
        }

        /**
         * Replace with MyColorPoint object
         * @return
         */
        private Object readResolve() {

            System.out.println("readResolve() called");
            return new MyColorPoint(this.point, this.color);
        }
    }

    /**
     * Write replace - write serialization proxy instead of MyColorPoint
     */
    private Object writeReplace() {

        System.out.println("writeReplace() called");
        return new SerializationProxy(this);
    }

    /**
     * Disable readObject - MyColorPoint should not be read because we have
     * written SerializationProxy
     */
    private void readObject(ObjectInputStream in) throws InvalidObjectException {

        System.out.println("readObject() called");
        throw new InvalidObjectException("Proxy needed");
    }

    public MyColorPoint() {
        color = null;
        point = null;
    }

    public MyColorPoint(MyPoint point, MyColor color) {

        /**
         * color and point are immutable so, there is not need
         * to create copy and assign
         */
        this.color = color;
        this.point = point;
    }

    public MyColor getColor() {

        /**
         * color is immutable so, there is no need to return a copy
         */
        return color;
    }

    public MyPoint getPoint() {

        /**
         * point is immutable so, there is no need to return a copy
         */
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyColorPoint )) return false;

        MyColorPoint that = (MyColorPoint) o;

        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (point != null ? !point.equals(that.point) : that.point != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (point != null ? point.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(MyColorPoint myColorPoint) {

        int ret = color.compareTo(myColorPoint.color);
        if (ret != 0) return ret;

        ret = point.compareTo(myColorPoint.point);
        return ret;
    }

    @Override
    public String toString() {
        return "MyColorPoint{" +
                "color=" + color +
                ", point=" + point +
                '}';
    }
}
