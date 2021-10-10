package question2.com.qee.threads;

import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: learning
 * @Package: com.qee.threads
 * @ClassName: Thread13  ReentrantLock Condition  PipedInputStream  PipedOutputStream实现
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread14 extends ThreadBase {

    public static void main(String[] args) throws IOException, InterruptedException {

        Thread14 thread14 = new Thread14();

        new Thread(thread14::execute).start();

        while (true) {
            List<String> result = thread14.getResult();
            if (result.size() == 0) {
                Thread.sleep(100);
            } else {
                break;
            }
        }
        for (String s : thread14.getResult()) {
            System.out.println(s);
        }
    }
}
