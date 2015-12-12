package learn.deepak.java.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by deepak on 8/12/15.
 */
public class MyThreadLocalExampleTest {

    private static class CallableTest implements Callable<String> {

        @Override
        public String call() throws Exception {
            return MyThreadLocalExample.getTransactionID();
        }
    }

    @Test
    public void testThreadLocal() throws ExecutionException, InterruptedException {

        ExecutorService testService = Executors.newFixedThreadPool(2);

        Future<String> result1 = testService.submit(new CallableTest());
        Future<String> result2 = testService.submit(new CallableTest());

        String transactionID1 = result1.get();
        String transactionID2 = result2.get();

        testService.shutdownNow();
        testService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("TransactionID 1 : " + transactionID1);
        System.out.println("TransactionID 2 : " + transactionID2);

        assertNotEquals(transactionID1, transactionID2);
    }

    @Test
    public void testThreadRemoved() throws InterruptedException {

        {
            Thread thread = new Thread(() -> {
                System.out.println("Fetching from thread local UUID : " + MyThreadLocalExample.getTransactionID());
            });
            thread.start();
        }

        Thread.sleep(60000);
    }
}