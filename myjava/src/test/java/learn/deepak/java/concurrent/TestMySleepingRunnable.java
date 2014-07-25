package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/16/14.
 */
public class TestMySleepingRunnable {

    @Test
    public void testSleepingRunnable() throws InterruptedException {

        Thread t1 = new Thread(new MySleepingRunnable());

        t1.start();
        t1.join();
    }
}
