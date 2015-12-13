package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MySemaphoreTest {

    @Test
    public void testBoundedSemaphore1() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MySemaphore sem = new MySemaphore(2);

        executor.submit(() -> {
            System.out.println("Thread 1 : acquiring semaphore..");
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 1 : releasing semaphore");
            sem.release();
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : acquiring semaphore..");
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 2 : releasing semaphore");
            sem.release();
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Test
    public void testBoundedSemaphore2() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MySemaphore sem = new MySemaphore(1);

        executor.submit(() -> {
            System.out.println("Thread 1 : acquiring semaphore..");
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 1 : releasing semaphore");
            sem.release();
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : acquiring semaphore..");
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 2 : releasing semaphore");
            sem.release();
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Test
    public void testAcquireMultiplePermits() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MySemaphore sem = new MySemaphore(2);

        executor.submit(() -> {
            System.out.println("Thread 1 : acquiring 2 permits from semaphore..");
            try {
                sem.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 1 : releasing 1st permit to semaphore");
            sem.release();

            // Doing some work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing 2nd permit
            System.out.println("Thread 1 : releasing 2nd permit to semaphore");
            sem.release();
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : acquiring semaphore..");
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 2 : releasing semaphore");
            sem.release();
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Test
    public void testMultipleAcquiresMultipleReleases() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MySemaphore sem = new MySemaphore(2);

        executor.submit(() -> {
            System.out.println("Thread 1 : acquiring 2 permits from semaphore..");
            try {
                sem.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 1 : releasing 2 permits to semaphore");
            sem.release(2);
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : acquiring 2 permits from semaphore..");
            try {
                sem.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Doing some work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Releasing
            System.out.println("Thread 2 : releasing 2 permits to semaphore");
            sem.release(2);
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

}