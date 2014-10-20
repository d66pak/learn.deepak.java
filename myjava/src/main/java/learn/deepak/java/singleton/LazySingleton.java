package learn.deepak.java.singleton;

import java.io.Serializable;

/**
 * Lazy initialization with double check locking
 *
 * Created by dtelkar on 7/31/14.
 */
public class LazySingleton implements Serializable {

    /**
     * Good to define serial version id else default will be generated
     * How to generate: compile the code then cd target/classes
     * serialver learn.deepak.java.singleton.LazySingleton
     */
    private static final long serialVersionUID = -379459704207142606L;

    /**
     * static volatile instance
     */
    private static volatile LazySingleton instance = null;

    /**
     * Static instance method with Double-Check-Locking pattern
     *
     * @return LazySingleton instance
     */
    public static LazySingleton getInstance() {

        if (instance == null) {

            // synchronize assess
            synchronized (LazySingleton.class) {

                // double check for null
                if (instance == null) {

                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    /**
     * readResolve to return same instance
     */
    private Object readResolve() {

        return instance;
    }

    /**
     * private constructor
     */
    private LazySingleton() {}

    // Private member variables
    private int member_;

    public int getMember() {
        return member_;
    }
}
