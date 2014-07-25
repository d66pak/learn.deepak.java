package learn.deepak.java.concurrent;

/**
 * Created by dtelkar on 4/17/14.
 *
 * Final class
 */
public final class MyImmutable {

    /**
     * Final members
     */
    private final int mOne;
    private final String mTwo;

    /**
     * Builder class
     */
    public static class Builder implements MyBuilder<MyImmutable> {

        /**
         * Same members as enclosing class but not final
         */
        private int bOne;
        private String bTwo;

        public Builder() {}

        /**
         * Method to set member variables
         */
        public Builder one(int i) {
            bOne = i;
            return this;
        }

        /**
         * Method to set member variables
         */
        public Builder two(String s) {
            bTwo = s;
            return this;
        }

        public MyImmutable build() {

            return new MyImmutable(this);
        }
    }

    /**
     * private constructor
     */
    private MyImmutable(Builder builder) {

        mOne = builder.bOne;
        mTwo = builder.bTwo;
    }
}
