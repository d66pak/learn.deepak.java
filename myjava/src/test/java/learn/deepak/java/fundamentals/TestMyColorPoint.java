package learn.deepak.java.fundamentals;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

/**
 * Created by dtelkar on 4/22/14.
 */
public class TestMyColorPoint {

    @Test
    public void testEquals() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp2 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp3 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));

        // Reflexive
        Assert.assertTrue(cp1.equals(cp1));

        // Symmetric
        Assert.assertTrue(cp1.equals(cp2));
        Assert.assertTrue(cp2.equals(cp1));

        // Transitive
        Assert.assertTrue(cp1.equals(cp2));
        Assert.assertTrue(cp2.equals(cp3));
        Assert.assertTrue(cp1.equals(cp3));

        // Non-nullity
        Assert.assertFalse(cp1.equals(null));

        // Equal objects must have equal hashcodes
        Assert.assertEquals(cp1.hashCode(), cp2.hashCode());
        System.out.println("cp1 hashcode : " + cp1.hashCode());
    }

    @Test
    public void testNotEquals() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp2 = new MyColorPoint(new MyPoint(1, 3), new MyColor(1.2f, 1.3));
        MyColorPoint cp3 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.3f, 1.3));

        System.out.println("cp3 : " + cp3);

        Assert.assertFalse(cp1.equals(cp2));
        Assert.assertFalse(cp2.equals(cp1));

        Assert.assertFalse(cp1.equals(cp3));
        Assert.assertFalse(cp2.equals(cp3));
    }

    @Test
    public void testCompareTo() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp2 = new MyColorPoint(new MyPoint(1, 1), new MyColor(1.2f, 1.3));
        MyColorPoint cp3 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.1f, 1.3));
        MyColorPoint cp4 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.2));
        MyColorPoint cp5 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));

        // Reflexive
        Assert.assertEquals(cp1.compareTo(cp1), 0);

        // Symmetric
        Assert.assertEquals(cp1.compareTo(cp2), 1);
        Assert.assertEquals(cp2.compareTo(cp1), -1);

        // Transitive
        Assert.assertEquals(cp1.compareTo(cp2), 1);
        Assert.assertEquals(cp2.compareTo(cp3), 1);
        Assert.assertEquals(cp1.compareTo(cp3), 1);

        // equals and compareTo (Recommended but not compulsory
        Assert.assertEquals(cp1.equals(cp5), cp1.compareTo(cp5) == 0);

        Set<MyColorPoint> colorPtSet = new TreeSet<MyColorPoint>();
        colorPtSet.add(cp3);
        colorPtSet.add(cp2);
        colorPtSet.add(cp1);
        colorPtSet.add(cp4);
        colorPtSet.add(cp5);

        System.out.println(colorPtSet);

    }

    @Test
    public void testComparator() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp2 = new MyColorPoint(new MyPoint(1, 1), new MyColor(1.2f, 1.3));
        MyColorPoint cp3 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.1f, 1.3));
        MyColorPoint cp4 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.2));
        MyColorPoint cp5 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));

        List<MyColorPoint> colorPointList = new ArrayList<MyColorPoint>(5);
        colorPointList.add(cp1);
        colorPointList.add(cp2);
        colorPointList.add(cp3);
        colorPointList.add(cp4);
        colorPointList.add(cp5);
        Collections.sort(colorPointList, MyColorPoint.POINT_COLOR_COMPARATOR);

        System.out.println(colorPointList);
    }

    @Test
    public void testAnonymousComparator() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));
        MyColorPoint cp2 = new MyColorPoint(new MyPoint(1, 1), new MyColor(1.2f, 1.3));
        MyColorPoint cp3 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.1f, 1.3));
        MyColorPoint cp4 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.2));
        MyColorPoint cp5 = new MyColorPoint(new MyPoint(1, 2), new MyColor(1.2f, 1.3));

        List<MyColorPoint> colorPointList = new ArrayList<MyColorPoint>(5);
        colorPointList.add(cp1);
        colorPointList.add(cp2);
        colorPointList.add(cp3);
        colorPointList.add(cp4);
        colorPointList.add(cp5);
        Collections.sort(colorPointList, new Comparator<MyColorPoint>() {
            @Override
            public int compare(MyColorPoint mcp1, MyColorPoint mcp2) {

                // Changed order, 1st compare point then color
                int ret = mcp1.getPoint().compareTo(mcp2.getPoint());
                if (ret != 0) return ret;

                ret = mcp1.getColor().compareTo(mcp2.getColor());
                return ret;
            }
        });

        System.out.println(colorPointList);
    }

    @Test
    public void testPointSerialization() {

        MyPoint p = new MyPoint(1,2);

        // Serialize
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(p);
            out.close();
        } catch (IOException e) {

            Assert.fail("Serialization for MyPoint failed : ", e);
        }

        // De serialize
        ObjectInputStream in = null;
        MyPoint p1 = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            p1 = (MyPoint)in.readObject();
            in.close();
        } catch (IOException e) {

            Assert.fail("De serialization for MyPoint failed : ", e);
        } catch (ClassNotFoundException e) {

            Assert.fail("De serialization for MyPoint failed : ", e);
        }

        Assert.assertTrue(p.equals(p1));

    }

    private ByteArrayOutputStream serialize(MyColor c) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(c);
        } finally {
            if (out != null) out.close();
        }

        return bos;
    }

    private MyColor deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        MyColor c = null;
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            c = (MyColor) in.readObject();
        } finally {
            if (in != null) in.close();
        }

        return c;
    }

    @Test
    public void testColorSerialization() {

        MyColor c1 = new MyColor(1.1f, 1.2);

        // Serialize
        ByteArrayOutputStream bos = null;
        try {
            bos = serialize(c1);
        } catch (IOException e) {

            Assert.fail("Serialization for MyColor failed : ", e);
        }

        // Deserialize
        MyColor c2 = null;
        try {
            c2 = deserialize(bos.toByteArray());
        } catch (IOException e) {
            Assert.fail("De serialization for MyColor failed : ", e);
        } catch (ClassNotFoundException e) {
            Assert.fail("De serialization for MyColor failed : ", e);
        }

        Assert.assertTrue(c1.equals(c2));
    }

    @Test
    public void testColorPointSerialization() {

        MyColorPoint cp1 = new MyColorPoint(new MyPoint(1,2), new MyColor(1.1f, 1.2));

        // Serialize
        System.out.println("Serializing MyColorPoint...");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(cp1);
        } catch (IOException e) {

            Assert.fail("Serialization for MyColorPoint failed : ", e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // De serialize
        System.out.println("Deerializing MyColorPoint...");
        ObjectInputStream in = null;
        MyColorPoint cp2 = null;
        try {
            in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            cp2 = (MyColorPoint)in.readObject();
            in.close();
        } catch (IOException e) {

            Assert.fail("De serialization for MyColorPoint failed : ", e);
        } catch (ClassNotFoundException e) {

            Assert.fail("De serialization for MyColorPoint failed : ", e);
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Assert.assertTrue(cp1.equals(cp2));

    }
}
