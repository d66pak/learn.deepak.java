package learn.deepak.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dtelkar on 4/18/14.
 */
public class MyTestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; ++i) {

            /**
             * Create n threads and stop them till all the threads are ready
             * using await() call
             */
            Thread t = new Thread() {

                @Override
                public void run() {

                    try {
                        startGate.await();
                    } catch (InterruptedException ignored) {}
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                }
            };
            t.start();
        }

        long startTime = System.nanoTime();
        MyInterruptedThread.logMsg("Opening the start gate!");
        // Open the startGate
        startGate.countDown();
        // Wait till all the threads have completed
        endGate.await();
        return System.nanoTime() - startTime;
    }

}
