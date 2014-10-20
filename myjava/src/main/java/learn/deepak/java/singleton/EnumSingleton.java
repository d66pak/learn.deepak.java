package learn.deepak.java.singleton;

/**
 * Enum singleton with implicit thread safety and serialization support
 *
 * Created by dtelkar on 7/31/14.
 */
public enum EnumSingleton {

    INSTANCE;

    private int member_;

    public int getMember() {
        return member_;
    }

    /**
     * Constructor
     */
    EnumSingleton() {

        member_ = 10;
    }
}
