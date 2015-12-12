package learn.deepak.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by deepak on 11/12/15.
 */
public class IntrinsicLocks {

    private int _counter;

    public synchronized int increment() {

        System.out.println("Incrementing counter...");
        if (_counter == Integer.MAX_VALUE) {
            throw new IllegalStateException("Reached MAX integer limit");
        }
        _counter += 1;

        try {
            // Simulating long work
            System.out.println("Doing TIME TAKING WORK.....");
            Thread.sleep(5000);
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
