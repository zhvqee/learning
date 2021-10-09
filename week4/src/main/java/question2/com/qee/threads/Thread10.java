package question2.com.qee.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread10 通过 BlockingQueue, offer,take
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread10 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(THREAD_NUM);
        Thread10 thread10 = new Thread10();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                String rec = thread10.execute();
                queue.offer(rec);
            }).start();
        }

        for (int i = 0; i < THREAD_NUM; i++) {
            System.out.println(queue.take());
        }

    }
}
