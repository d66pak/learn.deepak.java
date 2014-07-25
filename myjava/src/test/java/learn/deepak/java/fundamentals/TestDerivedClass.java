package learn.deepak.java.fundamentals;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/19/14.
 */
public class TestDerivedClass {

    @Test(enabled = false)
    public void testDerived01() {

        System.out.println("----- testDerived01 -----");
        BaseClass b = new BaseClass();
        DerivedClass d = new DerivedClass();

        BaseClass bRef = new DerivedClass();

        b.method1(1);
//        b.method1(1,2);
        b.method2("B");
//        b.methodD(2);

        d.method1(2);
        d.method1(2,1);
        d.method2("D");
        d.methodD(3);

        bRef.method1(3);
//        bRef.method1(3,1);
        bRef.method2("BD");
//        bRef.methodD(4);
    }

    @Test(enabled = false)
    public void initOrder01() {

        System.out.println("----- initOrder01 -----");
        DerivedClass d = new DerivedClass();
    }

    @Test(enabled = true)
    public void initOrder02() {

        System.out.println("----- initOrder03 -----");
        BaseClass b = new DerivedClass();
    }
}
