package learn.deepak.java.concurrent;

import java.util.UUID;

/**
 * Created by deepak on 8/12/15.
 *
 * When get() is called thread id is associated with variable.
 * When thread dies, this variable is subjected to GC and thread entry is removed from ThreadLocal
 */
public final class ThreadLocalExample {

    private static final ThreadLocal<String> localTransactionID = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return UUID.randomUUID().toString();
        }
    };

    public static String getTransactionID() {
        return localTransactionID.get();
    }
}
