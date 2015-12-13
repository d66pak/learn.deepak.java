package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyReadWriteLockTest {

    @Test
    public void multipleReadTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {
            try {
                System.out.println("Thread 1 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 1 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void singleWriteTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {
            try {
                System.out.println("Thread 1 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 1 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void pendingWriteTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {
            try {
                System.out.println("Thread 1 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 1 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 3 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 3 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void ReadReadReentrantTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing READ lock...");
                rwl.unlockRead();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void ReadReadReentrantWriteTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing READ lock...");
                rwl.unlockRead();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void WriteWriteReentrantReadTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing WRITE lock...");
                rwl.unlockWrite();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void ReadToWriteReentrantReadTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
//                Thread.sleep(5000);
//                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing WRITE lock...");
                rwl.unlockWrite();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void ReadToWriteReentrantReadWrite() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
//                Thread.sleep(5000);
//                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing WRITE lock...");
                rwl.unlockWrite();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void WritToReadeReentrantReadTest() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
//                Thread.sleep(5000);
                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing READ lock...");
                rwl.unlockRead();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing READ lock...");
                rwl.unlockRead();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void WriteToReadReentrantReadWrite() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyReadWriteLock rwl = new MyReadWriteLock();

        executor.submit(() -> {

            try {
                // ------------- Outer --------------
                System.out.println("Thread 1 OUTER : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
//                Thread.sleep(5000);
                Thread.sleep(2000);
//                Thread.sleep(0);

                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : acquiring READ lock...");
                rwl.readLock();

                // Do some read related work
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ------------- Inner --------------
                System.out.println("Thread 1 INNER : releasing READ lock...");
                rwl.unlockRead();

                // ------------- OUTER --------------
                System.out.println("Thread 1 OUTER : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("Thread 2 : acquiring WRITE lock...");
                rwl.lockWrite();

                // Do some read related work
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Thread 2 : releasing WRITE lock...");
                rwl.unlockWrite();
            }
        });

        Thread.sleep(30000);
        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}