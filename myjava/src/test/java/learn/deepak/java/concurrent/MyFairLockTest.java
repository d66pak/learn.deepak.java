package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyFairLockTest {

    @Test
    public void testReentrantLocking() throws InterruptedException {

        MyFairLock lock = new MyFairLock();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            // ------------- Outer --------------
            System.out.println("Thread 1 OUTER : acquiring lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 OUTER : lock is acquired, entering critical section");

            // ------------- Inner --------------
            System.out.println("Thread 1 INNER : acquiring lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 INNER : lock is acquired, entering critical section");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // ------------- Inner --------------
            System.out.println("Thread 1 INNER : exiting critical section, releasing lock");
            lock.unlock();
            // ------------- Outer --------------
            System.out.println("Thread 1 OUTER : exiting critical section, releasing lock");
            lock.unlock();
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : trying to acquire lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 : lock is acquired, entering critical section");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 : exiting critical section, releasing lock");
            lock.unlock();

        });

        // Need to wait before closing
        Thread.sleep(30000);

        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testFairLocking() throws InterruptedException {

        MyFairLock lock = new MyFairLock();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.submit(() -> {
            System.out.println("Thread 1 : acquiring lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 : lock is acquired, entering critical section");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread 1 : exiting critical section, releasing lock");
            lock.unlock();
        });

        executor.submit(() -> {
            System.out.println("Thread 2 : trying to acquire lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 : lock is acquired, entering critical section");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 : exiting critical section, releasing lock");
            lock.unlock();

        });

        executor.submit(() -> {
            System.out.println("Thread 3 : trying to acquire lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 3 : lock is acquired, entering critical section");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 3 : exiting critical section, releasing lock");
            lock.unlock();

        });

        executor.submit(() -> {
            System.out.println("Thread 4 : trying to acquire lock...");
            try {
                lock.lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 4 : lock is acquired, entering critical section");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 4 : exiting critical section, releasing lock");
            lock.unlock();
        });

        // Need to wait before closing
        Thread.sleep(30000);

        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}