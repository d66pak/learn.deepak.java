package learn.deepak.java.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by dtelkar on 5/19/14.
 */
public class MyTaskExecutor {

    private final int numThreads;

    private final Executor myExecutor;

    public MyTaskExecutor(int numThreads) {
        this.numThreads = numThreads;
        this.myExecutor = Executors.newFixedThreadPool(numThreads);
    }

    public void executeTask(Runnable runnable) {

        myExecutor.execute(runnable);
    }
}
