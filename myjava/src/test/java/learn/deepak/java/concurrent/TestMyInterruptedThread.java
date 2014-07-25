package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/16/14.
 */
public class TestMyInterruptedThread {

    @Test
    public void noInterruptionTest() throws InterruptedException{

        Thread t1 = new Thread(new MyInterruptedThread());
        MyInterruptedThread.logMsg("About to start thread T1");
        t1.start();
        MyInterruptedThread.logMsg("Waiting for T1 to complete...");
        t1.join(); // required to wait for t1 till its finished
        MyInterruptedThread.logMsg("Closing main thread");
    }

    @Test
    public void interruptTest() throws InterruptedException {

        Thread t2 = new Thread(new MyInterruptedThread());
        MyInterruptedThread.logMsg("About to start thread T2");
        t2.start();
        long waitFor = 6000; // 6 secs
        long startTime = System.currentTimeMillis();
        while (t2.isAlive()) {

            MyInterruptedThread.logMsg("Waiting...");
            t2.join(1000); // wait for 1 sec
            if ((System.currentTimeMillis() - startTime) > waitFor) {

                MyInterruptedThread.logMsg("Enough waiting, interrupting T2...");
                t2.interrupt();
                MyInterruptedThread.logMsg("Waiting for T2 to exit...");
                t2.join();
                MyInterruptedThread.logMsg("Closing main thread");
            }
        }

    }
}
