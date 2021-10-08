package com.qee.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: learning
 * @Package: com.qee.threads
 * @ClassName: Thread01 通过join 等待获取多线程执行结果
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread01 extends ThreadBase {


    public static void main(String[] args) {

        Thread01 thread01 = new Thread01();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                thread01.execute();
            });
            list.add(thread);
            thread.start();
        }

        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (String s : thread01.getResult()) {
            System.out.println(s);
        }
    }
}
