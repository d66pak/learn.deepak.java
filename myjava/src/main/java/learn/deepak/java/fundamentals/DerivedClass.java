package learn.deepak.java.fundamentals;

/**
 * Created by dtelkar on 4/19/14.
 */
public class DerivedClass extends BaseClass {

    static {

        System.out.println("DerivedClass static initializer");
    }

    {
        System.out.println("DerivedClass initializer");
    }

    public void method1(int i, int j) {

        System.out.println("DerivedClass method1 : " + i + " " + j);
    }

    @Override
    public void method2(String s) {

        System.out.println("DerivedClass method2 : " + s);
    }

    public void methodD(int i) {

        System.out.println("DerivedClass methodD : " + i);
    }
}
