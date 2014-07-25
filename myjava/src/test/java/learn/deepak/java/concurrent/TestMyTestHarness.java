package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/19/14.
 */
public class TestMyTestHarness {

    @Test
    public void testHarness() {

        try {
            long timeTaken = new MyTestHarness().timeTasks(4, new MySleepingRunnable());
            MyInterruptedThread.logMsg("Time taken : " + timeTaken/1E9 + " seconds");
        } catch (InterruptedException e) {
            System.out.println("Interrupted before calculatin time!");
        }
    }
}
