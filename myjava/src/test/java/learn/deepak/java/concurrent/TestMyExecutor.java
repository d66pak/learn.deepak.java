package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 5/19/14.
 */
public class TestMyExecutor {

    @Test
    public void testMyExecutor() throws InterruptedException {

        MyTaskExecutor executor = new MyTaskExecutor(2);

        executor.executeTask(new MyRunnable());
        executor.executeTask(new MySleepingRunnable());
        executor.executeTask(new MyRunnable());

        /*
        NOTE:
        TO stop main thread from exiting before the executor threads have
        completed use ExecutorService as it provides shutdown and await methods
         */
    }
}
