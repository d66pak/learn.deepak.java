package learn.deepak.java.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by dtelkar on 5/18/14.
 */
public class MyFutureTask {

    private final int mCalDurationInSeconds;

    /**
     * create future task
     */
    private final FutureTask<Integer> myFuture = new FutureTask<Integer>(new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {

            // do some lengthy operation
            return process();
        }
    });

    private final Thread mThread;

    public MyFutureTask(int calDurationInSeconds) {

        mCalDurationInSeconds = calDurationInSeconds;
        mThread = new Thread(myFuture);
    }

    /**
     * start thread with future task
     */
    public void start() {

        mThread.start();
    }

    /**
     * Get result from future task
     * can return :
     * 1. immediately if processing is finished (or)
     * 2. wait until the processing has completed
     * @return result
     */
    public int get() throws  InterruptedException {

        int ret = 0;
        try {

            ret = myFuture.get();

        } catch (ExecutionException e) {

            /*
             * Can be checked, unchecked or error
             * so, need to handle all
             */
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {

                System.out.println("Encountered unchecked exception : " + cause.getStackTrace());
            } else if (cause instanceof Error) {

                System.out.println("Encountered error : " + cause.getStackTrace());
            } else {

                System.out.println("Encountered checked exception : " + cause.getStackTrace());
            }
        }
        return ret;
    }

    /**
     * Method which performs some processing
     */
    private int process() throws InterruptedException {

        Thread.sleep(mCalDurationInSeconds * 1000);
        return 4;
    }
}
