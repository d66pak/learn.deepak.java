package learn.deepak.java.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * Created by dtelkar on 5/18/14.
 */
public class MyBoundedHashMap {

    private final ConcurrentMap<Integer, String> mBoundedMap;
    private final Semaphore sem;

    public MyBoundedHashMap(int bound) {

        mBoundedMap = new ConcurrentHashMap<Integer, String>();
        sem = new Semaphore(bound);
    }

    public void put(int key, String value) throws InterruptedException {

        sem.acquire();
        mBoundedMap.put(key, value);
    }

    public String get(int key) {

        String value = mBoundedMap.get(key);
        sem.release();
        return value;
    }

    public int size() {

        return mBoundedMap.size();
    }

    public int permits() {

        return sem.availablePermits();
    }
}
