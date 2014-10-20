package learn.deepak.java.singleton;

import java.io.Serializable;

/**
 * Singleton class with eager initialization
 *
 * Created by dtelkar on 7/31/14.
 */
public class EagerSingleton implements Serializable {

    /**
     * Good to define serial version id else default will be generated
     * How to generate: compile the code then cd target/classes
     * serialver learn.deepak.java.singleton.EagerSingleton
     */
    private static final long serialVersionUID = 7870096991710479130L;

    /**
     * Eager instance variable initialization
     */
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private member variables
    private int member_;

    public int getMember() {

        return member_;
    }

    /**
     * private constructor
     */
    private EagerSingleton() {

        member_ = 10;
    }

    /**
     * instance method
     */
    public static EagerSingleton getInstance() {

        return INSTANCE;
    }

    /**
     * readResolve() method to stop generating duplicate copy after de-serializing
     */
    private Object readResolve() {

        return INSTANCE;
    }
}
