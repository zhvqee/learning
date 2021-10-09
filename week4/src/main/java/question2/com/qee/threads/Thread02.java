package question2.com.qee.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread02 利用 线程池的submit Future机制获取
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread02 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread02 thread02 = new Thread02();

        ExecutorService executorService = new ThreadPoolExecutor(THREAD_NUM, THREAD_NUM, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(500));

        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Future<String> stringFuture = executorService.submit(thread02::execute);
            futureList.add(stringFuture);
        }

        for (Future future : futureList) {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }
}
