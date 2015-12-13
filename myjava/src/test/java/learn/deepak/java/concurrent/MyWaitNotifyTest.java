package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Test Wait and Notify
 */
public class MyWaitNotifyTest {

    private static class WaitRunnable implements Runnable {

        private final MyWaitNotify waitNotify;

        WaitRunnable(MyWaitNotify waitNotify) {

            this.waitNotify = waitNotify;
        }

        @Override
        public void run() {

            System.out.println("Thread 1, waiting for a signal from thread 2...");
            try {
                this.waitNotify.doWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1, received GO signal from thread 2");
        }
    }

    private static class SignalRunnable implements Runnable {

        private final MyWaitNotify waitNotify;

        public SignalRunnable(MyWaitNotify waitNotify) {

            this.waitNotify = waitNotify;
        }

        @Override
        public void run() {

            System.out.println("Thread 2, going to send GO signal...");
            this.waitNotify.doNotify();
        }
    }


    @Test
    public void testWaitNotify() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        MyWaitNotify waitNotify = new MyWaitNotify();

        WaitRunnable waitRunnable = new WaitRunnable(waitNotify);
        executor.submit(waitRunnable);

        Thread.sleep(5000);

        SignalRunnable signalRunnable = new SignalRunnable(waitNotify);
        executor.submit(signalRunnable);

        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}