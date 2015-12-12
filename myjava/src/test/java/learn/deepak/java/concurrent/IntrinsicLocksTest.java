package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by deepak on 11/12/15.
 */
public class IntrinsicLocksTest {

    private static class IncrRunnable implements Runnable {

        private final IntrinsicLocks intrinsicLocks;

        IncrRunnable(IntrinsicLocks intrinsicLocks) {

            this.intrinsicLocks = intrinsicLocks;
        }

        @Override
        public void run() {

            System.out.println("Thread 1, incrementing...");
            int counter = this.intrinsicLocks.increment();
            System.out.println("Thread 1, Incremented to : " + counter);
        }
    }

    private static class ReadRunnable implements Runnable {

        private final IntrinsicLocks intrinsicLocks;

        ReadRunnable(IntrinsicLocks intrinsicLocks) {

            this.intrinsicLocks = intrinsicLocks;
        }

        @Override
        public void run() {

            System.out.println("Thread 2, reading...");
            // Will block while acquiring lock
            int counter = this.intrinsicLocks.getCounter();
            System.out.println("Thread 2, Counter value is : " + counter);
        }
    }

    @Test
    public void testIntrinsicLocks() throws InterruptedException {

        IntrinsicLocks il = new IntrinsicLocks();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        /**
         * 'il' is a local variable
         * It can used directly inside Runnable implementation
         */
        executor.submit(() -> {
            System.out.println("Thread 1, incrementing...");
            int counter = il.increment();
            System.out.println("Thread 1, Incremented to : " + counter);
        });

        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2, reading...");
                int counter = il.getCounter();
                System.out.println("Thread 2, Counter value is : " + counter);
            }
        });

        /**
         * 'il' can also be passed as a ctor param to Runnable impl
         */
        /*
        executor.submit(new IncrRunnable(il));
        executor.submit(new ReadRunnable(il));
        */

        // Wait before closing executor, else sleep() in increment() will throw interrupted exception
        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

}