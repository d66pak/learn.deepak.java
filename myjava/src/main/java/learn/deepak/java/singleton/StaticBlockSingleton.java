package learn.deepak.java.singleton;

import java.io.Serializable;

/**
 * Class instance created in a static block
 *
 * Created by dtelkar on 7/31/14.
 */
public class StaticBlockSingleton implements Serializable {

    /**
     * Good to define serial version id else default will be generated
     * How to generate: compile the code then cd target/classes
     * serialver learn.deepak.java.singleton.StaticBlockSingleton
     */
    private static final long serialVersionUID = 1243863584623228589L;

    /**
     * Private instance variable
     */
    private static final StaticBlockSingleton INSTANCE;

    /**
     * Static block
     */
    static {

        INSTANCE = new StaticBlockSingleton();
    }

    /**
     * instance method
     */
    public static StaticBlockSingleton getInstance() {

        return INSTANCE;
    }

    /**
     * readResolve method to return same instance
     */
    private Object readResolve() {

        return INSTANCE;
    }

    /**
     * private constructor
     */
    private StaticBlockSingleton() {

    }

    // Member variables
    private int member_;

    public int getMember() {
        return member_;
    }
}
