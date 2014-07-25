package learn.deepak.java.fundamentals;

/**
 * Created by dtelkar on 4/19/14.
 */
public class BaseClass {

    static {
        System.out.println("BaseClass static initializer");
    }

    {
        System.out.println("BaseClass initializer");
    }

    public void method1(int i) {

        System.out.println("Base Class method1 : " + i);
    }

    public void method2(String s) {

        System.out.println("Base Class method2 : " + s);
    }
}
