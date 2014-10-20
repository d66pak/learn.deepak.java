package learn.deepak.java.singleton;

import java.io.Serializable;

/**
 * Created by dtelkar on 8/9/14.
 */
public class DoubleCheckSingleton implements Serializable {

    /**
     * Good to define serial version id else default will be generated
     * How to generate: compile the code then cd target/classes
     * serialver learn.deepak.java.singleton.DoubleCheckSingleton
     */
    private static final long serialVersionUID = 3012321065835444299L;

    /**
     * static volatile instance
     */
    private static volatile DoubleCheckSingleton instance = null;

    /**
     * local variable result is used to ensure that instance is read only once in
     * the common case where it’s already initialized
     * 25 percent faster than the obvious version without a local variable
     *
     * Pg 307 - Effective Java 2nd Edition
     *
     * Same double check logic can be applied to return non static instance field as well
     */
    public static DoubleCheckSingleton getInstance() {

        DoubleCheckSingleton result = instance;

        if (result == null) {

            synchronized (DoubleCheckSingleton.class) {

                result = instance;
                if (result == null) {

                    result = instance = new DoubleCheckSingleton();
                }
            }
        }
        return result;
    }

    /**
     * readResolve to return same instance
     */
    protected Object readResolve() {

        return instance;
    }

    /**
     * private constructor
     */
    private DoubleCheckSingleton() {}

    // Private member variables
    private int mField;

}
