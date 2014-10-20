package learn.deepak.java.singleton;

import java.io.Serializable;

/**
 * Initialization on demand holder idiom
 *
 * Created by dtelkar on 7/31/14.
 */
public class BillPughSingleton implements Serializable {

    /**
     * Good to define serial version id else default will be generated
     * How to generate: compile the code then cd target/classes
     * serialver learn.deepak.java.singleton.BillPughSingleton
     */
    private static final long serialVersionUID = 5000411988878819073L;

    /**
     * Instance holder class
     */
    private static class LazyHolder {

        /**
         * static instance
         */
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    /**
     * instance method
     */
    public static BillPughSingleton getInstance() {

        return LazyHolder.INSTANCE;
    }

    /**
     * readResolve class to return same instance
     */
    private Object readResolve() {

        return LazyHolder.INSTANCE;
    }

    /**
     * private constructor
     */
    private BillPughSingleton() {
    }

    // member variables
    private int member_;

    public int getMember() {
        return member_;
    }
}
