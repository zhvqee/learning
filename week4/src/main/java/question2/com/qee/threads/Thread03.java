package question2.com.qee.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: learning
 * @Package: question2.threads CountDownLatch, 主线程await等待获取
 * @ClassName: Thread03 利用
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread03 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
        Thread03 thread03 = new Thread03();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                thread03.execute();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        for (String s : thread03.getResult()) {
            System.out.println(s);
        }
    }
}
