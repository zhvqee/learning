package question2.com.qee.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: learning
 * @Package: question2.threads 利用和CyclicBarrier await完成后回调，主线程阻塞CountDownLatch.await()
 * @ClassName: Thread03 利用
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread04 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUM, () -> {
            latch.countDown();
        });
        Thread04 thread04 = new Thread04();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                thread04.execute();
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        latch.await();
        for (String s : thread04.getResult()) {
            System.out.println(s);
        }
    }
}
