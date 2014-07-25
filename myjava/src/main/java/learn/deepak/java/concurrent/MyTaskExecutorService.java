package learn.deepak.java.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dtelkar on 5/19/14.
 */
public class MyTaskExecutorService<V> {

    private final int nThreads;
    private final ExecutorService myExecutorService;

    public MyTaskExecutorService(int nThreads) {
        this.nThreads = nThreads;
        this.myExecutorService = Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 1. Runnable
     */
    public void execute(Runnable runnable) {

        myExecutorService.execute(runnable);
    }

    /**
     * 2. Submit(Runnable)
     *
     * No result is returned since Runnable is used.
     * But Future.get() can be used to check how task has completed
     */
    public Future submit(Runnable runnable) {

        return myExecutorService.submit(runnable);
    }

    /**
     * 3. Submit(Callable)
     */
    public Future<V> submit(Callable<V> callable) {

        return myExecutorService.submit(callable);
    }

    /**
     * 4. invokeAny
     * returns the result of one of the threads that has completed successfully
     */
    public V invokeAny(Collection<? extends Callable<V>> tasks) {

        V result = null;
        try {
            result = myExecutorService.invokeAny(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 5. invokeAll
     * returns the result of all the threads
     */
    public List<Future<V>> invokeAll(Collection<? extends Callable<V>> tasks) {

        List<Future<V>> resultList = null;
        try {
            resultList = myExecutorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * shutdown executor service so that all threads are killed
     */
    public void cleanup() {

        myExecutorService.shutdown();
        try {
            myExecutorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
