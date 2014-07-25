package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/18/14.
 */
public class TestMyTransactionManager {

    @Test(threadPoolSize = 3, invocationCount = 3)
    public void test1() {

        MyTransactionManager.startTransaction();

        MyInterruptedThread.logMsg("Transaction id : " + MyTransactionManager.getTransactionId());

        MyTransactionManager.endTransaction();
    }
}
