package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by deepak on 11/12/15.
 */
public class IntrinsicLocksTest {

    @Test
    public void testIntrinsicLocks() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        IntrinsicLocks il = new IntrinsicLocks(latch);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            System.out.println("Calling increment...");
            int counter = il.increment();
            System.out.println("Incremented to : " + counter);
        });

        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Reading counter...");
                int counter = il.getCounter();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("Counter value is : " + counter);
            }
        });

        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

}