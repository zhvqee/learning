package com.qee.threads;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @ProjectName: learning
 * @Package: com.qee.threads
 * @ClassName: Thread07  通过 LockSupport 自实现 简单future
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread07 extends ThreadBase {
    interface Promise<T> {
        void add(T value);

        void completed();
    }


    static class MaxSizeDefaultFuture implements Future<List<String>>, Promise<String> {

        private final List<String> result = new ArrayList<>();

        private volatile int state = State.NEW.ordinal();

        private static Unsafe unsafe;

        private static long stateOffset;

        private int size;

        private Object value = new Object();

        private ConcurrentHashMap<Thread, Object> threadsMap = new ConcurrentHashMap<>();


        static {
            try {
                Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeField.setAccessible(true);
                unsafe = (Unsafe) theUnsafeField.get(null);
                stateOffset = unsafe.objectFieldOffset(MaxSizeDefaultFuture.class.getDeclaredField("state"));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }


        enum State {
            NEW,
            CANCEL,
            RUNNING,
            INTERRUPTING,
            COMPLETED
        }

        public MaxSizeDefaultFuture(int size) {
            this.size = size;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if (!(state == State.NEW.ordinal() && unsafe.compareAndSwapInt(this, stateOffset, State.NEW.ordinal(),
                    mayInterruptIfRunning ? State.INTERRUPTING.ordinal() : State.CANCEL.ordinal())))
                return false;
            if (mayInterruptIfRunning) {
                try {
                    Thread.currentThread().interrupt();
                } finally { // final state
                    unsafe.putOrderedInt(this, stateOffset, State.INTERRUPTING.ordinal());
                }
            }
            return true;
        }

        @Override
        public boolean isCancelled() {
            return state == State.CANCEL.ordinal();
        }

        @Override
        public boolean isDone() {
            return state == State.COMPLETED.ordinal();
        }

        @Override
        public List<String> get() throws InterruptedException, ExecutionException {
            if (isDone()) {
                return result;
            }
            while (!isDone()) {
                threadsMap.put(Thread.currentThread(), value);
                LockSupport.park();
            }
            return result;
        }

        @Override
        public List<String> get(long timeout, TimeUnit unit) throws InterruptedException {
            if (isDone()) {
                return result;
            }

            long l = unit.toNanos(timeout);
            long last = System.nanoTime();
            long cur = 0;
            while (!isDone() && l > 0) {
                threadsMap.put(Thread.currentThread(), value);
                LockSupport.parkNanos(l);
                cur = System.nanoTime();
                l = cur - last;
                last = cur;
            }
            return result;
        }

        @Override
        public void add(String value) {
            if (isDone()) {
                return;
            }
            synchronized (result) {
                if (!isDone()) {
                    if (state == State.NEW.ordinal()) {
                        state = State.RUNNING.ordinal();
                    }
                    result.add(value);
                    if (result.size() == size) {
                        completed();
                    }
                }
            }
        }

        @Override
        public void completed() {
            if (unsafe.compareAndSwapInt(this, stateOffset, State.RUNNING.ordinal(), State.COMPLETED.ordinal())) {
                for (Thread t : threadsMap.keySet()) {
                    LockSupport.unpark(t);
                }
            }
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread07 thread06 = new Thread07();
        MaxSizeDefaultFuture defaultFuture = new MaxSizeDefaultFuture(THREAD_NUM);
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                String rec = thread06.execute();
                defaultFuture.add(rec);
            }).start();
        }

        System.out.println("阻塞：" + System.currentTimeMillis());
        List<String> stringList = defaultFuture.get();
        System.out.println("得结果：" + System.currentTimeMillis());
        for (String s : stringList) {
            System.out.println(s);
        }
    }
}
