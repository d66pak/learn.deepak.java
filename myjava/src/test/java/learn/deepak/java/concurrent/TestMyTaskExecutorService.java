package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dtelkar on 5/20/14.
 */
public class TestMyTaskExecutorService {

    @Test
    public void testExecutorService() throws InterruptedException, ExecutionException {

        MyTaskExecutorService<String> tes = new MyTaskExecutorService<String>(10);

        tes.execute(new MyRunnable());
        Future res1 = tes.submit(new MySleepingRunnable());
        if (res1.get() != null) {

            System.out.println("MySleepingRunnable has failed!");
        }
        Future<String> res2 = tes.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "child task completed!";
            }
        });
        System.out.println("Child task returned: " + res2.get());

        List<Callable<String>> callableList1 = new ArrayList<Callable<String>>(5);
        for (int i = 0; i < 5; ++i) {

            final int id = i;
            callableList1.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "Child thread finished : " + id;
                }
            });
        }
        System.out.println("Result of any one of completed threads : " + tes.invokeAny(callableList1));

        List<Callable<String>> callableList2 = new ArrayList<Callable<String>>(5);
        for (int i = 0; i < 5; ++i) {

            final int id = i;
            callableList2.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "Child thread " + id + " finished";
                }
            });
        }
        List<Future<String>> allResults = tes.invokeAll(callableList2);

        for (Future<String> r : allResults) {

            System.out.println("Result : " + r.get());
        }

        // Very important
        tes.cleanup();
    }
}
