package com.qee.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ProjectName: learning
 * @Package: com.qee.threads
 * @ClassName: Thread08  通过 FutureTask 获取结果
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread08 extends ThreadBase {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread08 thread08 = new Thread08();
        List<FutureTask<String>> result = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            FutureTask futureTask = new FutureTask(thread08::execute);
            new Thread(futureTask).start();
            result.add(futureTask);
        }
        for (FutureTask futureTask : result) {
            System.out.println(futureTask.get());
        }
    }
}
