package learn.deepak.java.concurrent;

import java.util.Random;

/**
 * Created by dtelkar on 4/18/14.
 */
public class MyTransactionManager {

    private static final Random random = new Random();

    // This one will be set using set() method
    private static final ThreadLocal<Integer> context1 = new ThreadLocal<Integer>();

//    private static final ThreadLocal<Integer> context2 = new ThreadLocal<Character>() {
//
//        @Override
//        public Character initialValue() {
//
//            Integer i = random.nextInt();
//            return i.byteValue();
//        }
//    }

    public static void startTransaction() {

        context1.set(random.nextInt());
    }

    public static void endTransaction() {

        context1.remove();
    }

    public static int getTransactionId() {

        return context1.get();
    }
}
