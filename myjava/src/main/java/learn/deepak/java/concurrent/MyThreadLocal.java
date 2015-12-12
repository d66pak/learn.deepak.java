package learn.deepak.java.concurrent;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by deepak on 12/12/15.
 *
 * ThreadLocal implementation
 */
public class MyThreadLocal<T> {

    private static final class Value<T> {

        private final WeakReference<Thread> weakRef;

        private final T value;
        Value(T value, ReferenceQueue<Thread> refQueue) {
            weakRef = new WeakReference<>(Thread.currentThread(), refQueue);
            this.value = value;
        }

        public WeakReference<Thread> getWeakRef() {
            return weakRef;
        }

        public T getValue() {
            return value;
        }
    }

    private ConcurrentMap<Thread, Value<T>> _threadLocalMap = new ConcurrentHashMap<>();
    private ReferenceQueue<Thread> _deadKeys = new ReferenceQueue<>();

    public MyThreadLocal() {

        // Start purging thread
        purge();
    }

    protected T initialValue() {

        return null;
    }

    public T get() {

        Value<T> localValue = _threadLocalMap.get(Thread.currentThread());
        if (localValue == null) {
            localValue = new Value<>(initialValue(), _deadKeys);
            set(localValue);
        }
        return localValue.getValue();
    }

    public void set(T value) {

        Value<T> localValue = new Value<>(value, _deadKeys);
        _threadLocalMap.put(Thread.currentThread(), localValue);
    }

    private void set(Value<T> localValue) {

        _threadLocalMap.put(Thread.currentThread(), localValue);
    }

    public void remove() {

        _threadLocalMap.remove(Thread.currentThread());
    }

    private void purge() {

        Thread cleanupThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Waiting on reference queue...");
                    WeakReference deadRef = (WeakReference) _deadKeys.remove();
                    _threadLocalMap.remove(deadRef.get());
                    System.out.println("Removed : " + deadRef.get());
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting cleanup thread...");
            }
        });

        cleanupThread.start();
    }
}
