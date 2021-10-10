package question2.com.qee.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread03 利用 ReentrantLock 条件，当完成时，signal
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread05 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition isCompleted = lock.newCondition();
        Thread05 thread05 = new Thread05();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    thread05.execute();
                    if (thread05.getResult().size() == 10) {
                        isCompleted.signal();
                    }
                } finally {
                    lock.unlock();
                }
            }).start();
        }
        lock.lock();
        isCompleted.await();
        for (String s : thread05.getResult()) {
            System.out.println(s);
        }
        lock.unlock();
    }
}
