package learn.deepak.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by deepak on 11/12/15.
 */
public class IntrinsicLocks {

    private final CountDownLatch _latch;
    private int _counter;

    public IntrinsicLocks(CountDownLatch latch) {
        _latch = latch;
    }

    public synchronized int increment() {

        System.out.println("Incrementing counter...");
        if (_counter == Integer.MAX_VALUE) {
            throw new IllegalStateException("Reached MAX integer limit");
        }
        _counter += 1;

        try {
            _latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return _counter;
    }

    public synchronized int getCounter() {

        System.out.println("Returning counter...");
        return _counter;
    }
}
